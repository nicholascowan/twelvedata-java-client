#!/bin/bash

# Version management script for TwelveData Java Client
# Usage: ./scripts/version.sh [command] [version]

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
POM_FILE="$PROJECT_DIR/pom.xml"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to get current version from pom.xml
get_current_version() {
    grep -oP '<version>\K[^<]+' "$POM_FILE" | head -1
}

# Function to set version in pom.xml
set_version() {
    local version="$1"
    print_info "Setting version to: $version"
    
    # Update version in pom.xml
    sed -i "s/<version>.*<\/version>/<version>$version<\/version>/" "$POM_FILE"
    
    print_success "Version updated to: $version"
}

# Function to create SNAPSHOT version
create_snapshot() {
    local base_version="$1"
    local snapshot_version="${base_version}-SNAPSHOT"
    
    print_info "Creating SNAPSHOT version: $snapshot_version"
    set_version "$snapshot_version"
    
    print_success "SNAPSHOT version created: $snapshot_version"
    print_info "You can now build and deploy SNAPSHOT with: mvn clean deploy -P snapshot"
}

# Function to create release version
create_release() {
    local base_version="$1"
    
    print_info "Creating release version: $base_version"
    set_version "$base_version"
    
    print_success "Release version created: $base_version"
    print_info "You can now build and deploy release with: mvn clean deploy -P github"
}

# Function to bump version
bump_version() {
    local current_version=$(get_current_version)
    local version_type="$1"
    
    # Remove SNAPSHOT suffix if present
    local base_version=$(echo "$current_version" | sed 's/-SNAPSHOT$//')
    
    # Parse version components
    IFS='.' read -ra VERSION_PARTS <<< "$base_version"
    local major="${VERSION_PARTS[0]}"
    local minor="${VERSION_PARTS[1]}"
    local patch="${VERSION_PARTS[2]}"
    
    case "$version_type" in
        "major")
            major=$((major + 1))
            minor=0
            patch=0
            ;;
        "minor")
            minor=$((minor + 1))
            patch=0
            ;;
        "patch")
            patch=$((patch + 1))
            ;;
        *)
            print_error "Invalid version type. Use: major, minor, or patch"
            exit 1
            ;;
    esac
    
    local new_version="$major.$minor.$patch"
    
    if [[ "$current_version" == *"-SNAPSHOT" ]]; then
        create_snapshot "$new_version"
    else
        create_release "$new_version"
    fi
}

# Function to show current version
show_version() {
    local current_version=$(get_current_version)
    print_info "Current version: $current_version"
}

# Function to show help
show_help() {
    echo "Version Management Script for TwelveData Java Client"
    echo ""
    echo "Usage: $0 [command] [version]"
    echo ""
    echo "Commands:"
    echo "  snapshot <version>    Create SNAPSHOT version (e.g., 1.0.2-SNAPSHOT)"
    echo "  release <version>     Create release version (e.g., 1.0.2)"
    echo "  bump <type>          Bump version (major|minor|patch)"
    echo "  show                 Show current version"
    echo "  help                 Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 snapshot 1.0.2    # Creates 1.0.2-SNAPSHOT"
    echo "  $0 release 1.0.2     # Creates 1.0.2"
    echo "  $0 bump patch        # Bumps patch version"
    echo "  $0 bump minor        # Bumps minor version"
    echo "  $0 bump major        # Bumps major version"
}

# Main script logic
case "$1" in
    "snapshot")
        if [[ -z "$2" ]]; then
            print_error "Version required for snapshot command"
            exit 1
        fi
        create_snapshot "$2"
        ;;
    "release")
        if [[ -z "$2" ]]; then
            print_error "Version required for release command"
            exit 1
        fi
        create_release "$2"
        ;;
    "bump")
        if [[ -z "$2" ]]; then
            print_error "Version type required for bump command (major|minor|patch)"
            exit 1
        fi
        bump_version "$2"
        ;;
    "show")
        show_version
        ;;
    "help"|"--help"|"-h")
        show_help
        ;;
    *)
        print_error "Unknown command: $1"
        echo ""
        show_help
        exit 1
        ;;
esac 