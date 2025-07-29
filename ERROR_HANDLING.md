# Error Handling Guide

This document describes how to handle errors when using the TwelveData Java Client.

## Overview

The TwelveData API can return various error responses with specific HTTP status codes. The Java client provides comprehensive error handling with specific exception classes for each error type.

## Error Response Structure

All API error responses follow this structure:

```json
{
  "status": "error",
  "code": 400,
  "message": "Invalid parameter provided"
}
```

## Exception Hierarchy

The client provides specific exception classes for different error types:

```
TwelveDataException (base)
├── BadRequestException (400)
├── UnauthorizedException (401)
├── ForbiddenException (403)
├── NotFoundException (404)
├── ParameterTooLongException (414)
├── RateLimitException (429)
└── ServerErrorException (500+)
```

## Error Codes and Meanings

| Code | Status | Meaning | Resolution |
|------|--------|---------|------------|
| 400 | Bad Request | Invalid or incorrect parameter(s) provided | Check the message in the response for details. Refer to the API Documentation to correct the input. |
| 401 | Unauthorized | Invalid or incorrect API key | Verify your API key is correct. Sign up for a key if needed. |
| 403 | Forbidden | API key lacks permissions for the requested resource | Upgrade your plan to access the requested resource. |
| 404 | Not Found | Requested data could not be found | Adjust parameters to be less strict as they may be too restrictive. |
| 414 | Parameter Too Long | Input parameter array exceeds the allowed length | Follow the message guidance to adjust the parameter length. |
| 429 | Too Many Requests | API request limit reached for your key | Wait briefly or upgrade your plan. |
| 500 | Internal Server Error | Server-side issue occurred | Retry later or contact support for assistance. |

## Basic Error Handling

### Automatic Error Detection

The client automatically detects error responses and throws appropriate exceptions:

```java
try {
    QuoteResponse quote = client.quote("AAPL").asObject();
} catch (BadRequestException e) {
    System.out.println("Invalid parameters: " + e.getMessage());
} catch (UnauthorizedException e) {
    System.out.println("Invalid API key: " + e.getMessage());
} catch (RateLimitException e) {
    System.out.println("Rate limit exceeded: " + e.getMessage());
} catch (TwelveDataException e) {
    System.out.println("API error: " + e.getMessage());
    System.out.println("Error code: " + e.getErrorCode());
}
```

### Comprehensive Error Handling

For more detailed error handling, you can catch specific exceptions:

```java
try {
    TimeSeriesResponse timeSeries = client.timeSeries("AAPL", "1day").asObject();
} catch (BadRequestException e) {
    // Handle invalid parameters
    System.out.println("Check your parameters: " + e.getMessage());
} catch (UnauthorizedException e) {
    // Handle invalid API key
    System.out.println("Check your API key: " + e.getMessage());
} catch (ForbiddenException e) {
    // Handle insufficient permissions
    System.out.println("Upgrade your plan: " + e.getMessage());
} catch (NotFoundException e) {
    // Handle data not found
    System.out.println("Data not found: " + e.getMessage());
} catch (ParameterTooLongException e) {
    // Handle parameter length issues
    System.out.println("Parameter too long: " + e.getMessage());
} catch (RateLimitException e) {
    // Handle rate limiting
    System.out.println("Rate limit exceeded: " + e.getMessage());
    // Implement exponential backoff
    Thread.sleep(1000);
} catch (ServerErrorException e) {
    // Handle server errors
    System.out.println("Server error: " + e.getMessage());
    System.out.println("Error code: " + e.getErrorCode());
    // Implement retry logic
} catch (TwelveDataException e) {
    // Handle other API errors
    System.out.println("Unexpected error: " + e.getMessage());
}
```

## Error Response Objects

You can also get error responses without throwing exceptions:

```java
// Get error response without throwing exception
ErrorResponse error = client.quote("INVALID_SYMBOL").asErrorResponse();
if (error != null) {
    System.out.println("Error: " + error.getMessage());
    System.out.println("Code: " + error.getCode());
    System.out.println("Description: " + error.getErrorDescription());
    
    // Check specific error types
    if (error.isBadRequest()) {
        System.out.println("Invalid parameters provided");
    } else if (error.isUnauthorized()) {
        System.out.println("Invalid API key");
    } else if (error.isRateLimited()) {
        System.out.println("Rate limit exceeded");
    } else if (error.isServerError()) {
        System.out.println("Server error occurred");
    }
}
```

## Best Practices

### 1. Always Handle Exceptions

```java
try {
    QuoteResponse quote = client.quote("AAPL").asObject();
    // Process successful response
} catch (TwelveDataException e) {
    // Always handle API errors
    logger.error("API error: " + e.getMessage(), e);
}
```

### 2. Implement Retry Logic for Transient Errors

```java
public QuoteResponse getQuoteWithRetry(String symbol, int maxRetries) {
    for (int attempt = 1; attempt <= maxRetries; attempt++) {
        try {
            return client.quote(symbol).asObject();
        } catch (RateLimitException e) {
            if (attempt == maxRetries) {
                throw e;
            }
            // Wait with exponential backoff
            try {
                Thread.sleep(1000 * attempt);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new TwelveDataException("Retry interrupted", e);
            }
        } catch (ServerErrorException e) {
            if (attempt == maxRetries) {
                throw e;
            }
            // Wait before retry
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new TwelveDataException("Retry interrupted", e);
            }
        }
    }
    throw new TwelveDataException("Max retries exceeded");
}
```

### 3. Validate Parameters Before Making Requests

```java
public QuoteResponse getQuote(String symbol) {
    if (symbol == null || symbol.trim().isEmpty()) {
        throw new IllegalArgumentException("Symbol cannot be null or empty");
    }
    
    try {
        return client.quote(symbol.trim()).asObject();
    } catch (BadRequestException e) {
        throw new IllegalArgumentException("Invalid symbol: " + symbol, e);
    }
}
```

### 4. Log Errors Appropriately

```java
try {
    TimeSeriesResponse response = client.timeSeries("AAPL", "1day").asObject();
} catch (RateLimitException e) {
    logger.warn("Rate limit exceeded, will retry later: " + e.getMessage());
    // Implement rate limiting logic
} catch (ServerErrorException e) {
    logger.error("Server error occurred: " + e.getMessage(), e);
    // Alert monitoring systems
} catch (TwelveDataException e) {
    logger.error("API error: " + e.getMessage(), e);
    // Handle general API errors
}
```

## Error Recovery Strategies

### Rate Limiting (429)

- Implement exponential backoff
- Queue requests for later processing
- Upgrade your plan for higher limits

### Server Errors (500+)

- Implement retry logic with delays
- Use circuit breaker pattern for repeated failures
- Contact support for persistent issues

### Authentication Errors (401/403)

- Verify API key is correct
- Check API key permissions
- Upgrade plan if needed

### Parameter Errors (400/414)

- Validate parameters before sending
- Check API documentation for correct formats
- Implement parameter validation

## Monitoring and Alerting

Monitor these error types for system health:

- **High rate of 429 errors**: Consider upgrading your plan
- **High rate of 500 errors**: Monitor API service status
- **High rate of 401/403 errors**: Check API key configuration
- **High rate of 400 errors**: Review parameter validation logic

## Support

For persistent issues or questions about error handling:

1. Check the API documentation for parameter requirements
2. Verify your API key and plan limits
3. Contact TwelveData support for server-side issues
4. Review the error messages for specific guidance 