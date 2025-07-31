# Contributing to TwelveData Java Client

Thank you for your interest in contributing to the TwelveData Java Client! This document provides guidelines and information for contributors.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Coding Standards](#coding-standards)
- [Testing](#testing)
- [Pull Request Process](#pull-request-process)
- [Version Management](#version-management)
- [Reporting Issues](#reporting-issues)

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher (or use the included Maven wrapper: `./mvnw`)
- Git

### Development Setup

1. **Fork the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/twelvedata-java-client.git
   cd twelvedata-java-client
   ```

2. **Set up your development environment**
   ```bash
   # Use Maven wrapper for consistent builds
   ./mvnw clean install
   ```

3. **Run tests to ensure everything works**
   ```bash
   ./mvnw test
   ```

## Coding Standards

### Java Code Style

- Follow the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Add comprehensive JavaDoc for all public APIs
- Keep methods focused and under 50 lines when possible
- Use meaningful commit messages

### Code Organization

- Place new classes in appropriate packages
- Follow the existing package structure:
  - `com.github.nicholascowan.twelvedata` - Main client classes
  - `com.github.nicholascowan.twelvedata.config` - Configuration classes
  - `com.github.nicholascowan.twelvedata.endpoints` - API endpoint classes
  - `com.github.nicholascowan.twelvedata.models` - Data models
  - `com.github.nicholascowan.twelvedata.exceptions` - Custom exceptions
  - `com.github.nicholascowan.twelvedata.http` - HTTP client implementations

### Error Handling

- Use custom exceptions from the `exceptions` package
- Provide meaningful error messages
- Include appropriate error codes
- Document error scenarios in JavaDoc

## Testing

### Running Tests

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=TimeSeriesEndpointTest

# Run with coverage
./mvnw jacoco:report
```

### Test Standards

- Write tests for all new functionality
- Aim for at least 80% code coverage
- Use descriptive test method names
- Test both success and failure scenarios
- Mock external dependencies appropriately

### Test Structure

- Unit tests in `src/test/java`
- Integration tests in `src/test-integration/java` (if needed)
- Test resources in `src/test/resources`

## Pull Request Process

1. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes**
   - Follow coding standards
   - Add tests for new functionality
   - Update documentation if needed

3. **Run quality checks**
   ```bash
   ./mvnw clean verify
   ```

4. **Commit your changes**
   ```bash
   git add .
   git commit -m "feat: add new feature description"
   ```

5. **Push and create a pull request**
   ```bash
   git push origin feature/your-feature-name
   ```

### Pull Request Guidelines

- Provide a clear description of the changes
- Reference any related issues
- Ensure all CI checks pass
- Request review from maintainers
- Update documentation if needed

## Version Management

### Version Bumping

This project uses automated version management. When creating a release:

1. **Create a GitHub Release** - This automatically triggers the release workflow
2. **Use the Simple Release Management workflow** - For manual version control
3. **Follow semantic versioning** - MAJOR.MINOR.PATCH

### Version Types

- **SNAPSHOT versions** - Development versions (e.g., `1.0.0-SNAPSHOT`)
- **Release versions** - Stable releases (e.g., `1.0.0`)

## Reporting Issues

### Bug Reports

When reporting bugs, please include:

- **Description** - Clear description of the issue
- **Steps to reproduce** - Detailed steps to reproduce the bug
- **Expected behavior** - What you expected to happen
- **Actual behavior** - What actually happened
- **Environment** - Java version, OS, etc.
- **Code example** - Minimal code to reproduce the issue

### Feature Requests

When requesting features, please include:

- **Description** - Clear description of the feature
- **Use case** - Why this feature is needed
- **Proposed implementation** - How you think it should work
- **Alternatives considered** - Other approaches you've considered

## Getting Help

- **Documentation** - Check the README.md and other documentation files
- **Issues** - Search existing issues for similar problems
- **Discussions** - Use GitHub Discussions for questions and ideas

## License

By contributing to this project, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing to TwelveData Java Client! 🚀 