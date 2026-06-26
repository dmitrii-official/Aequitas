# Candidate Instructions: Android Development Technical Interview

## Objective

The goal of this exercise is to assess your ability to design, structure, and implement a robust, maintainable, and testable Android application flow. We are evaluating your proficiency in:

- **Software Architecture:** Applying the MVVM (Model-View-ViewModel) or MVI (Model-View-Intent) pattern.
- **Modern Kotlin:** Utilizing Kotlin Coroutines and Flow for asynchronous operations and state management.
- **Dependency Injection:** Employing a modern DI framework (e.g., Hilt or Koin) to manage dependencies.
- **Unit Testing:** Writing effective tests for core business logic.
- **Jetpack Navigation:** Utilizing the Navigation Component for navigation.

## Scenario

You will implement a simplified **request processing flow** for a mobile application.

### Application Screens

**Request List Screen:**
- Displays a button to initiate a new request.
- Must observe the application state (via a shared flow or ViewModel) and display a **Snackbar** with the outcome (success or error) that allows for a transient display, following the completion of an action on the detail screen.

**Request Detail Screen:**
- Displays the details of a request (mock data).
- Includes a **"Reject" button** and an **"Approve" slider**.
- Must display a loading state (e.g., a `CircularProgressIndicator`) and **disable all interaction** while the approval/rejection action is in progress.

## Core Business Logic

You must implement a mock asynchronous service that handles the approval/rejection logic.

| Action      | Simulation                                                            | Outcome                                                                               |
|-------------|-----------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **Reject**  | Immediate return with success/error state.                            | Navigate back with an appropriate error message.                                      |
| **Approve** | Simulates an async network call that takes **2 seconds** to complete. | **Random Failure:** Has a **50% chance** of failing with a descriptive error message. |

Figma Mockups: *Figma link provided in the original*.

## Task

### Architectural Setup (MVVM/MVI)

- **UI Framework:** State your choice — Jetpack Compose (preferred) or XML layouts/Fragments.
- **Model:** Define the `Request` data structure (must conform to `Serializable` or `Parcelable` if using Arguments).
- **Service Layer:** Define a `RequestService` interface. Implement a concrete `MockRequestService` class where methods are `suspend` functions and use coroutine `delay()` to simulate network latency and random failure.
- **ViewModel:** Implement a `RequestDetailViewModel` to host the business logic. It must depend on the `RequestService` interface for dependency injection and testing.
- **Dependency Injection:** Use a modern DI framework (Hilt or Koin) to manage the dependency between the ViewModel and the Service.

### Implementation Requirements

**Request List Screen:**
- The button tap must use the Navigation Component to navigate to the Detail screen.
- Implement logic to collect the outcome message (via `SharedFlow` or similar) and display it as a transient Snackbar.

**Request Detail Screen:**
- The "Reject" button tap and "Approve" slider completion must call the ViewModel to process the action.
- The UI must show a loading state and disable interactions while the async service call is pending.

**Navigation:** All navigation must be handled using the Jetpack Navigation Component.

### Concurrency and Error Handling

- Use Kotlin Coroutines and Flow to call the mock service and manage the ViewModel's state.
- The ViewModel must expose state changes (e.g., `isLoading`, `successMessage`, `errorMessage`) using `StateFlow` to the View reactively.

### Code Quality & Testing

- **Unit Tests:** You must provide unit tests for the `RequestDetailViewModel`.
    - Test at least one successful approval path (verifying state transitions).
    - Test at least one failure path (rejection or random service failure).
    - Tests must use a `TestDispatcher` and `runTest` to ensure proper coroutine testing practices.
- **Mocking:** Use a mock or stub implementation of the `RequestService` interface to effectively isolate and test the ViewModel's logic.
- **Code Quality:** Write clean, modular, and maintainable code with clear naming conventions.

## Evaluation Criteria

- Architectural Design (MVVM/MVI) and clear separation of layers.
- Concurrency Mastery (effective use of Coroutines, Flow, and structured concurrency).
- Testing Excellence (robust unit tests using modern coroutine testing conventions).
- Jetpack Usage (correct implementation of Navigation Component, StateFlow, and DI).
- Adherence to Android best practices.
- Basic UI styling and presentation.

## Submission

Please provide your completed project as a zipped archive or a link to a Git repository, ensuring the test targets are included.

## Additional Notes

- Feel free to ask clarifying questions during the exercise.
- You are encouraged to use any built-in Android frameworks or libraries as needed.
- Focus on demonstrating your architectural decision-making and concurrency skills.
