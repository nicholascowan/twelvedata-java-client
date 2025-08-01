---
layout: default
title: TwelveData Java Client
nav_order: 1
---

# TwelveData Java Client

A comprehensive Java client library for the TwelveData API, providing easy access to real-time and historical financial market data.

## Quick Start

### Installation

**Maven Central (Recommended):**
```xml
<dependency>
    <groupId>com.github.nicholascowan.twelvedata</groupId>
    <artifactId>twelvedata-java-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

**GitHub Package Registry:**
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
    <version>0.0.1</version>
</dependency>
```

### Basic Usage

```java
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.models.*;

// Initialize client
TwelveDataClient client = new TwelveDataClient("your-api-key");

// Get time series data
TimeSeriesResponse timeSeriesData = client.timeSeries("AAPL", "1day").asObject();

// Get quote data
QuoteResponse quoteData = client.quote("AAPL").asObject();

// Get price data
PriceResponse priceData = client.price("AAPL").asObject();
```

## Features

- **Real-time Data**: Access live quotes, prices, and market data
- **Historical Data**: Retrieve time series data with various intervals
- **Type Safety**: Strongly typed models for all API responses
- **Error Handling**: Comprehensive exception handling with specific error types
- **Multiple Formats**: Support for JSON and CSV responses
- **Easy Integration**: Simple, fluent API design

## Documentation

- [Installation Guide](installation.md)
- [API Reference](api-reference.md)
- [Examples](examples.md)
- [Configuration](configuration.md)

## Support

- **GitHub Issues**: [Report bugs or request features](https://github.com/nicholascowan/twelvedata-java-client/issues)
- **Documentation**: [Full documentation](https://github.com/nicholascowan/twelvedata-java-client)
- **License**: [MIT License](LICENSE.txt)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.txt) file for details. 