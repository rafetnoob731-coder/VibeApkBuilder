#!/data/data/com.termux/files/usr/bin/bash
# language: bash | file: termux-full-setup.sh | runtime: Termux
# One-shot: packages + SDK + env. Then use termux-gradle-wrapper.sh for builds.

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

log() { printf '\033[1;36m[vibe]\033[0m %s\n' "$*"; }

log "Termux full setup for VIBE / Android builds"
pkg update -y
pkg install -y openjdk-17 gradle curl wget unzip git which coreutils proot 2>/dev/null || pkg install -y openjdk-17 curl wget unzip git

if [ -f "$SCRIPT_DIR/termux-sdk-install.sh" ]; then
  bash "$SCRIPT_DIR/termux-sdk-install.sh"
else
  log "termux-sdk-install.sh not beside this script — skip SDK step"
fi

log "setup finished"
log "build a project with:"
echo "  bash termux-gradle-wrapper.sh /path/to/android/project"
echo "  bash termux-gradle-wrapper.sh /path/to/android/project assembleRelease"
