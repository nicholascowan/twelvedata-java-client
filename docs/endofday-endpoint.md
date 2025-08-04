---
layout: default
title: EndOfDay Endpoint
nav_order: 4
parent: API Reference
---

# EndOfDay Endpoint

The EndOfDay endpoint provides access to end-of-day closing price data for financial instruments. This endpoint returns the closing price for a specific date along with comprehensive instrument metadata.

## Overview

The EndOfDay endpoint allows you to retrieve:
- End-of-day closing prices for stocks, ETFs, and other financial instruments
- Instrument metadata including exchange, currency, and identifiers
- Historical closing prices for specific dates
- Data with various formatting options

## Basic Usage

### Simple Request

```java
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.models.EndOfDayResponse;

TwelveDataClient client = new TwelveDataClient("your-api-key");

// Get end-of-day data for a symbol
EndOfDayResponse response = client.endOfDay("AAPL").asObject();

System.out.println("Symbol: " + response.getSymbol());
System.out.println("Close: " + response.getClose());
System.out.println("Date: " + response.getDatetime());
```

### With Specific Date

```java
// Get end-of-day data for a specific date
EndOfDayResponse response = client.endOfDay("AAPL")
    .date("2024-01-15")
    .asObject();
```

### With Exchange and Formatting

```java
// Get end-of-day data with exchange and decimal places
EndOfDayResponse response = client.endOfDay("MSFT")
    .exchange("NASDAQ")
    .dp(2)
    .asObject();
```

## Parameters

### Required Parameters

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `symbol` | String | Symbol ticker of the instrument | `"AAPL"` |

### Optional Parameters

| Parameter | Type | Description | Default | Example |
|-----------|------|-------------|---------|---------|
| `figi` | String | Financial Instrument Global Identifier | - | `"BBG000BHTMY7"` |
| `isin` | String | International Securities Identification Number | - | `"US0378331005"` |
| `cusip` | String | CUSIP identifier | - | `"594918104"` |
| `exchange` | String | Exchange where instrument is traded | - | `"NASDAQ"` |
| `mic_code` | String | Market Identifier Code (MIC) | - | `"XNAS"` |
| `country` | String | Country where instrument is traded | - | `"United States"` |
| `type` | String | Asset class of the instrument | - | `"Common Stock"` |
| `date` | String | Specific date for data retrieval (YYYY-MM-DD) | - | `"2006-01-02"` |
| `prepost` | Boolean | Include pre/post market data | `false` | `true` |
| `dp` | Integer | Decimal places for floating values (0-11) | `5` | `2` |
| `format` | String | Output format | `"JSON"` | `"CSV"` |
| `delimiter` | String | Delimiter for CSV format | `","` | `";"` |

### Asset Types

The `type` parameter supports the following asset classes:

- American Depositary Receipt
- Bond
- Bond Fund
- Closed-end Fund
- Common Stock
- Depositary Receipt
- Digital Currency
- ETF
- Exchange-Traded Note
- Global Depositary Receipt
- Limited Partnership
- Mutual Fund
- Physical Currency
- Preferred Stock
- REIT
- Right
- Structured Product
- Trust
- Unit
- Warrant

## Response Format

### JSON Response

```json
{
    "symbol": "AAPL",
    "exchange": "NASDAQ",
    "mic_code": "XNAS",
    "currency": "USD",
    "datetime": "2021-09-16",
    "close": "148.79"
}
```

### Response Fields

| Field | Type | Description |
|-------|------|-------------|
| `symbol` | String | The stock symbol |
| `exchange` | String | The exchange name |
| `mic_code` | String | Market Identifier Code |
| `currency` | String | The currency code |
| `datetime` | String | The date and time (YYYY-MM-DD format) |
| `close` | String | The closing price |

## Advanced Usage

### Fluent API with Multiple Parameters

```java
EndOfDayResponse response = client.endOfDay("AAPL")
    .figi("BBG000BHTMY7")
    .exchange("NASDAQ")
    .country("US")
    .type("Common Stock")
    .date("2024-01-15")
    .dp(2)
    .prepost(false)
    .asObject();
```

### Using Different Identifiers

```java
// Using FIGI
EndOfDayResponse response = client.endOfDay("AAPL")
    .figi("BBG000BHTMY7")
    .asObject();

// Using ISIN
EndOfDayResponse response = client.endOfDay("AAPL")
    .isin("US0378331005")
    .asObject();

// Using CUSIP
EndOfDayResponse response = client.endOfDay("AAPL")
    .cusip("594918104")
    .asObject();
```

### CSV Format

```java
// Get response as CSV
String csvResponse = client.endOfDay("AAPL")
    .format("CSV")
    .delimiter(";")
    .asCsv();

System.out.println(csvResponse);
```

### Raw JSON Response

```java
// Get raw JSON response
JsonNode jsonResponse = client.endOfDay("AAPL")
    .date("2024-01-15")
    .asJson();

System.out.println(jsonResponse.toString());
```

## Error Handling

The EndOfDay endpoint throws the same exceptions as other endpoints:

```java
try {
    EndOfDayResponse response = client.endOfDay("INVALID_SYMBOL").asObject();
} catch (BadRequestException e) {
    System.err.println("Bad request: " + e.getMessage());
} catch (InvalidApiKeyException e) {
    System.err.println("Invalid API key: " + e.getMessage());
} catch (RateLimitException e) {
    System.err.println("Rate limit exceeded: " + e.getMessage());
} catch (TwelveDataException e) {
    System.err.println("API error: " + e.getMessage());
}
```

## Examples

### Get Latest End-of-Day Data

```java
EndOfDayResponse response = client.endOfDay("AAPL").asObject();
System.out.println("Latest close for AAPL: $" + response.getClose());
```

### Get Historical End-of-Day Data

```java
EndOfDayResponse response = client.endOfDay("MSFT")
    .date("2023-12-31")
    .asObject();
System.out.println("MSFT close on 2023-12-31: $" + response.getClose());
```

### Get ETF End-of-Day Data

```java
EndOfDayResponse response = client.endOfDay("SPY")
    .type("ETF")
    .exchange("ARCA")
    .dp(2)
    .asObject();
System.out.println("SPY ETF close: $" + response.getClose());
```

### Get Data with Pre/Post Market

```java
EndOfDayResponse response = client.endOfDay("TSLA")
    .prepost(true)
    .asObject();
System.out.println("TSLA close including pre/post market: $" + response.getClose());
```

## Rate Limits

The EndOfDay endpoint follows the same rate limiting as other TwelveData API endpoints. Please refer to your subscription plan for specific limits.

## Related Endpoints

- [TimeSeries Endpoint](timeseries-endpoint.md) - Historical OHLC data
- [Quote Endpoint](quote-endpoint.md) - Real-time quote data
- [Price Endpoint](price-endpoint.md) - Current price data 