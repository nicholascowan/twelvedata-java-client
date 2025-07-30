# Version Management Guide

This document explains how to manage versions for the TwelveData Java Client project using Maven standards and GitHub Actions automation.

## Version Strategy

### SNAPSHOT Versions
- Used during development
- Format: `X.Y.Z-SNAPSHOT` (e.g., `1.0.2-SNAPSHOT`)
- Automatically deployed to GitHub Package Registry on push to main
- Can be overwritten (not immutable)

### Release Versions
- Used for official releases
- Format: `X.Y.Z` (e.g., `1.0.2`)
- Deployed to GitHub Package Registry
- Immutable once published

## Automated Workflow

### 1. SNAPSHOT Builds (Automatic)
- **Trigger**: Push to `main` branch
- **Action**: Automatically builds and deploys SNAPSHOT version
- **Profile**: `snapshot`
- **Repository**: GitHub Package Registry

### 2. Release Builds (Manual)
- **Trigger**: Create GitHub Release or manual workflow dispatch
- **Action**: Builds and deploys release version
- **Profile**: `github`
- **Repository**: GitHub Package Registry

## How to Release

### Option 1: GitHub Release (Recommended)
1. **Create a GitHub Release**:
   ```bash
   gh release create v1.0.2 --title "Release v1.0.2" --notes "Release notes here"
   ```
2. **GitHub Actions automatically**:
   - Extracts version from tag (`v1.0.2` → `1.0.2`)
   - Updates `pom.xml` version
   - Builds and deploys to GitHub Package Registry
   - Creates next SNAPSHOT version (`1.0.3-SNAPSHOT`)
   - Commits and pushes the new SNAPSHOT version

### Option 2: Manual Workflow Dispatch
1. **Go to GitHub Actions** → **Maven Build and Publish** → **Run workflow**
2. **Enter version** (e.g., `1.0.2`)
3. **GitHub Actions automatically**:
   - Updates `pom.xml` version
   - Builds and deploys to GitHub Package Registry
   - Creates Git tag and GitHub Release
   - Creates next SNAPSHOT version
   - Commits and pushes changes

## Maven Profiles

### `snapshot` Profile (Default)
- Activated by default
- Deploys SNAPSHOT versions to GitHub Package Registry
- Allows overwriting of artifacts

### `github` Profile
- Deploys release versions to GitHub Package Registry
- Used for official releases

### `release` Profile
- Deploys to Maven Central (OSS Sonatype)
- Includes GPG signing
- Requires additional setup

## Distribution Management

### SNAPSHOT Repository
- URL: `https://maven.pkg.github.com/nicholascowan/twelvedata-java-client`
- Used for development builds

### Release Repository
- URL: `https://maven.pkg.github.com/nicholascowan/twelvedata-java-client`
- Used for official releases

## Development Workflow

### 1. Development (SNAPSHOT)
```bash
# Make changes to your code
git add .
git commit -m "Add new feature"
git push origin main

# GitHub Actions automatically:
# - Builds and deploys SNAPSHOT version
# - Runs tests
# - Uploads artifacts
```

### 2. Release Process
```bash
# Create GitHub Release
gh release create v1.0.2 --title "Release v1.0.2" --notes "New features and bug fixes"

# GitHub Actions automatically:
# - Updates version to 1.0.2
# - Builds and deploys release
# - Creates next SNAPSHOT (1.0.3-SNAPSHOT)
# - Commits and pushes changes
```

## Best Practices

1. **Always work on SNAPSHOT versions**
   - SNAPSHOT versions are automatically deployed on push to main
   - Prevents conflicts with release versions

2. **Use semantic versioning**
   - `MAJOR.MINOR.PATCH` format
   - Increment appropriately for changes

3. **Create GitHub Releases for all official versions**
   - Use `gh release create` command
   - Include meaningful release notes

4. **Let GitHub Actions handle version management**
   - No manual version updates needed
   - Automatic SNAPSHOT → Release → Next SNAPSHOT cycle

## Example Workflow

```bash
# Current version: 1.0.2-SNAPSHOT

# 1. Development work
git add .
git commit -m "Add new feature"
git push origin main
# → GitHub Actions deploys 1.0.2-SNAPSHOT

# 2. Ready for release
gh release create v1.0.2 --title "Release v1.0.2" --notes "New feature added"
# → GitHub Actions:
#    - Updates version to 1.0.2
#    - Deploys release
#    - Creates next SNAPSHOT (1.0.3-SNAPSHOT)
#    - Commits and pushes changes

# 3. Continue development
# → Version is now 1.0.3-SNAPSHOT automatically
```

## Troubleshooting

### Version Conflicts
If you encounter version conflicts:
1. Check current version in `pom.xml`
2. Ensure you're using the correct profile
3. Clean local Maven cache if needed: `mvn clean`

### Deployment Issues
- Ensure you have proper GitHub token permissions
- Check that the repository is properly configured
- Verify Maven settings in `.github/maven/settings.xml`

### Manual Version Updates
If you need to manually update the version:
```bash
# Update pom.xml version
sed -i "s/<version>.*<\/version>/<version>1.0.2-SNAPSHOT<\/version>/" pom.xml

# Commit and push
git add pom.xml
git commit -m "Update version to 1.0.2-SNAPSHOT"
git push origin main
```

## Benefits of This Approach

1. **Maven Standards**: Follows Maven conventions for version management
2. **GitHub Integration**: Leverages GitHub Actions for automation
3. **No Scripts Required**: Everything is handled by Maven and GitHub Actions
4. **Automatic Workflow**: SNAPSHOT → Release → Next SNAPSHOT cycle is automatic
5. **Version Consistency**: Version is always managed in `pom.xml`
6. **CI/CD Integration**: Builds, tests, and deploys automatically 