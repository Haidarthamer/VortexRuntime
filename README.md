# VORTEX Runtime

**An Android-native runtime management platform — early development.**

VORTEX is being built as a modular Android application for managing compatible, legally owned game-runtime installations. The first milestone is a real native Android app with device capability detection, a runtime integration boundary, and a reproducible APK build.

> **Current status:** foundation only. This repository does not contain GTA V, proprietary game files, a game engine, DXVK binaries, or a working GTA V runtime. The runtime and multiplayer screens are honest integration placeholders until compatible, redistributable components and verified engine interfaces are available.

## Initial targets

- Native Android UI: Kotlin + Jetpack Compose + Material 3
- Android 8.0+ (API 26+), ARM64-first
- Device profile: ABI, Android version, memory, Vulkan feature availability
- Runtime integration through a documented adapter interface
- Future modules: graphics configuration, controller mapping, file validation, diagnostics, and multiplayer integration where supported by the underlying runtime

## Build

Requires JDK 17, Android SDK Platform 35, and Gradle 8.11.1.

```bash
gradle :app:assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

GitHub Actions builds the debug APK on pushes and pull requests.

## Important technical boundary

A launcher cannot create a proprietary game engine or make an incompatible binary run. VORTEX will only integrate runtime components that are legally usable, compatible with the target Android ABI/API, and expose documented interfaces. No game assets or copyrighted binaries are bundled.

See [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) and [docs/RUNTIME_INTEGRATION.md](docs/RUNTIME_INTEGRATION.md).
