## Module: core:ui

### Purpose
Shared Compose UI components and theme.

### Public Surface
- `AppTheme`
- `LoadingView`, `ErrorView`, `AppToolbar`

### Dependencies
- Depends on: `core:model`
- Libraries: Compose BOM, Material3

### Key Classes
- `AppTheme`: wraps MaterialTheme
- Components in `components/`

### How to Extend
- Add reusable composables; keep them stateless and parameterized.

### Architecture Documentation
See [ARCHITECTURE.md](ARCHITECTURE.md) for detailed rationales behind the design decisions in this module.
