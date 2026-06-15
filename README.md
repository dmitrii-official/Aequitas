# Aequitas
> Aequitas is the Roman divine personification of fairness, equity, and justice. Unlike strict, letter-of-the-law justice, Aequitas represents the philosophical ideal of impartiality and "honest measure". Reflects the 50/50 chance of request approval or rejection.

## Deliberate Constraints
- No tablet layouts
- Locked in portrait mode
- No window size classes

## Libraries
- [Koin](https://insert-koin.io/) - for dependency injection
- [Timber](https://github.com/jakewharton/timber) - for logging
- [MockK](https://mockk.io/) - for testing

## Modules
- `data` - service implementation, supposed to contain networking and persistence layers.
- `app` - application with UI based on Jetpack Compose, theme included.

## Architecture and Implementations
- MVI - Model, View, Intent used between view model and composable UI
- Service injection implemented using Koin
- Request result is passed using `savedStateHandle` to display a snackbar
- Custom snackbar implemented
- Custom `SwitchSlider` composable implemented
- UI follows atomic design convention composables for the whole screen are considered _templates_, composables for single UI element are _atoms_, composables combining 2 and more UI elements are _molecules_ and _organisms_ are more complex layouts that are not whole screen.
