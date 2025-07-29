# TwelveData Java Client

A Java client library for the TwelveData API, providing easy access to financial market data.

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

### Running the Example

The project includes an exec-maven-plugin for easy execution of the example code.

#### Run with Demo API Key (Free Tier)
```bash
mvn exec:java
```

#### Run with Custom API Key
```bash
mvn exec:java -Dexec.args="your-api-key-here"
```

#### Run with Profile (Alternative Method)
```bash
mvn exec:java -P run-with-api-key -Dapi.key="your-api-key-here"
```

### Example Output

The example demonstrates:
1. Getting time series data (OHLC) for AAPL
2. Retrieving data in CSV format
3. Generating API URLs
4. Getting real-time quotes
5. Getting current prices

## Usage

```java
// Initialize client
TwelveDataClient client = new TwelveDataClient("your-api-key");

// Get time series data
TimeSeriesEndpoint ts = client.timeSeries("AAPL", "1min")
    .outputsize(5)
    .timezone("America/New_York");

// Get JSON response
JsonNode data = ts.asJson();

// Get CSV response
String csvData = ts.asCsv();

// Get quote data
JsonNode quoteData = client.quote("AAPL").asJson();

// Get price data
JsonNode priceData = client.price("AAPL").asJson();
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

This project is automatically published to both Maven Central and GitHub Package Registry when a new release is created. For detailed publishing instructions, see [PUBLISHING.md](PUBLISHING.md).

## Version Management

The project includes automated version management tools to help maintain consistent versioning across releases.

### GitHub Actions Workflow

The easiest way to bump versions is using the GitHub Actions workflow:

1. Go to **Actions** tab in your repository
2. Select **"Bump Maven Version"** workflow
3. Click **"Run workflow"**
4. Choose version bump type: `patch`, `minor`, or `major`

For detailed version management instructions, see [VERSION_MANAGEMENT.md](VERSION_MANAGEMENT.md).

## License

MIT License - see [LICENSE](LICENSE.txt) file for details. 