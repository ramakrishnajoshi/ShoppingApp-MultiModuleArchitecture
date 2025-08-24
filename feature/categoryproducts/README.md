## Module: feature:categoryproducts

### Purpose
Show products inside a selected category (by slug).

### Public Surface
- `categoryProductsNav(NavGraphBuilder)` route: `categoryProducts/{category}` with arg `category`

### Dependencies
- Depends on: `core:ui`, `core:network`, `core:model`
- Libraries: Hilt, Compose, Navigation, Coil

### Key Classes
- `CategoryProductsRepository`/`Impl`: fetch products by slug
- `GetProductsByCategoryUseCase`
- `CategoryProductsViewModel`: reads slug from `SavedStateHandle`
- `CategoryProductsScreen`: UI list with images/price/rating

### How to Extend
- Add sorting, search; observe result from detail to update list or navigate.


