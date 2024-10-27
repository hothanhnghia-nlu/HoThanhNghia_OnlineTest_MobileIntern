# Currency Converter Application

## Overview
The Currency Converter app is a simple Android application that allows users to convert currencies in real-time. With a user-friendly interface, users can input an amount, select from a range of currencies, and instantly see the converted value.

## Build and Run Instructions
1. **Setup Environments**:
- Create GitHub Repository.
- Open Android Studio and create a new project with named "Currency Converter".
- Init, add and push the project onto the created Repository.

2. **Build screens**:
- Create a Splash Screen that launch app firstly and also is Standby screen.
- Create a Main Activity that display main UI.

3. **Set Up API Key**:
- Obtain an API key from a currency conversion API provider.
- Place it in the `gradle.properties` or any configuration file specified in your code.

4. **Build and Run the App**:
- Sync Gradle by selecting **File > Sync Project with Gradle Files**.
- Click the **Run** button or use `Shift + F10` to start the app on an emulator or physical device.

## Challenges and Notes
- **Network Requests**: The app uses Retrofit, Gson, and OkHttp libraries to make API requests and parse JSON data. Ensure you have an active internet connection to fetch currency rates.
- **Error Handling**: If the API call fails or there's no network connection and user has not filled in an amount, the app shows an error message.

## Demo Video
https://bit.ly/4fgQsdY