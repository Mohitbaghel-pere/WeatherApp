# WeatherApp
WeatherApp is an Android application built using Kotlin and designed with MVVM Clean architecture. This app fetches users live location after login or registration and show the current weather. It features a secure local database using Room with SQLCipher encryption, ensuring user data is kept safe. The app includes both a current weather screen and a history screen to display past weather data. The app utilizes HILT for dependency injection, Compose for UI, Room for local database storage, Fused Location API for obtaining the current location, and the OpenWeatherMap API for fetching weather information.

## Features
- MVVM Clean Architecture:
- Dependency Injection with HILT:
- Room Database:
- Secure Storage
- Fused Location API:
- OpenWeatherMap API:
- Navgraph
- Splash Screen
- Login and Registration
- Current Weather
- Weather History
- Data Binding


## Dependencies
- HILT
- Room Database
- Fused Location Provider
- OpenWeatherMap API
- Retrofit
- JUnit
- Mockito
- SqlCipher
- LiveData
- ViewModel
  
## NOTE
You need to use your own OpenWeather API Key at below mentioned path
build.gradle -> defaultConfig -> OPEN_WEATHER_API_KEY

## NOTE
Also update the Pass Code for Database encrpytion at below mentioned path
build.gradle -> defaultConfig -> PASS_CODE

## Unit Test
This repository provides a comprehensive unit testing setup for Android applications using JUnit, Mockito. The testing framework is designed to ensure the reliability, correctness, and maintainability of the application codebase.

### Key Components
- JUnit
- Mockito

### Unit test results
Unit test results covers above 95% of code coverage.

![Screenshot 2024-07-22 at 2 18 37 PM](https://github.com/user-attachments/assets/485d6e84-bf82-4066-8c52-d09d411ea330)


## Project Structure
The project is organized into the following directories:

### data
The data directory encapsulates all data-related components. It includes subdirectories for various data models and operations:

### dao
Contains Data Access Object (DAO) interfaces for database operations.

### models
Defines data models and entity classes related to the database.

### network
Contains classes for handling API requests and responses

### repository
Manages data interactions with the database and remote APIs.

### di
The di directory manages dependency injection modules for different aspects of the application:

### DB Module
Handles dependency injection related to the database.

### Network Module
Manages dependency injection for network-related components.

### UI
The UI directory is subdivided into specific features and modules of the application:

### adapter
Contains adapters for RecyclerViews and other components.

### apistate
Manages API states and responses.

### auth
Contains files related to user authentication, including login and registration.

### home
Encompasses files related to the home feature of the app, including displaying current weather and weather history.

### splash
Manages the splash screen components.

### ViewModel
Contains ViewModel classes for managing UI-related data.

### utils
The utils directory contains utility classes and functions that are used across the application for various purposes like encryption, network checks, etc.

### feature
The feature directory is subdivided into specific features/modules of the application:

#### - Home:
Contains files related to the home feature.

#### - Login:
Contains files associated with user login functionality.

#### - Registration:
Manages user registration-related components.

#### - Weather History:
Encompasses files related to displaying weather history.

## Security

#### - SQLCipher Encryption in Room DB

Our application employs [SQLCipher](https://www.zetetic.net/sqlcipher/) for encrypting sensitive data stored in the Room Database. SQLCipher provides robust encryption mechanisms, ensuring the confidentiality of user data.

#### - ProGuard for Code Obfuscation

To enhance the security of our application, we utilize [ProGuard](https://www.guardsquare.com/products/proguard) for code obfuscation. ProGuard helps make reverse engineering more challenging, protecting sensitive code and assets from unauthorized access.


This structure ensures a clean separation of concerns, making the codebase modular, maintainable, and scalable. Each directory and subdirectory is designed to house components specific to a particular aspect of the application, facilitating easier development and testing

#### - App screens

![Screenshot_20240722_142302](https://github.com/user-attachments/assets/887711a0-29d6-43e0-afea-22c2588bb610)

![Screenshot_20240722_142124](https://github.com/user-attachments/assets/5469e579-5a1c-4b5d-a6d5-9ba3a480765f)

![Screenshot_20240722_142149](https://github.com/user-attachments/assets/9a9e842a-69f0-4544-b8b0-36fdc5b8ab48)

![Screenshot 2024-07-22 at 2 25 17 PM](https://github.com/user-attachments/assets/2e40b6aa-8707-433b-9fb4-24f9c6f8a1f6)

<img width="363" alt="Screenshot 2024-07-22 at 2 26 30 PM" src="https://github.com/user-attachments/assets/71ac3b66-2a83-402a-83c8-0b0b3ce82979">

### - Contributing
Being only contributor I have put all code in main branch directly.
Feel free to contribute to the WeatherApp project by opening issues, providing feedback, or submitting pull requests. Your contributions are highly appreciated.









