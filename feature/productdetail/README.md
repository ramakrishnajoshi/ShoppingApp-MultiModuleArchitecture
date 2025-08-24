## Module: feature:productdetail

### Purpose
Display product details with images, brand, price, stock, rating.

### Public Surface
- `productDetailNav(NavGraphBuilder, NavController)` route: `productDetail/{id}` with arg `id` (Int)

### Dependencies
- Depends on: `core:ui`, `core:network`, `core:model`
- Libraries: Hilt, Compose, Navigation, Coil

### Key Classes
- `ProductDetailRepository`/`Impl`: fetch single product
- `GetProductByIdUseCase`
- `ProductDetailViewModel`: reads `id` from `SavedStateHandle`, provides `ProductDetailUiState`
- `ProductDetailScreen`: UI with rich content and result callback

### How to Extend
- Add reviews section, image carousel, add-to-cart; send selection back via `savedStateHandle`.


