# Version Management

This document describes how to manage versions in the TwelveData Java Client project.

## Overview

The project includes automated version management tools to help maintain consistent versioning across releases. We use semantic versioning (SemVer) with the format `MAJOR.MINOR.PATCH`.

## Version Bump Options

### 1. GitHub Actions Workflow (Recommended)

The easiest way to bump versions is using the GitHub Actions workflow:

1. Go to your repository on GitHub
2. Navigate to **Actions** tab
3. Select **"Bump Maven Version"** workflow
4. Click **"Run workflow"**
5. Choose your options:
   - **Version bump type**: `patch`, `minor`, or `major`
   - **Custom version**: Optional specific version (overrides bump type)
   - **Create branch**: Whether to create a new branch for the change

#### Workflow Features

- **Automated version calculation** based on semantic versioning
- **Branch creation** with automatic pull request
- **Test execution** to ensure changes don't break anything
- **Build verification** to confirm the project builds successfully
- **Detailed summary** of the version bump process

### 2. Local Script

For local development, you can use the provided bash script:

```bash
# Make sure the script is executable
chmod +x scripts/bump-version.sh

# Bump patch version (0.0.1 -> 0.0.2)
./scripts/bump-version.sh patch

# Bump minor version (0.0.1 -> 0.1.0)
./scripts/bump-version.sh minor

# Bump major version (0.0.1 -> 1.0.0)
./scripts/bump-version.sh major

# Set specific version
./scripts/bump-version.sh 1.2.3
```

#### Script Features

- **Colored output** for better readability
- **Version validation** to ensure proper format
- **Automatic testing** after version update
- **Build verification** to confirm everything works
- **Helpful commit instructions** at the end

## Version Bump Types

### Patch (0.0.1 → 0.0.2)
- Bug fixes
- Minor improvements
- Documentation updates
- No breaking changes

### Minor (0.0.1 → 0.1.0)
- New features
- Backward-compatible enhancements
- Deprecated features (but still functional)

### Major (0.0.1 → 1.0.0)
- Breaking changes
- Major API changes
- Incompatible updates

## Manual Version Update

If you need to manually update the version, edit the `pom.xml` file:

```xml
<version>0.0.1</version>
```

Change to your desired version, then run:

```bash
mvn clean test package
```

## Best Practices

1. **Always test** after version changes
2. **Use semantic versioning** consistently
3. **Document breaking changes** in release notes
4. **Create releases** on GitHub after version bumps
5. **Tag releases** with the version number

## Release Process

1. **Bump version** using GitHub Actions or local script
2. **Review changes** in the pull request (if using GitHub Actions)
3. **Merge changes** to main branch
4. **Create a release** on GitHub
5. **Tag the release** with the version number
6. **Publish to repositories** (GitHub Package Registry and/or Maven Central)

## Troubleshooting

### Common Issues

1. **Version not updating**: Make sure you're editing the correct `pom.xml` file
2. **Tests failing**: Check for any version-specific issues in your code
3. **Build errors**: Verify all dependencies are compatible with the new version
4. **GitHub Actions failing**: Check the workflow logs for specific error messages

### Getting Help

- Check the GitHub Actions logs for detailed error information
- Review the script output for any validation errors
- Ensure you have the necessary permissions to push to the repository

## Related Files

- `.github/workflows/bump-version.yml` - GitHub Actions workflow
- `scripts/bump-version.sh` - Local version bump script
- `pom.xml` - Maven project configuration
- `PUBLISHING.md` - Publishing guide
- `RELEASE_CHECKLIST.md` - Release preparation checklist 