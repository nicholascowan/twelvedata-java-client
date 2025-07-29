# Publishing Guide

This document explains how to publish the TwelveData Java Client to both GitHub Package Registry and Maven Central.

## Prerequisites

### For GitHub Package Registry
- GitHub repository with proper permissions
- GitHub Personal Access Token with `write:packages` scope

### For Maven Central
- Sonatype OSSRH account (https://oss.sonatype.org/)
- GPG key for signing artifacts
- Verified group ID ownership

## Setup

### 1. GitHub Package Registry Setup

1. Create a GitHub Personal Access Token:
   - Go to GitHub Settings → Developer settings → Personal access tokens
   - Generate a new token with `write:packages` scope
   - Copy the token

2. Configure Maven settings locally (optional, for manual publishing):
   ```xml
   <servers>
     <server>
       <id>github</id>
       <username>YOUR_GITHUB_USERNAME</username>
       <password>YOUR_GITHUB_TOKEN</password>
     </server>
   </servers>
   ```

### 2. Maven Central Setup

1. **Sonatype OSSRH Account**:
   - Sign up at https://oss.sonatype.org/
   - Create a JIRA ticket to request group ID ownership
   - Wait for approval

2. **GPG Key Setup**:
   ```bash
   # Generate GPG key
   gpg --gen-key
   
   # Export public key
   gpg --armor --export YOUR_EMAIL > public-key.asc
   
   # Export private key (for CI/CD)
   gpg --armor --export-secret-key YOUR_EMAIL > private-key.asc
   ```

3. **Upload GPG Key**:
   - Upload your public key to https://keyserver.ubuntu.com/
   - Or use: `gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID`

4. **Configure Maven settings locally** (optional, for manual publishing):
   ```xml
   <servers>
     <server>
       <id>ossrh</id>
       <username>YOUR_OSSRH_USERNAME</username>
       <password>YOUR_OSSRH_PASSWORD</password>
     </server>
   </servers>
   
   <profiles>
     <profile>
       <id>gpg</id>
       <properties>
         <gpg.executable>gpg</gpg.executable>
         <gpg.passphrase>YOUR_GPG_PASSPHRASE</gpg.passphrase>
       </properties>
     </profile>
   </profiles>
   
   <activeProfiles>
     <activeProfile>gpg</activeProfile>
   </activeProfiles>
   ```

## GitHub Secrets Configuration

Add the following secrets to your GitHub repository:

### For GitHub Package Registry
- `GITHUB_TOKEN` (automatically provided by GitHub Actions)

### For Maven Central
- `OSSRH_USERNAME`: Your Sonatype OSSRH username
- `OSSRH_PASSWORD`: Your Sonatype OSSRH password
- `GPG_PRIVATE_KEY`: Your GPG private key (content of private-key.asc)
- `GPG_PASSPHRASE`: Your GPG key passphrase

## Publishing Process

### Automated Publishing (Recommended)

1. **Create a Release**:
   - Go to GitHub repository → Releases
   - Click "Create a new release"
   - Tag version (e.g., `v1.0.0`)
   - Set release title and description
   - Publish the release

2. **Workflows will automatically**:
   - Run tests
   - Build the project
   - Publish to GitHub Package Registry
   - Publish to Maven Central (if configured)

### Manual Publishing

#### To GitHub Package Registry
```bash
mvn clean deploy -P github -s .github/maven/settings.xml
```

#### To Maven Central
```bash
mvn clean deploy -P release -s .github/maven/settings.xml
```

## Version Management

### Snapshot Releases
- Use version format: `1.0.0-SNAPSHOT`
- Snapshots are automatically published to OSSRH snapshots repository
- Use: `mvn clean deploy -P release -s .github/maven/settings.xml`

### Release Versions
- Use version format: `1.0.0` (without -SNAPSHOT)
- Releases are published to both GitHub Package Registry and Maven Central
- Update version in `pom.xml` before creating a GitHub release

## Using the Published Library

### From GitHub Package Registry
```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://maven.pkg.github.com/nicholascowan/twelvedata-java-client</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.nicholascowan.twelvedata</groupId>
  <artifactId>twelvedata-java-client</artifactId>
  <version>1.0.0</version>
</dependency>
```

### From Maven Central
```xml
<dependency>
  <groupId>com.github.nicholascowan.twelvedata</groupId>
  <artifactId>twelvedata-java-client</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Troubleshooting

### Common Issues

1. **Authentication Errors**:
   - Verify GitHub token has correct permissions
   - Check OSSRH credentials
   - Ensure GPG key is properly configured

2. **GPG Signing Issues**:
   - Verify GPG key is available in CI environment
   - Check passphrase is correct
   - Ensure GPG key is uploaded to key server

3. **Version Conflicts**:
   - Ensure version doesn't already exist in repositories
   - Use unique version numbers for releases
   - Check for snapshot vs release version conflicts

### Support

For issues with:
- **GitHub Package Registry**: Check GitHub Actions logs and repository settings
- **Maven Central**: Check OSSRH status and Sonatype support
- **Build Issues**: Review Maven logs and dependency conflicts

## Security Notes

- Never commit secrets to the repository
- Use GitHub Secrets for all sensitive information
- Rotate GPG keys and tokens regularly
- Keep dependencies updated for security patches 