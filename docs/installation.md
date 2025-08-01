---
layout: default
title: Installation
nav_order: 2
---

# Installation Guide

This guide will help you install and set up the TwelveData Java Client in your project.

## Prerequisites

- **Java 17 or higher**
- **Maven 3.6+** or **Gradle 7+**
- **TwelveData API Key** (get one at [twelvedata.com](https://twelvedata.com))

## Maven Installation

### From Maven Central (Recommended)

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.github.nicholascowan.twelvedata</groupId>
    <artifactId>twelvedata-java-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

### From GitHub Package Registry

If you prefer to use GitHub Package Registry, add the repository and dependency:

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

## Gradle Installation

### From Maven Central

Add to your `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.nicholascowan.twelvedata:twelvedata-java-client:0.0.1'
}
```

### From GitHub Package Registry

Add to your `build.gradle`:

```gradle
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/nicholascowan/twelvedata-java-client")
    }
}

dependencies {
    implementation 'com.github.nicholascowan.twelvedata:twelvedata-java-client:0.0.1'
}
```

## Manual Installation

### Download JAR Files

You can download the JAR files directly from the [releases page](https://github.com/nicholascowan/twelvedata-java-client/releases).

### Dependencies

The client requires these dependencies:

- **OkHttp 5.1.0+** - HTTP client
- **Jackson 2.19.2+** - JSON processing
- **SLF4J 2.0.9+** - Logging facade

## Verification

To verify the installation, run this simple test:

```java
import com.github.nicholascowan.twelvedata.TwelveDataClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestInstallation {
    private static final Logger logger = LoggerFactory.getLogger(TestInstallation.class);
    
    public static void main(String[] args) {
        try {
            TwelveDataClient client = new TwelveDataClient("your-api-key");
            logger.info("TwelveData Java Client installed successfully!");
        } catch (Exception e) {
            logger.error("Installation failed: {}", e.getMessage());
        }
    }
}
```

## Next Steps

After installation, you can:

1. [Get your API key](https://twelvedata.com)
2. [Read the API Reference](api-reference.md)



## Troubleshooting

### Common Issues

**Dependency Resolution Errors**
- Ensure you're using Java 17 or higher
- Check that your Maven/Gradle version is up to date
- Verify the repository URLs are correct

**Class Not Found Errors**
- Make sure all dependencies are properly included
- Check that the JAR files are in your classpath
- Verify the package imports are correct

**API Key Issues**
- Ensure your API key is valid and active
- Check your TwelveData account status
- Verify the API key format

### Getting Help

If you encounter issues:

1. Check the [GitHub Issues](https://github.com/nicholascowan/twelvedata-java-client/issues)
2. Review the [Error Handling Guide](error-handling.md)
3. Create a new issue with detailed information 