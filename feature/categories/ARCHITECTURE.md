# Feature Categories Module - Architecture Rationales

## Purpose
This module implements the categories listing feature, demonstrating the standard feature module architecture pattern used throughout the app.

## Key Architectural Decisions

### Why Feature-Based Module Structure?
- **Business Alignment**: Each module represents a complete user feature
- **Team Ownership**: Different teams can own different features
- **Independent Development**: Features can be developed and tested in isolation
- **Deployment Flexibility**: Features could be dynamically delivered in the future
- **Code Organization**: Related code stays together (data, domain, presentation)

### Why Repository Pattern?
- **Data Source Abstraction**: UI doesn't know if data comes from network, cache, or database
- **Testing**: Easy to mock data layer for presentation layer tests
- **Flexibility**: Can switch data sources without changing domain or UI layers
- **Error Handling**: Centralized place to handle data layer errors
- **Caching Strategy**: Repository controls caching logic transparently

### Why UseCase (Domain) Layer?
- **Business Logic Isolation**: Complex business rules live here, not in ViewModel
- **Reusability**: UseCases can be shared between different presentation layers
- **Testing**: Business logic can be unit tested without Android dependencies
- **Single Responsibility**: Each UseCase handles one specific business operation
- **Coordination**: Can coordinate multiple repositories or complex operations

### Why ViewModel + StateFlow Pattern?
- **Lifecycle Awareness**: ViewModel survives configuration changes
- **State Management**: Single source of truth for screen state
- **Reactive UI**: UI automatically updates when state changes
- **Thread Safety**: StateFlow handles background/main thread switching
- **Memory Management**: ViewModel is automatically cleared when screen is destroyed

### Why UiState Data Class?
- **Predictable State**: UI behavior is a pure function of UiState
- **Immutability**: Cannot be accidentally modified by UI layer
- **Testing**: Easy to verify state changes in ViewModels
- **Debugging**: Clear state representation for debugging
- **Compose Integration**: Works perfectly with Compose remember and recomposition

## Layer Responsibilities

### Data Layer (Repository)
```kotlin
class CategoriesRepositoryImpl @Inject constructor(
    private val api: ProductApiService
) : CategoriesRepository {
    override fun getCategories(): Flow<List<Category>> = flow {
        val items = api.getCategories()
        emit(items.map { dto -> /* DTO to Domain mapping */ })
    }
}
```

**Responsibilities**:
- Call API and handle network errors
- Transform DTOs to domain models with null-safe defaults
- Could add caching, retry logic, or offline support
- Emit data as Flow for reactive consumption

### Domain Layer (UseCase)
```kotlin
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    operator fun invoke(): Flow<List<Category>> = repository.getCategories()
}
```

**Responsibilities**:
- Encapsulate business logic (currently simple pass-through)
- Could add business rules like sorting, filtering, or validation
- Coordinate multiple repositories if needed
- Provide clean interface for presentation layer

### Presentation Layer (ViewModel)
```kotlin
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState(isLoading = true))
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()
}
```

**Responsibilities**:
- Manage UI state and lifecycle
- Handle user interactions
- Transform domain data to UI-specific state
- Manage async operations with proper error handling

## Navigation Integration

### Why Extension Function for NavGraphBuilder?
```kotlin
fun NavGraphBuilder.categoriesNav(onCategoryClick: (String) -> Unit) {
    composable(ROUTE_CATEGORIES) {
        CategoriesScreen(onCategoryClick = onCategoryClick)
    }
}
```

**Benefits**:
- **Encapsulation**: Feature owns its navigation setup
- **Reusability**: Can be used in different navigation hosts
- **Type Safety**: Compile-time checking of navigation parameters
- **Modularity**: Feature can be easily removed or moved

### Why Callback-Based Navigation?
- **Decoupling**: Screen doesn't know about the navigation graph structure
- **Testing**: Easy to test navigation callbacks
- **Flexibility**: Parent can decide how to handle navigation
- **Composability**: Screens can be used in different navigation contexts

## Dependency Injection Strategy

### Why Hilt Module for Repository Binding?
```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriesModule {
    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository
}
```

**Benefits**:
- **Interface Segregation**: Depend on abstractions, not implementations
  - **What this means**: The `@Binds` annotation tells Hilt to provide the interface (`CategoriesRepository`) when requested, but internally bind it to the concrete implementation (`CategoriesRepositoryImpl`)
  - **Why abstractions matter**:
    - **Interface Segregation Principle**: Clients (like ViewModels) should only depend on the methods they actually use, not on concrete classes with additional methods they don't need
    - **Dependency Inversion**: High-level modules (UI layer) don't depend on low-level modules (data layer), both depend on abstractions
  - **Concrete benefits**:
    - **ViewModel simplicity**: `CategoriesViewModel` only knows about `suspend fun getCategories()` from the interface, not internal implementation details
    - **Implementation flexibility**: We can switch from room database to web API or in-memory cache without changing ViewModel code
    - **Testing isolation**: Unit tests can inject a `FakeCategoriesRepository` that implements the same interface
    - **Code contracts**: Interface defines the "what" (contract), implementation handles the "how" (details)
- **Testing**: Easy to provide test implementations
- **Singleton Scope**: Repository is shared across the app
- **Performance**: Hilt creates instances only when needed

## Error Handling Strategy

### Why Try-Catch in ViewModel?
```kotlin
try {
    getCategories().collect { list ->
        _uiState.value = CategoriesUiState(data = list)
    }
} catch (t: Throwable) {
    _uiState.value = CategoriesUiState(error = t.message ?: "Unknown error")
}
```

**Rationale**:
- **User Experience**: Convert technical errors to user-friendly messages
- **Stability**: Prevent crashes from network or parsing errors
- **Recovery**: UI can show retry options
- **Debugging**: Can log errors while showing safe messages to users

## Module Dependencies

```
feature:categories
├── depends on: core:model, core:network, core:ui
├── provides: CategoriesScreen, categoriesNav
└── used by: app module for navigation setup
```

## Testing Strategy
- **Unit Tests**: UseCase and Repository with mocked dependencies
- **ViewModel Tests**: State changes with test coroutines
- **UI Tests**: Screen behavior with composed UI
- **Integration Tests**: End-to-end feature flow

## Future Enhancements
- **Caching**: Add local caching for offline support
- **Pagination**: Support for large category lists
- **Search**: Add category search functionality
- **Analytics**: Track user interactions with categories
- **Error Recovery**: Automatic retry with exponential backoff