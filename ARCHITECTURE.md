# ShoppingApp - Complete Architecture Documentation

This document provides a comprehensive overview of the architectural decisions and rationales in the ShoppingApp multi-module Android application.

## Architecture Overview

The ShoppingApp follows Clean Architecture principles with MVVM pattern, implemented using modern Android development practices and a multi-module structure.

```
┌─────────────────────────────────────────────────────────┐
│                     App Module                          │
│           (Navigation, DI Setup, MainActivity)          │
└─────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────┐
│                 Feature Modules                        │
│  ┌─────────────┐ ┌─────────────────┐ ┌──────────────┐  │
│  │ categories  │ │ categoryproducts│ │ productdetail│  │
│  └─────────────┘ └─────────────────┘ └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────┐
│                   Core Modules                         │
│  ┌──────────┐    ┌───────────┐      ┌──────────────┐   │
│  │   :ui    │    │ :network  │      │   :model     │   │
│  │ (shared  │    │(API calls,│      │ (domain      │   │
│  │  UI      │    │ DI setup) │      │  models)     │   │
│  │components)│    │           │      │              │   │
│  └──────────┘    └───────────┘      └──────────────┘   │
└─────────────────────────────────────────────────────────┘
```

## Core Architectural Principles

### 1. Multi-Module Architecture
**Why**: Enables parallel development, faster builds, clear boundaries, and team scalability.

### 2. Clean Architecture Layers
```
Presentation ──→ Domain ──→ Data
(ViewModel)    (UseCase)   (Repository)
```
**Why**: Separation of concerns, testability, and maintainability.

### 3. Dependency Inversion

**Core Principle**: Higher-level modules should not depend on lower-level modules. Both should depend on abstractions (interfaces).

#### What are Higher-Level vs Lower-Level Modules?

**Higher-Level Modules** (Business Logic):
- Domain layer (Use Cases, Business Rules)
- Presentation layer (ViewModels, UI Components)
- These modules contain the core business logic and application policies

**Lower-Level Modules** (Implementation Details):
- Data layer (Repository implementations, API clients, Database access)
- Infrastructure concerns (Network, Database, File System)
- These modules handle technical implementation details

#### Why Dependency Inversion Matters

**Without Dependency Inversion** (❌ Problematic):
```kotlin
// UseCase directly depends on concrete implementation
class GetCategoriesUseCase(
    private val apiService: ProductApiService  // Direct dependency on low-level module
) {
    // Business logic is tightly coupled to API implementation
}
```

**With Dependency Inversion** (✅ Correct):
```kotlin
// UseCase depends only on abstraction
class GetCategoriesUseCase(
    private val repository: CategoriesRepository  // Depends on interface/abstraction
) {
    // Business logic is independent of implementation details
}
```

#### How It's Implemented in This Project

**1. Repository Interfaces in Domain Context**
```kotlin
// Interface defines what the domain needs (contract)
interface CategoriesRepository {
    fun getCategories(): Flow<List<Category>>
}

// UseCase depends on abstraction, not implementation
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository  // Abstraction dependency
)
```

**2. Implementations in Data Layer**
```kotlin
// Concrete implementation handles technical details
class CategoriesRepositoryImpl @Inject constructor(
    private val api: ProductApiService
) : CategoriesRepository {
    // Implementation can change without affecting domain
}
```

**3. Dependency Injection Wiring**
```kotlin
@Module
abstract class CategoriesModule {
    @Binds
    abstract fun bindCategoriesRepository(
        impl: CategoriesRepositoryImpl
    ): CategoriesRepository  // Binds concrete to abstraction
}
```

#### Benefits of This Approach

**1. Flexibility and Replaceability**
- Can swap implementations (REST API → GraphQL → Local Database) without changing business logic
- Different implementations for different environments (mock for testing, real for production)

**2. Testability** 
- Use Cases can be tested with mock repositories
- No need to set up real network/database for unit tests
- Fast, isolated test execution

**3. Parallel Development**
- Teams can work on domain logic while data layer is being implemented
- Interface serves as a contract between teams

**4. Build Performance**
- Higher-level modules don't need to recompile when lower-level implementations change
- Reduced build times in large codebases

**5. Code Stability**
- Business logic remains stable even when technical implementation changes
- Reduces cascade effects of changes through the system

**6. Framework Independence**
- Domain layer doesn't know about Android, Retrofit, Room, or any framework
- Business logic can be reused across different platforms

This pattern ensures that the application's core business logic (what makes your app unique) is protected from changes in technical implementation details (databases, APIs, frameworks).

### 4. Single Responsibility Principle
- Each module has one reason to change
- Each class has one job
- **Example**: ViewModel manages UI state, UseCase handles business logic, Repository manages data access

## Module Documentation

### Quick Reference
- **[App Module](app/ARCHITECTURE.md)**: Application assembly, navigation, DI setup
- **[Core Model](core/model/ARCHITECTURE.md)**: Domain models and shared types
- **[Core Network](core/network/ARCHITECTURE.md)**: API services and networking infrastructure
- **[Core UI](core/ui/ARCHITECTURE.md)**: Shared UI components and theming
- **[Feature Categories](feature/categories/ARCHITECTURE.md)**: Example feature module pattern

### Core Modules (Foundation)

#### core:model
- **Purpose**: Pure domain models, no Android dependencies
- **Key Decision**: Non-nullable properties with repository-provided defaults
- **Why**: Type safety, clear contracts, easy testing

#### core:network
- **Purpose**: Centralized networking with Retrofit and OkHttp
- **Key Decision**: DTO to Domain mapping in repositories
- **Why**: API independence, null safety, data validation

#### core:ui
- **Purpose**: Shared UI components and design system
- **Key Decision**: Stateless composables with Material 3
- **Why**: Consistency, reusability, maintainability

### Feature Modules (Business Logic)

#### Standard Feature Pattern
Each feature follows the same layered pattern:
```
feature:name
├── data/
│   ├── Repository (interface)
│   └── RepositoryImpl (API calls, DTO mapping)
├── domain/
│   └── UseCase (business logic)
├── presentation/
│   ├── ViewModel (UI state management)
│   └── Screen (Compose UI)
└── navigation/
    └── NavGraphBuilder extension
```

**Why This Pattern**:
- **Predictability**: Developers know where to find specific functionality
- **Testability**: Each layer can be tested in isolation
- **Scalability**: Easy to add new features following the same pattern

## Key Architectural Decisions

### 1. Reactive Programming with Flow
```kotlin
Repository → Flow<Data> → UseCase → Flow<Data> → ViewModel → StateFlow<UiState>
```
**Benefits**: Asynchronous data handling, automatic UI updates, cancellation support

### 2. Dependency Injection with Hilt
- **Scope Management**: Singleton for repositories, ViewModelScoped for ViewModels
- **Testing**: Easy to provide mocks and fakes
- **Performance**: Compile-time dependency graph generation

### 3. Navigation with Compose
- **Type Safety**: Route constants and parameter validation
- **Modularity**: Each feature owns its navigation setup
- **State Management**: Proper backstack and state handling

### 4. Error Handling Strategy
- **Repository Level**: Convert technical errors to domain errors
- **ViewModel Level**: Convert to user-friendly messages
- **UI Level**: Display appropriate error states with retry options

## Development Guidelines

### Adding a New Feature
1. Create `feature:newfeature` module
2. Implement Repository → UseCase → ViewModel → Screen
3. Add navigation extension function
4. Wire into app navigation

### Adding a New API
1. Create DTOs in `core:network`
2. Add service method to `ProductApiService`
3. Map DTO to Domain in repository
4. Use existing UseCase/ViewModel pattern

### Testing Strategy
- **Unit Tests**: UseCases and Repositories with mocked dependencies
- **ViewModel Tests**: State changes with TestCoroutineRule
- **UI Tests**: Compose screens with test semantics
- **Integration Tests**: Feature flows end-to-end

## Benefits of This Architecture

### For Developers
- **Predictable Structure**: Know where to find and add code
- **Parallel Development**: Work on different features simultaneously
- **Easy Testing**: Clear boundaries make mocking straightforward
- **Fast Builds**: Gradle builds modules in parallel

### For Product
- **Maintainability**: Changes in one area don't break others
- **Scalability**: Easy to add new features or team members
- **Quality**: Architecture enforces good practices
- **Performance**: Optimized builds and runtime performance

### For Business
- **Faster Delivery**: Parallel development and clear patterns
- **Lower Risk**: Isolated changes reduce regression likelihood
- **Team Growth**: New developers can contribute quickly
- **Technical Debt**: Architecture prevents common pitfalls

## Future Considerations

### Potential Enhancements
- **Dynamic Feature Modules**: On-demand feature delivery
- **Local Database**: Room for offline support and caching
- **Background Sync**: WorkManager for data synchronization
- **Push Notifications**: Firebase for user engagement
- **Analytics**: Track user behavior and app performance

### Architectural Evolution
- **Event-Driven Architecture**: For complex inter-feature communication
- **Repository Pattern Enhancement**: Add caching and conflict resolution
- **Domain Events**: For business rule enforcement
- **CQRS**: If read/write patterns become complex

This architecture provides a solid foundation for growth while maintaining code quality and developer productivity.