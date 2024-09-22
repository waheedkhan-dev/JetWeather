# JetWeather

 JetWeather is a Native Android Application that provides real-time weather data and a 5-day forecast. The app is built using modern Android development tools and libraries, including Jetpack Compose for the UI, Compose Navigation, Hilt for dependency injection, and Room for local data storage. The app follows the MVVM architecture pattern and uses StateFlow and Flow for managing and updating UI states efficiently.

# Features
* Current Weather Data: Displays current weather conditions, including temperature, humidity, wind speed, and more.
* 5-Day Forecast: Provides a detailed 5-day weather forecast.
* Search by City: Users can search for the current weather by entering the city name.
# Tech Stack
* Jetpack Compose: Modern toolkit for building native UI in Android.
* Compose Navigation: For navigating between different composable screens in a declarative way.
* Hilt: Dependency injection framework for managing dependencies across the app.
* Room: Local database for storing weather data and search history.
* MVVM Architecture: Separation of concerns with ViewModel, managing UI-related data and logic.
* StateFlow & Flow: For managing and emitting state changes across the app in a reactive way.
# How It Works
1. Current Weather: The app fetches the current weather using an API and displays it on the home screen.
2. 5-Day Forecast: The app fetches weather data for the next 5 days and presents it in a list format.
3. Search by City: Users can search for current weather conditions by typing in the name of a city.
# Setup Instructions
1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.
