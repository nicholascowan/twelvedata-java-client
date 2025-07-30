# Version Management Guide

This document explains how to manage versions for the TwelveData Java Client project using SNAPSHOT versions for development and official releases.

## Version Strategy

### SNAPSHOT Versions
- Used during development
- Format: `X.Y.Z-SNAPSHOT` (e.g., `1.0.2-SNAPSHOT`)
- Deployed to GitHub Package Registry for testing
- Can be overwritten (not immutable)

### Release Versions
- Used for official releases
- Format: `X.Y.Z` (e.g., `1.0.2`)
- Deployed to GitHub Package Registry
- Immutable once published

## Version Management Script

We provide a convenient script to manage versions: `scripts/version.sh`

### Usage

```bash
# Show current version
./scripts/version.sh show

# Create SNAPSHOT version
./scripts/version.sh snapshot 1.0.2

# Create release version
./scripts/version.sh release 1.0.2

# Bump version automatically
./scripts/version.sh bump patch  # 1.0.1 -> 1.0.2
./scripts/version.sh bump minor  # 1.0.1 -> 1.1.0
./scripts/version.sh bump major  # 1.0.1 -> 2.0.0

# Show help
./scripts/version.sh help
```

## Development Workflow

### 1. Start Development (SNAPSHOT)

```bash
# Create a new SNAPSHOT version for development
./scripts/version.sh snapshot 1.0.2

# Build and deploy SNAPSHOT
mvn clean deploy -P snapshot

# Commit and push changes
git add pom.xml
git commit -m "Bump to 1.0.2-SNAPSHOT for development"
git push origin main
```

### 2. Release Process

```bash
# Create release version (removes SNAPSHOT suffix)
./scripts/version.sh release 1.0.2

# Build and deploy release
mvn clean deploy -P github

# Create GitHub release
gh release create v1.0.2 --title "Release v1.0.2" --notes "Release notes here"

# Start next development cycle
./scripts/version.sh snapshot 1.0.3
```

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

## Best Practices

1. **Always use SNAPSHOT for development**
   - Prevents conflicts with release versions
   - Allows iterative development

2. **Bump version before starting new development**
   - Use `./scripts/version.sh bump patch` for bug fixes
   - Use `./scripts/version.sh bump minor` for new features
   - Use `./scripts/version.sh bump major` for breaking changes

3. **Test SNAPSHOT before release**
   - Deploy SNAPSHOT and test in dependent projects
   - Ensure everything works before creating release

4. **Create GitHub releases for all official versions**
   - Tag releases with `vX.Y.Z` format
   - Include release notes and changelog

## Example Workflow

```bash
# Current version: 1.0.1

# Start development for next version
./scripts/version.sh snapshot 1.0.2
mvn clean deploy -P snapshot
git add pom.xml && git commit -m "Bump to 1.0.2-SNAPSHOT" && git push

# Development work...
# Test with SNAPSHOT...

# Ready for release
./scripts/version.sh release 1.0.2
mvn clean deploy -P github
gh release create v1.0.2 --title "Release v1.0.2" --notes "Bug fixes and improvements"

# Start next development cycle
./scripts/version.sh snapshot 1.0.3
```

## Troubleshooting

### Version Conflicts
If you encounter version conflicts:
1. Check current version: `./scripts/version.sh show`
2. Ensure you're using the correct profile
3. Clean local Maven cache if needed: `mvn clean`

### Deployment Issues
- Ensure you have proper GitHub token permissions
- Check that the repository is properly configured
- Verify Maven settings in `.github/maven/settings.xml` 