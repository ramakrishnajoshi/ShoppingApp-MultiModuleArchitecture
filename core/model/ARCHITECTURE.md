# Core Model Module - Architecture Rationales

## Purpose
This module contains pure domain models and shared types that represent the core business concepts of the shopping application.

## Key Architectural Decisions

### Why Pure Kotlin Module?
- **Platform Independence**: Models can be used in any Kotlin environment (Android, JVM, Native)
- **Fast Compilation**: No Android dependencies means faster builds
- **Testing**: Easy to unit test without Android framework
- **Reusability**: Could be shared with backend services or other platforms
- **Simplicity**: Clear separation between business models and platform concerns

### Why Data Classes for Models?
- **Immutability**: Data classes encourage immutable data structures
- **Value Semantics**: Automatic equals(), hashCode(), and toString() implementations
- **Copy Function**: Easy to create modified copies while preserving immutability
- **Destructuring**: Can destructure into individual properties when needed
- **Serialization**: Works well with JSON serialization libraries

### Why Non-Nullable Properties?
- **Safety**: Eliminates null pointer exceptions in business logic
- **Clarity**: Domain models represent valid business states only
- **API Contract**: Clear contracts between layers about required data
- **Default Values**: Repository layer provides sensible defaults for missing API data
- **Validation**: Forces validation at the data layer boundary

### Why Separate from Network DTOs?
- **Stability**: Domain models don't change when API changes
- **Clean Interfaces**: Only contains data that the app actually uses
- **Type Safety**: Can use appropriate types (e.g., Int for IDs vs String from API)
- **Business Rules**: Models can encode business rules and constraints
- **Testing**: Easier to create test data without external API dependencies

### Why Resource Sealed Class?
- **Type Safety**: Compiler ensures all states are handled
- **Exhaustive When**: Kotlin forces handling of all cases
- **Clear Intent**: Makes async operation states explicit
- **Error Handling**: Standardized way to handle success/error/loading states
- **Flow Integration**: Works perfectly with Flow for reactive programming

### Why Minimal Model Classes?
- **Focus**: Each model represents exactly one business concept
- **Maintainability**: Small classes are easier to understand and modify
- **Performance**: Less memory usage and faster serialization
- **Testing**: Easier to create test instances with minimal required data
- **Evolution**: Easier to add new models than to modify existing large ones

## Model Design Principles

### Product Model
```kotlin
data class Product(
    val id: Int,           // Unique identifier (business key)
    val title: String,     // Display name (required for UI)
    val description: String, // Product details (can be empty but not null)
    val price: Double,     // Business value (required for calculations)
    // ... other properties
)
```

**Rationale**: 
- `id` as Int instead of String for type safety and efficiency
- All properties non-null with repository providing defaults
- Contains only data needed by the UI and business logic

### Category Model
```kotlin
data class Category(
    val slug: String,      // URL-safe identifier for navigation
    val name: String,      // Display name for UI
    val url: String        // API endpoint reference
)
```

**Rationale**:
- `slug` as primary identifier for REST API consistency
- Minimal properties focused on navigation and display
- No nested objects to keep it simple

### Resource Sealed Class
```kotlin
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
```

**Rationale**:
- Generic type parameter for type safety
- Loading state with no data
- Error includes both user message and optional exception for debugging
- Success wraps actual data

## Module Dependencies

```
core:model
├── depends on: Kotlin stdlib only
├── provides: Product, Category, Resource
└── used by: all other modules (network, ui, features)
```

## Design Guidelines

### Adding New Models
1. **Keep it Simple**: Only include properties needed by the app
2. **Non-Nullable**: Use repository layer to provide defaults for missing data
3. **Data Classes**: Use data classes unless you need custom behavior
4. **Business Focused**: Represent business concepts, not API structure
5. **Immutable**: Use `val` properties and immutable collections

### Model Evolution
- **Additive Changes**: Adding properties is safe (use default values)
- **Breaking Changes**: Coordinate with all dependent modules
- **Deprecation**: Mark old properties as deprecated before removal
- **Migration**: Provide migration utilities for complex changes

## Testing Considerations
- **Factory Functions**: Consider adding factory functions for test data
- **Builder Pattern**: For complex models, consider builder pattern
- **Default Values**: Provide sensible defaults for easier test setup

## Future Considerations
- **Validation**: Add model validation annotations or functions
- **Serialization**: Add kotlinx.serialization support if needed
- **Value Classes**: Use value classes for type-safe IDs (ProductId, CategoryId)
- **Result Type**: Consider using Result<T> instead of Resource<T> for better Kotlin integration