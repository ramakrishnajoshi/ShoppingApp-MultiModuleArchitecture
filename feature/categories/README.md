## Module: feature:categories

### Purpose
Display all categories from the API.

### Public Surface
- `categoriesNav(NavGraphBuilder)` route constant: `ROUTE_CATEGORIES`

### Dependencies
- Depends on: `core:ui`, `core:network`, `core:model`
- Libraries: Hilt, Compose, Navigation

### Key Classes
- `CategoriesRepository`/`Impl`: fetch and map DTO→domain
- `GetCategoriesUseCase`: use case wrapper
- `CategoriesViewModel`: exposes `CategoriesUiState`
- `CategoriesScreen`: UI

### How to Extend
- Add pagination/filtering; update mapping for new fields; expose callbacks to navigate to products.


