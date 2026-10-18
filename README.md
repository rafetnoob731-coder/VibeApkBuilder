# VIBE APK BUILDER

**Build • Convert • Publish • Grow**  
Universal AI App Converter & Builder

## Stack
- Kotlin 2.0 + Jetpack Compose + Material 3
- Navigation Compose, Hilt, Room, DataStore, Coil
- Target: Android 8.0+ (minSdk 26), compile/target SDK 35

## Phase 1 Status (Foundation)
- [x] Project skeleton & Gradle config
- [x] Design system (dark charcoal + violet/electric blue)
- [x] Splash screen
- [x] Dashboard (hero, quick tools, recent projects, bottom nav)
- [x] Projects list with search + filters
- [x] Import Project method selector
- [x] Navigation graph (core routes)
- [x] Domain models (Project, Inventory, DiagnosticFinding)

## Next
- Project Doctor / Analysis screen
- Real import pipeline (ZIP security, detection)
- Build Center + isolated Gradle worker
- BYOK AI provider manager
- Web → App converter

## Open in Android Studio
1. File → Open → select this folder
2. Sync Gradle
3. Run on device/emulator (API 26+)

## Notes
Real Gradle builds, SDK installs, and AI calls require a remote worker / backend service. Device-side UI is fully functional for navigation and project management flows.
