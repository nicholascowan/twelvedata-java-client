# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Maven wrapper for consistent builds
- Comprehensive contributing guidelines
- Code of conduct
- Security policy
- Changelog documentation

### Changed
- Updated to latest stable dependency versions
- Improved project structure and documentation

## [1.0.0-SNAPSHOT] - 2025-07-30

### Added
- Initial release of TwelveData Java Client
- Support for Time Series data (OHLC)
- Support for Quote data
- Support for Price data
- Comprehensive error handling with custom exceptions
- Spring Boot integration
- Maven build configuration
- GitHub Actions CI/CD workflows
- Automated version management
- Dependabot configuration for dependency updates
- Comprehensive test suite
- JavaDoc documentation
- Example usage and documentation

### Features
- **Time Series Endpoint**: Historical OHLC data with various intervals
- **Quote Endpoint**: Real-time quote information
- **Price Endpoint**: Current price data
- **Flexible Configuration**: Custom base URLs and HTTP clients
- **Multiple Response Formats**: JSON, CSV, and typed objects
- **Error Handling**: Custom exceptions for different error types
- **Rate Limiting**: Built-in rate limit exception handling
- **Timezone Support**: Configurable timezone handling
- **Data Validation**: Input validation and type safety

### Technical Details
- **Java 17**: Minimum Java version requirement
- **Maven**: Build and dependency management
- **Spring Boot 3.5.4**: Framework integration
- **Jackson 2.19.2**: JSON processing
- **OkHttp 5.1.0**: HTTP client
- **JUnit 5.13.4**: Testing framework
- **SLF4J 2.0.17**: Logging facade

### Documentation
- Comprehensive README with usage examples
- API documentation with JavaDoc
- Error handling guide
- Publishing instructions
- Version management guide
- Release checklist
- Project summary

### Infrastructure
- GitHub Actions workflows for CI/CD
- Automated testing on pull requests
- Automated dependency updates via Dependabot
- Maven Central and GitHub Packages publishing
- Automated version management
- Documentation generation

---

## Version History

### Version 1.0.0-SNAPSHOT
- **Status**: Development version
- **Release Date**: 2025-07-30
- **Description**: Initial development release with core functionality

---

## Release Types

### Major Releases (X.0.0)
- Breaking changes to the API
- Major new features
- Significant architectural changes

### Minor Releases (0.X.0)
- New features (backward compatible)
- Enhancements to existing features
- New endpoint support

### Patch Releases (0.0.X)
- Bug fixes
- Performance improvements
- Documentation updates
- Dependency updates

---

## Migration Guides

### From Pre-1.0 Versions
- No migration required - this is the initial release

---

## Deprecation Policy

- Deprecated features will be marked with `@Deprecated` annotation
- Deprecated features will be removed in the next major version
- Migration guides will be provided for deprecated features

---

## Support

For support and questions:
- Check the [README.md](README.md) for usage examples
- Review the [documentation](docs/) for detailed guides
- Open an issue on GitHub for bugs or feature requests
 