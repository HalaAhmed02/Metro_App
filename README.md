📱 Mobile Application (Jetpack Compose)
In addition to the console-based version, the Cairo Metro Route Planner has been extended into a fully functional Android mobile application using modern UI technologies. This version delivers a smooth, interactive, and user-friendly experience while reusing the same core routing logic.

🌟 Features
Modern UI/UX: Built using Jetpack Compose with a clean, responsive design inspired by Cairo Metro colors (Red & Black).

Interactive Station Selection: Users can easily select the start and destination stations through an intuitive interface.

Swap Functionality: Quickly switch between origin and destination stations with a single action.

Real-Time Route Calculation: Instantly computes the optimal route using the existing BFS-based logic from the Domain layer.

Route Details Display: Provides:

Full list of stations

Interchange stations

Total number of stations

Estimated travel time

Ticket fare

State Management: Efficient UI state handling using StateFlow to ensure reactive and consistent updates.

🏗️ Architecture & Integration
The mobile application follows the same Clean Architecture principles to ensure scalability and maintainability:

Presentation Layer:
Implemented using Jetpack Compose and ViewModel to manage UI state and user interactions.

Domain Layer:
Reused without modification, ensuring that business logic remains independent and testable.

Data Layer:
Provides station data through repository implementations integrated with the domain layer.

This architecture ensures strong separation of concerns, high reusability, and consistency between the console and mobile versions.

🛠️ Tech Stack (Mobile)
Language: Kotlin

UI: Jetpack Compose

Architecture: MVVM + Clean Architecture

State Management: StateFlow

Target: Android Application
