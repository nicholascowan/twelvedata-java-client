# Release Checklist

Use this checklist when preparing a new release of the TwelveData Java Client.

## Pre-Release Tasks

- [ ] **Update version** in `pom.xml`
  ```bash
  ./scripts/update-version.sh 1.0.1
  ```
- [ ] **Update CHANGELOG.md** (if it exists) with new features, fixes, and breaking changes
- [ ] **Run tests** to ensure everything works
  ```bash
  mvn clean test
  ```
- [ ] **Build the project** to verify packaging
  ```bash
  mvn clean package
  ```
- [ ] **Test the example** to ensure it works
  ```bash
  mvn exec:java
  ```

## Release Process

### 1. Create GitHub Release

1. **Create a new release** on GitHub:
   - Go to repository → Releases → "Create a new release"
   - Tag version: `v1.0.1` (must match pom.xml version)
   - Release title: `Release v1.0.1`
   - Description: Include changelog and notable changes
   - Mark as "Latest release"

2. **Publish the release** - This triggers automated workflows

### 2. Verify Automated Publishing

After publishing the release, verify:

- [ ] **GitHub Package Registry**:
  - Check https://github.com/twelvedata/twelvedata-java-client/packages
  - Verify new version appears
  - Test installation from GitHub Packages

- [ ] **Maven Central**:
  - Check https://search.maven.org/ for the new version
  - Verify artifacts are signed and uploaded
  - Test installation from Maven Central

### 3. Post-Release Tasks

- [ ] **Update documentation** if needed
- [ ] **Announce the release** on relevant channels
- [ ] **Monitor for issues** in the first few days after release
- [ ] **Update version** to next snapshot (e.g., `1.0.2-SNAPSHOT`) for development

## Troubleshooting

### If GitHub Package Registry fails:
- Check GitHub Actions logs
- Verify `GITHUB_TOKEN` has correct permissions
- Ensure repository settings allow package publishing

### If Maven Central fails:
- Check GitHub Actions logs
- Verify OSSRH credentials in secrets
- Ensure GPG key is properly configured
- Check Sonatype OSSRH status

### If tests fail:
- Fix issues before releasing
- Re-run tests after fixes
- Consider if issues warrant a new version

## Version Numbering

Follow semantic versioning (SemVer):
- **MAJOR.MINOR.PATCH**
- **MAJOR**: Breaking changes
- **MINOR**: New features (backward compatible)
- **PATCH**: Bug fixes (backward compatible)

Examples:
- `1.0.0` → `1.0.1` (patch)
- `1.0.1` → `1.1.0` (minor)
- `1.1.0` → `2.0.0` (major)

## Security Considerations

- [ ] **Dependencies**: Ensure all dependencies are up to date
- [ ] **Vulnerabilities**: Check for known security vulnerabilities
- [ ] **Signing**: Verify GPG signing is working correctly
- [ ] **Secrets**: Ensure no secrets are exposed in the release 