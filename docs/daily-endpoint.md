---
layout: default
title: Daily Endpoint
nav_order: 5
parent: API Reference
---

# Daily Endpoint

The Daily endpoint provides access to daily time series data (OHLC - Open, High, Low, Close) for financial instruments. This endpoint is a convenience wrapper around the TimeSeries endpoint with a fixed interval of "1day".

## Overview

The Daily endpoint allows you to retrieve:
- Daily historical OHLC data for stocks, ETFs, and other financial instruments
- Data with a fixed interval of "1day" (no need to specify the interval)
- All the same parameters and functionality as the TimeSeries endpoint
- Convenient access for users who specifically want daily data

## Basic Usage

### Simple Request

```java
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;

TwelveDataClient client = new TwelveDataClient("your-api-key");

// Get daily time series data
TimeSeriesResponse response = client.daily("AAPL").asObject();

System.out.println("Symbol: " + response.getMeta().getSymbol());
System.out.println("Interval: " + response.getMeta().getInterval()); // Always "1day"
System.out.println("Latest close: " + response.getValues().get(0).getClose());
```

### With Output Size

```java
// Get last 30 days of daily data
TimeSeriesResponse response = client.daily("AAPL")
    .outputsize(30)
    .asObject();
```

### With Date Range

```java
// Get daily data for a specific date range
TimeSeriesResponse response = client.daily("MSFT")
    .startDate("2024-01-01")
    .endDate("2024-01-31")
    .asObject();
```

## Parameters

The Daily endpoint supports all the same parameters as the TimeSeries endpoint, except that the interval is automatically set to "1day" and cannot be changed.

### Required Parameters

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `symbol` | String | Symbol ticker of the instrument | `"AAPL"` |

### Optional Parameters

| Parameter | Type | Description | Default | Example |
|-----------|------|-------------|---------|---------|
| `exchange` | String | Exchange where instrument is traded | - | `"NASDAQ"` |
| `country` | String | Country where instrument is traded | - | `"United States"` |
| `type` | String | Asset class of the instrument | - | `"Common Stock"` |
| `outputsize` | Integer | Number of data points to return (1-5000) | `30` | `100` |
| `start_date` | String | Start date for data range (YYYY-MM-DD) | - | `"2024-01-01"` |
| `end_date` | String | End date for data range (YYYY-MM-DD) | - | `"2024-01-31"` |
| `dp` | Integer | Decimal places for floating values (0-11) | `5` | `2` |
| `timezone` | String | Timezone for the data | `"Exchange"` | `"America/New_York"` |
| `order` | String | Sort order ("asc" or "desc") | `"desc"` | `"asc"` |
| `prepost` | Boolean | Include pre/post market data | `false` | `true` |
| `date` | String | Specific date for data retrieval (YYYY-MM-DD) | - | `"2024-01-15"` |
| `mic_code` | String | Market Identifier Code (MIC) | - | `"XNAS"` |
| `previous_close` | String | Previous close price for calculations | - | `"150.25"` |
| `adjust` | String | Adjustment type ("split", "dividend", "split,div") | - | `"split"` |
| `figi` | String | Financial Instrument Global Identifier | - | `"BBG000BHTMY7"` |
| `isin` | String | International Securities Identification Number | - | `"US0378331005"` |
| `cusip` | String | CUSIP identifier | - | `"594918104"` |
| `format` | String | Output format ("JSON", "CSV") | `"JSON"` | `"CSV"` |
| `delimiter` | String | Delimiter for CSV format | `","` | `";"` |

## Response Format

The Daily endpoint returns the same response format as the TimeSeries endpoint:

### JSON Response

```json
{
    "status": "ok",
    "meta": {
        "symbol": "AAPL",
        "interval": "1day",
        "currency": "USD",
        "exchange_timezone": "America/New_York",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "type": "Common Stock"
    },
    "values": [
        {
            "datetime": "2024-01-15",
            "open": "185.59",
            "high": "186.12",
            "low": "183.62",
            "close": "185.14",
            "volume": "52464180"
        }
    ]
}
```

### Response Fields

| Field | Type | Description |
|-------|------|-------------|
| `status` | String | Response status ("ok" or "error") |
| `meta` | Object | Metadata about the request |
| `meta.symbol` | String | The stock symbol |
| `meta.interval` | String | The time interval (always "1day") |
| `meta.currency` | String | The currency code |
| `meta.exchange` | String | The exchange name |
| `meta.mic_code` | String | Market Identifier Code |
| `meta.type` | String | The security type |
| `values` | Array | Array of OHLC data points |
| `values[].datetime` | String | The date and time |
| `values[].open` | String | Opening price |
| `values[].high` | String | Highest price |
| `values[].low` | String | Lowest price |
| `values[].close` | String | Closing price |
| `values[].volume` | String | Trading volume |

## Advanced Usage

### Fluent API with Multiple Parameters

```java
TimeSeriesResponse response = client.daily("AAPL")
    .exchange("NASDAQ")
    .country("US")
    .type("Common Stock")
    .outputsize(100)
    .startDate("2024-01-01")
    .endDate("2024-01-31")
    .dp(2)
    .timezone("America/New_York")
    .order("desc")
    .prepost(false)
    .adjust("split")
    .asObject();
```

### Using Different Identifiers

```java
// Using FIGI
TimeSeriesResponse response = client.daily("AAPL")
    .figi("BBG000BHTMY7")
    .asObject();

// Using ISIN
TimeSeriesResponse response = client.daily("AAPL")
    .isin("US0378331005")
    .asObject();

// Using CUSIP
TimeSeriesResponse response = client.daily("AAPL")
    .cusip("594918104")
    .asObject();
```

### CSV Format

```java
// Get response as CSV
String csvResponse = client.daily("AAPL")
    .format("CSV")
    .delimiter(";")
    .asCsv();

System.out.println(csvResponse);
```

### Raw JSON Response

```java
// Get raw JSON response
JsonNode jsonResponse = client.daily("AAPL")
    .outputsize(10)
    .asJson();

System.out.println(jsonResponse.toString());
```

## Error Handling

The Daily endpoint throws the same exceptions as other endpoints:

```java
try {
    TimeSeriesResponse response = client.daily("INVALID_SYMBOL").asObject();
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

### Get Latest Daily Data

```java
TimeSeriesResponse response = client.daily("AAPL").asObject();
TimeSeriesValue latest = response.getValues().get(0);
System.out.println("Latest daily close for AAPL: $" + latest.getClose());
System.out.println("Date: " + latest.getDatetime());
```

### Get Historical Daily Data

```java
TimeSeriesResponse response = client.daily("MSFT")
    .startDate("2023-01-01")
    .endDate("2023-12-31")
    .asObject();

for (TimeSeriesValue value : response.getValues()) {
    System.out.println(value.getDatetime() + ": $" + value.getClose());
}
```

### Get ETF Daily Data

```java
TimeSeriesResponse response = client.daily("SPY")
    .type("ETF")
    .exchange("ARCA")
    .dp(2)
    .asObject();

System.out.println("SPY ETF daily data retrieved");
```

### Get Data with Adjustments

```java
TimeSeriesResponse response = client.daily("AAPL")
    .adjust("split,div")
    .outputsize(50)
    .asObject();

System.out.println("AAPL daily data with split and dividend adjustments");
```

## Comparison with TimeSeries Endpoint

The Daily endpoint is functionally equivalent to using the TimeSeries endpoint with `interval("1day")`:

```java
// These two calls are equivalent:
TimeSeriesResponse daily1 = client.daily("AAPL").asObject();
TimeSeriesResponse daily2 = client.timeSeries("AAPL", "1day").asObject();
```

The Daily endpoint provides:
- **Convenience**: No need to specify the interval parameter
- **Clarity**: Makes it clear that you're requesting daily data
- **Consistency**: Ensures the interval is always "1day"
- **Simplicity**: Reduces the chance of errors in interval specification

## Rate Limits

The Daily endpoint follows the same rate limiting as other TwelveData API endpoints. Please refer to your subscription plan for specific limits.

## Related Endpoints

- [TimeSeries Endpoint](timeseries-endpoint.md) - Historical OHLC data with variable intervals
- [EndOfDay Endpoint](endofday-endpoint.md) - End-of-day closing price data
- [Quote Endpoint](quote-endpoint.md) - Real-time quote data
- [Price Endpoint](price-endpoint.md) - Current price data 