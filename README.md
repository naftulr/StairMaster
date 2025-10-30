# StairMaster - Step Counter App

A modern Android step counter application with beautiful UI/UX design.

## Features

- Real-time step counting using device sensors
- Progress tracking with visual progress bar
- Daily goal of 10,000 steps
- Animated step icon with bounce effects
- Motivational messages based on progress
- Material Design 3 interface
- Clean, modern card-based layout
- Reset counter functionality

## Building the APK

### Prerequisites

- Android Studio or Android SDK with Build Tools
- Java Development Kit (JDK) 8 or higher
- Internet connection for downloading dependencies

### Build Instructions

#### Option 1: Using Android Studio

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the StairMaster directory
4. Wait for Gradle sync to complete
5. Go to `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
6. The APK will be generated in `app/build/outputs/apk/debug/`

#### Option 2: Using Command Line

```bash
# Navigate to project directory
cd /path/to/StairMaster

# Make gradlew executable (Unix/Mac)
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# Build release APK (unsigned)
./gradlew assembleRelease

# The APK will be in: app/build/outputs/apk/
```

#### Option 3: Using Gradle Directly

```bash
# Navigate to project directory
cd /path/to/StairMaster

# Create Gradle wrapper
gradle wrapper --gradle-version 8.2

# Build APK
./gradlew assembleDebug
```

### Output Location

After successful build, find your APK at:
- **Debug APK**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK**: `app/build/outputs/apk/release/app-release-unsigned.apk`

## Installation

### Install on Android Device

```bash
# Using ADB (Android Debug Bridge)
adb install app/build/outputs/apk/debug/app-debug.apk
```

Or transfer the APK to your device and install manually.

## Permissions

The app requires the following permission:
- `ACTIVITY_RECOGNITION` - For accessing step detection sensor

## Technical Details

### Architecture
- **Language**: Java
- **Min SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 34 (Android 14)
- **Build Tools**: Gradle 8.2

### Dependencies
- AndroidX AppCompat 1.6.1
- ConstraintLayout 2.1.4
- Material Design Components 1.11.0
- CardView 1.0.0

### UI Components
- ConstraintLayout for responsive design
- CardView with elevation and rounded corners
- Material Design button
- Custom progress drawable
- Vector drawable step icon
- Custom color scheme

## Project Structure

```
StairMaster/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/staircounter/
│   │       │   └── MainActivity.java
│   │       └── res/
│   │           ├── drawable/
│   │           │   ├── ic_steps.xml
│   │           │   └── progress_drawable.xml
│   │           ├── layout/
│   │           │   └── activity_main.xml
│   │           └── values/
│   │               └── colors.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
├── gradle.properties
└── AndroidManifest.xml
```

## Troubleshooting

### Gradle Build Fails
- Ensure you have internet connection for first build
- Check that ANDROID_HOME environment variable is set
- Verify Java JDK is installed and configured

### Sensor Not Available
- The app requires a device with a step detection sensor
- Some emulators may not support step sensors
- Test on a real Android device for best results

### Permission Denied
- Ensure you grant ACTIVITY_RECOGNITION permission
- On Android 10+, this permission must be granted at runtime

## License

This project is open source and available for educational purposes.

## Credits

Enhanced UI/UX design implemented with Material Design principles.
