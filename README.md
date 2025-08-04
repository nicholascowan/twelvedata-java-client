# TwelveData Java Client

[![CI](https://github.com/nicholascowan/twelvedata-java-client/workflows/CI/badge.svg)](https://github.com/nicholascowan/twelvedata-java-client/actions/workflows/ci.yml)
[![Tests](https://img.shields.io/badge/tests-passing-brightgreen)](https://github.com/nicholascowan/twelvedata-java-client/actions/workflows/ci.yml)
[![Java](https://img.shields.io/badge/java-17+-blue.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/maven-3.6+-orange.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE.txt)

A Java client library for the TwelveData API, providing easy access to financial market data. This was heavily inspired by other clients.

## ⚠️ Development Status

**This library is currently in active development and should not be used in production environments.** We recommend:

- **Testing thoroughly** before using in production environments
- **Pinning to specific versions** if you need stability
- **Monitoring releases** for breaking changes
- **Providing feedback** through issues or discussions

The API is not yet considered stable for production use.

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation

Clone the repository and build the project:

```bash
git clone https://github.com/nicholascowan/twelvedata-java-client.git
cd twelvedata-java-client
mvn clean install
```



## Usage

```java
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.models.*;
import com.github.nicholascowan.twelvedata.exceptions.*;

// Initialize client
TwelveDataClient client = new TwelveDataClient("your-api-key");

// Get time series data
TimeSeries ts = client.timeSeries("AAPL", "1min")
    .outputsize(5)
    .timezone("America/New_York");

// Get JSON response
JsonNode data = ts.asJson();

// Get CSV response
String csvData = ts.asCsv();

// Get typed object response
TimeSeriesResponse timeSeriesData = ts.asObject();

// Get quote data
QuoteResponse quoteData = client.quote("AAPL").asObject();

// Get price data
PriceResponse priceData = client.price("AAPL").asObject();

// Access model properties
logger.info("Symbol: {}", quoteData.getSymbol());
logger.info("Current Price: {}", quoteData.getClose());
logger.info("Market Open: {}", quoteData.getIsMarketOpen());

// Get numeric values
Double currentPrice = quoteData.getCloseAsDouble();
Long volume = quoteData.getVolumeAsLong();

// Access time series data
TimeSeriesValue latestValue = timeSeriesData.getLatestValue();
logger.info("Latest Close: {}", latestValue.getCloseAsDouble());

// Error handling
try {
    QuoteResponse quoteData = client.quote("INVALID_SYMBOL").asObject();
} catch (RateLimitException e) {
    logger.error("Rate limit exceeded: {}", e.getMessage());
    logger.error("Error code: {}", e.getErrorCode());
} catch (ServerErrorException e) {
    logger.error("Server error: {}", e.getMessage());
    logger.error("Error code: {}", e.getErrorCode());
} catch (TwelveDataException e) {
    logger.error("API error: {}", e.getMessage());
    logger.error("Error code: {}", e.getErrorCode());
}
```



## Building

### Compile
```bash
mvn compile
```

### Run Tests
```bash
mvn test
```

The project includes comprehensive tests that validate:
- **API Response Structure**: Tests verify that API responses contain expected properties
- **Data Types**: Validates correct data types for all fields
- **CSV Format**: Tests CSV response format and structure
- **URL Generation**: Validates generated API URLs contain correct parameters
- **Multiple Intervals**: Tests various time intervals (1min, 5min, 1day, etc.)
- **Error Handling**: Tests HTTP client error scenarios

### Package
```bash
mvn package
```

This creates several artifacts:
- `twelvedata-java-client-1.0.0.jar` - Main JAR
- `twelvedata-java-client-1.0.0-sources.jar` - Source code
- `twelvedata-java-client-1.0.0-javadoc.jar` - Documentation
- `twelvedata-java-client-1.0.0-jar-with-dependencies.jar` - Fat JAR with all dependencies

## Development

### Dependabot

This project uses [Dependabot](https://dependabot.com/) to automatically check for dependency updates. The configuration is located in `.github/dependabot.yml`.

**Features:**
- Weekly checks for Maven dependency updates
- Automatic grouping of minor and patch updates
- Pull request creation for dependency updates
- Automated testing via GitHub Actions

**Configuration:**
- Checks every Monday at 09:00 UTC
- Limits to 5 open pull requests
- Groups minor and patch updates together
- Only updates direct dependencies
- Adds appropriate labels to pull requests

### Continuous Integration

The project includes GitHub Actions workflows for:
- **CI**: Runs on all pull requests and pushes to main/develop branches
- **Dependabot PR Check**: Automatically tests dependency updates

## Installation

### From Maven Central (Recommended)
```xml
<dependency>
    <groupId>com.github.nicholascowan.twelvedata</groupId>
    <artifactId>twelvedata-java-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

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

## Publishing

This project is automatically published to both Maven Central and GitHub Package Registry when a new release is created.

## Version Management

The project includes automated version management tools to help maintain consistent versioning across releases.

### GitHub Actions Workflow

The easiest way to bump versions is using the GitHub Actions workflow:

1. Go to **Actions** tab in your repository
2. Select **"Bump Maven Version"** workflow
3. Click **"Run workflow"**
4. Choose version bump type: `patch`, `minor`, or `major`



## License

MIT License - see [LICENSE](LICENSE.txt) file for details. 