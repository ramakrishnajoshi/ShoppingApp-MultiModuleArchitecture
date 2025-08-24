# Core Network Module - Architecture Rationales

## Purpose
This module provides centralized networking infrastructure for all feature modules.

## Key Architectural Decisions

### Why Centralized Networking?
- **Consistency**: All API calls use the same configuration (timeouts, interceptors, error handling)
- **Maintainability**: Change networking behavior in one place affects entire app
- **Debugging**: Single point to add logging, monitoring, and debugging tools
- **Performance**: Shared OkHttp client connection pool improves efficiency
- **Security**: Centralized certificate pinning and security policies

### Why Retrofit over Raw OkHttp?
- **Type Safety**: Convert HTTP APIs into Java/Kotlin interfaces at compile time
- **Automatic Serialization**: Built-in support for JSON, XML, and custom converters
- **Error Handling**: Consistent error responses across all endpoints
- **Testing**: Easy to mock service interfaces for unit tests
- **Code Generation**: Less boilerplate than manual HTTP client code

### Why Gson for JSON Serialization?
- **Mature**: Battle-tested library with extensive documentation
- **Flexible**: Handles complex JSON structures and custom serialization
- **Performance**: Fast serialization/deserialization for mobile use cases
- **Null Safety**: Works well with Kotlin's nullable types
- **Backward Compatibility**: Handles API version changes gracefully

### Why HTTP Logging Interceptor?
- **Development Aid**: See exact requests/responses during development
- **Debugging**: Quickly identify API issues and malformed requests
- **Conditional**: Only enabled in debug builds for security
- **Configurable**: Can adjust log level (NONE, BASIC, HEADERS, BODY)

### Why Separate DTOs from Domain Models?
- **API Evolution**: DTOs can change without affecting business logic
- **Null Safety**: Convert nullable API fields to safe domain models
- **Data Transformation**: Clean up API inconsistencies before they reach domain layer
- **Validation**: Validate data at network boundary before passing upstream
- **Versioning**: Support multiple API versions with same domain models

### Why Dependency Injection for Network Components?
- **Testing**: Easy to replace real network calls with mocks/fakes
- **Configuration**: Different environments (dev, staging, prod) can use different configurations
- **Lifecycle Management**: Hilt manages singleton instances efficiently
- **Modularity**: Features can depend on abstractions, not concrete implementations

### Why IoDispatcher for Network Calls?
- **Thread Safety**: Network calls should never block the main thread
- **Resource Management**: Dedicated thread pool for I/O operations
- **Performance**: Coroutines are more efficient than creating new threads
- **Cancellation**: Easy to cancel network operations when UI is destroyed
- **Flow Integration**: Works seamlessly with Flow for reactive data streams

## Module Dependencies

```
core:network
├── depends on: core:model (for domain models in DTOs)
├── provides: API services, NetworkModule, DTOs
└── used by: feature modules for data layer
```

## Future Considerations
- **Caching**: Consider adding response caching with OkHttp cache
- **Authentication**: Add JWT token management when auth is needed
- **Rate Limiting**: Implement request throttling for API quota management
- **Offline Support**: Cache responses for offline-first experience
- **GraphQL**: Consider Apollo if API moves to GraphQL