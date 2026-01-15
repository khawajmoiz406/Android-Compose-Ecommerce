# Android Compose E-Commerce

A sample e-commerce app built with Jetpack Compose, following Clean Architecture and MVVM. This project showcases modern Android development practices with offline-first capabilities.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

<p align="center">
  <img src="screenshots/home-demo.gif" width="250" alt="Home"/>
  <img src="screenshots/detail-demo.gif" width="250" alt="Product Detail"/>
  <img src="screenshots/login-screen.png" width="250" alt="Login"/>
</p>

## What It Does

- Browse products by category with search and sorting
- View product details with image galleries
- Save favorites locally
- Secure login with encrypted credential storage
- Works offline using cached data
- Supports dark mode

## Tech Stack

**UI:** Jetpack Compose, Material 3, Coil for images

**Architecture:** Clean Architecture + MVVM, Koin for DI, Kotlin Coroutines & Flow

**Data:** Retrofit + OkHttp for networking, Room for local storage, Encrypted SharedPreferences for credentials

**Navigation:** Type-safe Compose Navigation

## How It Works

The app uses a local-first approach - it shows cached data immediately while fetching fresh data in the background. User credentials are stored using AES-256 encryption.

```
Presentation (Compose + ViewModel)
        ↓
Domain (Use Cases)
        ↓
Data (Repository → Remote API + Local DB)
```

## Getting Started

1. Clone and open in Android Studio
2. Sync Gradle and run

**Demo login:**
```
Username: emilys
Password: emilyspass
```

Uses [DummyJSON API](https://dummyjson.com/) for demo data.

**Requirements:** Android 7.0+ (SDK 24), JDK 11+

## License

MIT License - see the license file for details.

## Contact

Email: khwajamoiz406@gmail.com

[GitHub Repository](https://github.com/khawajmoiz406/Android-Compose-Ecommerce)
