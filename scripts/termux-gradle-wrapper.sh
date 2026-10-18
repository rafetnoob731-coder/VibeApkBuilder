#!/data/data/com.termux/files/usr/bin/bash
# language: bash | file: termux-gradle-wrapper.sh | runtime: Termux | target: Android phone
# Ensures Gradle Wrapper exists and builds debug APK.
# Usage:
#   bash termux-gradle-wrapper.sh /path/to/android/project
#   bash termux-gradle-wrapper.sh /path/to/android/project assembleRelease

set -euo pipefail

PROJECT_DIR="${1:-.}"
TASK="${2:-assembleDebug}"
GRADLE_VERSION="${GRADLE_VERSION:-8.9}"
ENV_FILE="${HOME}/.vibe-android-env"

log()  { printf '\033[1;36m[vibe]\033[0m %s\n' "$*"; }
ok()   { printf '\033[1;32m[ok]\033[0m %s\n' "$*"; }
die()  { printf '\033[1;31m[fail]\033[0m %s\n' "$*"; exit 1; }

[ -d "$PROJECT_DIR" ] || die "project dir not found: $PROJECT_DIR"
cd "$PROJECT_DIR"
PROJECT_DIR="$(pwd)"

# load SDK env if present
if [ -f "$ENV_FILE" ]; then
  # shellcheck disable=SC1090
  source "$ENV_FILE"
  log "loaded $ENV_FILE"
else
  log "no $ENV_FILE — run termux-sdk-install.sh first if build fails"
fi

# local.properties
SDK_DIR="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-$HOME/android-sdk}}"
if [ ! -f local.properties ]; then
  echo "sdk.dir=$SDK_DIR" > local.properties
  log "wrote local.properties → sdk.dir=$SDK_DIR"
else
  log "local.properties exists"
fi

# ensure wrapper
if [ ! -f gradlew ]; then
  log "no gradlew — generating wrapper (Gradle $GRADLE_VERSION)"
  if command -v gradle >/dev/null 2>&1; then
    gradle wrapper --gradle-version "$GRADLE_VERSION"
  else
    # bootstrap via official wrapper jar distribution
    mkdir -p gradle/wrapper
    WRAPPER_JAR_URL="https://raw.githubusercontent.com/gradle/gradle/v${GRADLE_VERSION}.0/gradle/wrapper/gradle-wrapper.jar"
    # fallback: use gradle installed via pkg if possible
    pkg install -y gradle 2>/dev/null || true
    if command -v gradle >/dev/null 2>&1; then
      gradle wrapper --gradle-version "$GRADLE_VERSION"
    else
      die "gradle not available — pkg install gradle  or install SDK script first"
    fi
  fi
fi

chmod +x gradlew 2>/dev/null || true

# memory for phone
export GRADLE_OPTS="${GRADLE_OPTS:--Xmx1536m -Dfile.encoding=UTF-8}"
export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS:--Xmx1536m}"

log "running ./gradlew $TASK"
./gradlew "$TASK" --no-daemon --stacktrace

# locate APK
APK=$(find app/build/outputs/apk -name "*.apk" 2>/dev/null | head -1 || true)
if [ -n "${APK:-}" ]; then
  ok "APK: $PROJECT_DIR/$APK"
  ls -lh "$APK"
  # optional copy to shared storage
  if [ -d /sdcard/Download ]; then
    cp "$APK" /sdcard/Download/ || true
    ok "copied to /sdcard/Download/"
  fi
else
  log "no APK found under app/build/outputs/apk (task may not produce one)"
fi
