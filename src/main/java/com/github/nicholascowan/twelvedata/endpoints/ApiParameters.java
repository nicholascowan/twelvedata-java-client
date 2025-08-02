package com.github.nicholascowan.twelvedata.endpoints;

/**
 * Constants for TwelveData API parameter names.
 * 
 * <p>This class centralizes all parameter names used in API requests to ensure consistency
 * and reduce the risk of typos. All endpoint classes should use these constants instead
 * of hardcoded string literals.</p>
 * 
 * @see Price
 * @see Quote
 * @see TimeSeries
 */
public final class ApiParameters {

  // Private constructor to prevent instantiation
  private ApiParameters() {
    throw new UnsupportedOperationException("Utility class - cannot be instantiated");
  }

  // Core parameters
  public static final String SYMBOL = "symbol";
  public static final String INTERVAL = "interval";
  public static final String EXCHANGE = "exchange";
  public static final String COUNTRY = "country";
  public static final String TYPE = "type";
  public static final String CURRENCY = "currency";

  // Time and date parameters
  public static final String TIMEZONE = "timezone";
  public static final String START_DATE = "start_date";
  public static final String END_DATE = "end_date";
  public static final String DATE = "date";
  public static final String ORDER = "order";

  // Data format parameters
  public static final String OUTPUT_SIZE = "outputsize";
  public static final String DP = "dp";
  public static final String FORMAT = "format";
  public static final String DELIMITER = "delimiter";

  // Market data parameters
  public static final String PREPOST = "prepost";
  public static final String EOD = "eod";
  public static final String ROLLING_PERIOD = "rolling_period";
  public static final String VOLUME_TIME_PERIOD = "volume_time_period";
  public static final String PREVIOUS_CLOSE = "previous_close";
  public static final String ADJUST = "adjust";

  // Identifier parameters
  public static final String MIC_CODE = "mic_code";
  public static final String FIGI = "figi";
  public static final String ISIN = "isin";
  public static final String CUSIP = "cusip";

  // Price data parameters
  public static final String PRICE = "price";
  public static final String OPEN = "open";
  public static final String HIGH = "high";
  public static final String LOW = "low";
  public static final String CLOSE = "close";
  public static final String VOLUME = "volume";

  // Quote specific parameters
  public static final String NAME = "name";
  public static final String DATETIME = "datetime";
  public static final String TIMESTAMP = "timestamp";
  public static final String LAST_QUOTE_AT = "last_quote_at";
  public static final String CHANGE = "change";
  public static final String PERCENT_CHANGE = "percent_change";
  public static final String AVERAGE_VOLUME = "average_volume";
  public static final String IS_MARKET_OPEN = "is_market_open";

  // Time series specific parameters
  public static final String STATUS = "status";
  public static final String META = "meta";
  public static final String VALUES = "values";

  // 52-week data parameters
  public static final String FIFTY_TWO_WEEK = "fifty_two_week";
  public static final String LOW_CHANGE = "low_change";
  public static final String HIGH_CHANGE = "high_change";
  public static final String LOW_CHANGE_PERCENT = "low_change_percent";
  public static final String HIGH_CHANGE_PERCENT = "high_change_percent";
  public static final String RANGE = "range";
} 