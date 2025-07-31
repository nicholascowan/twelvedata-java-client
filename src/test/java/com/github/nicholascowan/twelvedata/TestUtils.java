package com.github.nicholascowan.twelvedata;

/** Test utilities providing mock JSON responses for testing without hitting the actual API. */
public class TestUtils {
  /** Mock TimeSeries API response with realistic data. */
  public static final String TIME_SERIES_JSON_RESPONSE =
      """
      {
      "meta": {
      "symbol": "AAPL",
      "interval": "1min",
      "currency": "USD",
      "exchange_timezone": "America/New_York",
      "exchange": "NASDAQ",
      "mic_code": "XNAS",
      "type": "Common Stock"
      } ,
      "values": [
      {
      "datetime": "2021-09-16 15:59:00",
      "open": "148.73500",
      "high": "148.86000",
      "low": "148.73000",
      "close": "148.85001",
      "volume": "624277"
      } ,
      {
      "datetime": "2021-09-16 15:58:00",
      "open": "148.72000",
      "high": "148.75000",
      "low": "148.71000",
      "close": "148.73500",
      "volume": "589123"
      }
      ],
      "status": "ok"
      }
      """;

  /** Mock Quote API response with realistic data. */
  public static final String QUOTE_JSON_RESPONSE =
      """
      {
      "symbol": "AAPL",
      "name": "Apple Inc",
      "exchange": "NASDAQ",
      "mic_code": "XNAS",
      "currency": "USD",
      "datetime": "2021-09-16",
      "timestamp": "1631772000",
      "last_quote_at": "1631772000",
      "open": "148.44000",
      "high": "148.96840",
      "low": "147.22099",
      "close": "148.85001",
      "volume": "67903927",
      "previous_close": "149.09000",
      "change": "-0.23999",
      "percent_change": "-0.16097",
      "average_volume": "83571571",
      "rolling_1day_change": "123.123",
      "rolling_7day_change": "123.123",
      "rolling_period_change": "123.123",
      "is_market_open": false,
      "fifty_two_week": {
      "low": "103.10000",
      "high": "157.25999",
      "low_change": "45.75001",
      "high_change": "-8.40999",
      "low_change_percent": "44.37440",
      "high_change_percent": "-5.34782",
      "range": "103.099998 - 157.259995"
      } ,
      "extended_change": "0.09",
      "extended_percent_change": "0.05",
      "extended_price": "125.22",
      "extended_timestamp": "1649845281"
      }
      """;

  /** Mock Price API response with realistic data. */
  public static final String PRICE_JSON_RESPONSE =
      """
      {
      "price": "200.99001"
      }
      """;

  /** Mock error response for testing error handling. */
  public static final String ERROR_JSON_RESPONSE =
      """
      {
      "code": 400,
      "message": "Bad Request",
      "status": "error"
      }
      """;

  /** Mock rate limit error response. */
  public static final String RATE_LIMIT_JSON_RESPONSE =
      """
      {
      "code": 429,
      "message": "Rate limit exceeded",
      "status": "error"
      }
      """;

  /** Mock server error response. */
  public static final String SERVER_ERROR_JSON_RESPONSE =
      """
      {
      "code": 500,
      "message": "Internal Server Error",
      "status": "error"
      }
      """;

  /** Mock unauthorized error response. */
  public static final String UNAUTHORIZED_JSON_RESPONSE =
      """
      {
      "code": 401,
      "message": "Unauthorized",
      "status": "error"
      }
      """;

  /** Mock forbidden error response. */
  public static final String FORBIDDEN_JSON_RESPONSE =
      """
      {
      "code": 403,
      "message": "Forbidden",
      "status": "error"
      }
      """;

  /** Mock not found error response. */
  public static final String NOT_FOUND_JSON_RESPONSE =
      """
      {
      "code": 404,
      "message": "Not Found",
      "status": "error"
      }
      """;

  /** Mock parameter too long error response. */
  public static final String PARAMETER_TOO_LONG_JSON_RESPONSE =
      """
      {
      "code": 414,
      "message": "Parameter too long",
      "status": "error"
      }
      """;
}
