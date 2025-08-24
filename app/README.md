## Module: app

### Purpose
Application entry point. Hosts NavHost and wires feature routes. Applies Hilt and app-wide dependencies.

### Public Surface
- `MainActivity` with `NavHost`
- Routes wired: `categories`, `categoryProducts/{category}`, `productDetail/{id}`

### Dependencies
- Depends on: `feature:categories`, `feature:categoryproducts`, `feature:productdetail`, `core:ui`, `core:network`, `core:model`
- Plugins: `com.android.application`, Kotlin, Compose, Hilt

### Key Classes
- `ShoppingApp` (`@HiltAndroidApp`)
- `MainActivity` (Navigation graph)

### How to Extend
- Add a new feature module and expose a NavGraph function; include route in `MainActivity` NavHost.


