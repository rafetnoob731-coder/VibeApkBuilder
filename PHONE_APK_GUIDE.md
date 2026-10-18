# Convert / Work with APK on Phone — Realistic Path

## What works on phone
1. **Import existing APK** → metadata, permissions, package name, version, basic resource list.
2. **Web → App** → generate a WebView wrapper project (HTML/URL → Android shell). Templates stay on device.
3. **Edit identity** → package ID, version, icon (if provided), permissions toggles.
4. **AI analysis** (Pro) → text-level advice on logs / manifest issues you paste.
5. **Export project ZIP** → take to a PC / remote build worker for real Gradle APK/AAB.

## What does NOT work on phone alone
- Full Gradle builds with complete Android SDK + JDK (too large, OEM restrictions).
- Reliable source recovery from a compiled APK (decompile ≠ original source).
- Signing production releases with upload keys without secure key storage.

## Recommended phone workflow
1. Open VIBE → Import → Existing APK (or Web URL).
2. Review Analysis + Permissions.
3. Fix issues via AI Assistant (subscription required for auto-fix).
4. Export project / request remote build.
5. Install resulting APK via Package Installer (unknown sources / session install).

## Subscription gate
- Free: import, basic WebView convert, ads, low credit pool.
- Pro ($19.99/mo): AI features, rejection analyzer, 1000 credits ≈ $10 provider budget.
- Universe: higher limits + team history.

## License / anti-mod
- Device-bound license record in Room.
- Integrity risk score (installer, debugger).
- Server attestation recommended for production.

## Credits
1000 app credits ≈ $10 real AI provider spend (ratio configurable).
EOF
