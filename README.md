This is a Kotlin Multiplatform project targeting Android, iOS.

Project Structure:
=================
Multi Module: Core, Home
MVVM=> UI->ViewModel->UseCase-> Repository->Service-> Platform Specific Expect/Actual
and listening Flow for any update in data.

#Architecture MVVM:
===================
The View displays data from the ViewModel in Screen.
When the user interacts with the UI (like clicking a link or opening a PDF), the View informs the ViewModel.
The ViewModel performs required logic, and for business logic I made  Usecase for (Repository) to be used by Viewmodel.
Any changes in the Model are reflected back to the ViewModel using MutableStateFlow, which updates the View using flows.

This separation ensures that:
The UI remains simple and reactive.
The data layer is isolated and testable.
Business logic can be maintained without touching the UI code.

Apart from this I used Multi Module Architecture, handling core logic in core module, and platform specific logic in ComposeApp module
I create Services for the intercommunication between modules.

#Libraries:
Koin: For dependency Injection, viewmodel, navigation
Room: For database
Kotlinx DateTIme


HTML Clicks and rendering platform wise using expect/actual
In webview have rendered html with javascript function for added event listening js script in webview
which then Toasted message.