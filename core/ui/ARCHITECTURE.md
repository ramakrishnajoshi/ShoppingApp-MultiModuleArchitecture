# Core UI Module - Architecture Rationales

## Purpose
This module provides shared UI components, themes, and design system foundations for all feature modules.

## Key Architectural Decisions

### Why Shared UI Components?
- **Consistency**: All features use the same visual components and behavior
- **Maintainability**: Fix a bug or update styling in one place
- **Design System**: Enforces consistent user experience across the app
- **Efficiency**: Avoid duplicating common UI patterns
- **Testing**: Test complex UI logic once instead of in every feature

### Why Stateless Components?
- **Reusability**: Components can be used in different contexts with different data
- **Predictability**: Output depends only on input parameters
- **Testing**: Easy to test with different inputs and verify outputs
- **Performance**: Compose can optimize recomposition better
- **Composition**: Stateless components compose well together

### Why Material 3 Design System?
- **Modern**: Latest Material Design principles and components
- **Accessibility**: Built-in support for accessibility guidelines
- **Theming**: Dynamic theming with Material You color schemes
- **Platform Integration**: Follows Android platform conventions
- **Documentation**: Extensive design guidelines and best practices

### Why AppTheme Wrapper?
- **Customization**: Can override Material defaults with app-specific values
- **Consistency**: Single source of truth for app-wide theming
- **Future-Proofing**: Easy to add custom theme properties
- **Testing**: Can provide test-specific themes for UI tests
- **Dark Mode**: Centralized dark/light theme switching

### Why LoadingView and ErrorView Components?
- **User Experience**: Consistent loading and error states across features
- **Accessibility**: Proper content descriptions and error announcements
- **Customization**: Can be customized per feature while maintaining consistency
- **Best Practices**: Follows Material Design guidelines for state handling
- **Reusability**: Common patterns that every feature needs

### Why Composables in Separate Files?
- **Maintainability**: Easy to find and modify specific components
- **Testing**: Can test individual components in isolation
- **Code Review**: Smaller files are easier to review
- **IDE Performance**: Better performance with smaller files
- **Reusability**: Easy to import specific components where needed

### Why No Business Logic in UI Components?
- **Separation of Concerns**: UI components focus only on presentation
- **Testability**: Business logic can be tested without UI framework
- **Reusability**: Same UI components can work with different data sources
- **Performance**: No unnecessary recomposition from business logic changes
- **Debugging**: Easier to isolate UI bugs from business logic bugs

## Module Dependencies

```
core:ui
├── depends on: core:model (for domain models in component parameters)
├── provides: AppTheme, LoadingView, ErrorView, AppToolbar
└── used by: feature modules for UI layer
```

## Component Guidelines

### Creating New Shared Components
1. **Make them stateless**: Accept parameters, emit events
2. **Follow Material Design**: Use Material 3 components as building blocks
3. **Add previews**: Provide @Preview functions for design verification
4. **Consider accessibility**: Add content descriptions and semantic properties
5. **Document parameters**: Clear kdoc for component purpose and usage

### When to Add a Component Here vs Feature Module
- **Add to core:ui**: Component is used by 2+ features OR enforces design system
- **Keep in feature**: Component is specific to one feature's business logic

## Design Decisions

### Why Modifier Parameters?
- **Flexibility**: Parent can control layout behavior (padding, size, etc.)
- **Composition**: Components work well together
- **Performance**: Avoid wrapper composables that just add modifiers
- **Convention**: Follows Compose best practices

### Why Material Components as Base?
- **Accessibility**: Material components have accessibility built-in
- **Platform Integration**: Follows Android design guidelines
- **Maintenance**: Google maintains and updates these components
- **Documentation**: Well-documented with examples and guidelines

## Future Considerations
- **Animation System**: Add shared animation utilities and transitions
- **Typography Scale**: Expand typography system beyond Material defaults
- **Custom Components**: Add app-specific components (cards, lists, etc.)
- **Theming Extensions**: Add semantic color tokens for app-specific use cases
- **Responsive Design**: Add utilities for different screen sizes and orientations