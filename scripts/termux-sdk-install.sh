#!/data/data/com.termux/files/usr/bin/bash
# language: bash | file: termux-sdk-install.sh | runtime: Termux | target: Android phone
# Installs OpenJDK 17 + Android command-line SDK (platform 34, build-tools 34.0.0)
# Run: bash termux-sdk-install.sh

set -euo pipefail

PREFIX="${PREFIX:-/data/data/com.termux/files/usr}"
HOME_DIR="${HOME:-$PREFIX/home}"
SDK_ROOT="${ANDROID_SDK_ROOT:-$HOME_DIR/android-sdk}"
CMDLINE_VERSION="11076708"   # update if Google bumps the zip
CMDLINE_ZIP="commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
CMDLINE_URL="https://dl.google.com/android/repository/${CMDLINE_ZIP}"
PLATFORM="platforms;android-34"
BUILD_TOOLS="build-tools;34.0.0"
PLATFORM_TOOLS="platform-tools"

log()  { printf '\033[1;36m[vibe]\033[0m %s\n' "$*"; }
ok()   { printf '\033[1;32m[ok]\033[0m %s\n' "$*"; }
warn() { printf '\033[1;33m[warn]\033[0m %s\n' "$*"; }
die()  { printf '\033[1;31m[fail]\033[0m %s\n' "$*"; exit 1; }

need_pkg() {
  local p="$1"
  if ! command -v "$p" >/dev/null 2>&1; then
    log "installing package: $p"
    pkg install -y "$p" || die "pkg install $p failed"
  fi
}

log "Termux Android SDK installer"
log "SDK_ROOT=$SDK_ROOT"

# ── base packages ──────────────────────────────────────────────
pkg update -y
need_pkg curl
need_pkg wget
need_pkg unzip
need_pkg openjdk-17
need_pkg which
need_pkg coreutils

# ── directories ────────────────────────────────────────────────
mkdir -p "$SDK_ROOT/cmdline-tools"
cd "$SDK_ROOT"

# ── download command-line tools ────────────────────────────────
if [ ! -f "$CMDLINE_ZIP" ]; then
  log "downloading $CMDLINE_URL"
  if ! curl -fL --retry 3 -o "$CMDLINE_ZIP" "$CMDLINE_URL"; then
    die "download failed — check network or update CMDLINE_VERSION"
  fi
else
  log "zip already present, skipping download"
fi

# ── extract ────────────────────────────────────────────────────
log "extracting command-line tools"
rm -rf cmdline-tools/latest cmdline-tools/cmdline-tools
unzip -q -o "$CMDLINE_ZIP" -d cmdline-tools
if [ -d cmdline-tools/cmdline-tools ]; then
  mv cmdline-tools/cmdline-tools cmdline-tools/latest
fi
[ -x cmdline-tools/latest/bin/sdkmanager ] || die "sdkmanager not found after extract"

# ── env ────────────────────────────────────────────────────────
export JAVA_HOME="$PREFIX/opt/openjdk"
# fallback if layout differs
[ -d "$JAVA_HOME" ] || export JAVA_HOME="$(dirname "$(dirname "$(readlink -f "$(command -v java)")")")"
export ANDROID_HOME="$SDK_ROOT"
export ANDROID_SDK_ROOT="$SDK_ROOT"
export PATH="$PATH:$SDK_ROOT/cmdline-tools/latest/bin:$SDK_ROOT/platform-tools"

log "JAVA_HOME=$JAVA_HOME"
java -version || die "java not runnable"

# ── licenses + packages ────────────────────────────────────────
log "accepting licenses"
yes | sdkmanager --sdk_root="$SDK_ROOT" --licenses >/tmp/sdk-licenses.log 2>&1 || true

log "installing $PLATFORM_TOOLS $PLATFORM $BUILD_TOOLS"
sdkmanager --sdk_root="$SDK_ROOT" \
  "$PLATFORM_TOOLS" \
  "$PLATFORM" \
  "$BUILD_TOOLS" || die "sdkmanager install failed"

# ── write env file ─────────────────────────────────────────────
ENV_FILE="$HOME_DIR/.vibe-android-env"
cat > "$ENV_FILE" <<EOF
# sourced by VIBE / Termux builds
export JAVA_HOME="$JAVA_HOME"
export ANDROID_HOME="$SDK_ROOT"
export ANDROID_SDK_ROOT="$SDK_ROOT"
export PATH="\$PATH:\$ANDROID_HOME/cmdline-tools/latest/bin:\$ANDROID_HOME/platform-tools:\$ANDROID_HOME/build-tools/34.0.0"
EOF

ok "SDK installed under $SDK_ROOT"
ok "env file: $ENV_FILE"
log "add to ~/.bashrc :"
echo "  source $ENV_FILE"

# ── verify ─────────────────────────────────────────────────────
log "verification"
sdkmanager --sdk_root="$SDK_ROOT" --list_installed 2>/dev/null | head -20 || true
[ -d "$SDK_ROOT/platforms/android-34" ] && ok "platform-34 present" || warn "platform-34 missing"
[ -d "$SDK_ROOT/build-tools/34.0.0" ] && ok "build-tools 34.0.0 present" || warn "build-tools missing"

log "done. example project build:"
echo "  source $ENV_FILE"
echo "  cd /path/to/project"
echo "  echo \"sdk.dir=$SDK_ROOT\" > local.properties"
echo "  ./gradlew assembleDebug"
