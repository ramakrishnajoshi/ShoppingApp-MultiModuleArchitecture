## Module: core:network

### Purpose
Provide networking stack and API interfaces for features.

### Public Surface
- `ProductApiService`
- DI modules: `NetworkModule`, `ApiModule`, `CoroutinesModule`
- Constant: `BASE_URL`

### Dependencies
- Depends on: `core:model`
- Libraries: Retrofit, OkHttp logging, Gson, Hilt

### Key Classes
- `NetworkModule`: OkHttp, Retrofit, Gson providers
- `ApiModule`: Retrofit.create services
- `CoroutinesModule`: `@IoDispatcher` qualifier

### How to Extend
- Add new DTOs in `dto/`, add service methods in `api/`, provide new interfaces in a DI module as needed.

### Architecture Documentation
See [ARCHITECTURE.md](ARCHITECTURE.md) for detailed rationales behind the design decisions in this module.

