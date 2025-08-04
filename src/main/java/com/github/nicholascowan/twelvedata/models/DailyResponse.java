package com.github.nicholascowan.twelvedata.models;

/**
 * Response model for the Daily endpoint.
 *
 * <p>This class extends {@link TimeSeriesResponse} to provide a more specific type
 * for daily time series data. It represents the response from the TwelveData API's
 * Daily endpoint, which provides daily OHLC (Open, High, Low, Close) data with a
 * fixed interval of {@link #DAILY_INTERVAL}.</p>
 *
 * <p>Example response:</p>
 * <pre>{@code
 * {
 *     "status": "ok",
 *     "meta": {
 *         "symbol": "AAPL",
 *         "interval": "1day", // This is the value of DAILY_INTERVAL
 *         "currency": "USD",
 *         "exchange_timezone": "America/New_York",
 *         "exchange": "NASDAQ",
 *         "mic_code": "XNAS",
 *         "type": "Common Stock"
 *     },
 *     "values": [
 *         {
 *             "datetime": "2024-01-15",
 *             "open": "185.59",
 *             "high": "186.12",
 *             "low": "183.62",
 *             "close": "185.14",
 *             "volume": "52464180"
 *         }
 *     ]
 * }
 * }</pre>
 *
 * <p>The DailyResponse provides the same functionality as TimeSeriesResponse but
 * with additional type safety and clarity that the data represents daily intervals.</p>
 *
 * @see com.github.nicholascowan.twelvedata.endpoints.Daily
 * @see TimeSeriesResponse
 * @see TimeSeriesMeta
 * @see TimeSeriesValue
 */
public class DailyResponse extends TimeSeriesResponse {

  /**
   * The fixed interval for daily time series data.
   */
  private static final String DAILY_INTERVAL = "1day";

  /**
   * Default constructor.
   */
  public DailyResponse() {
    super();
  }

  /**
   * Constructor with all fields.
   *
   * @param status the response status
   * @param meta the metadata about the request
   * @param values the array of daily OHLC data points
   */
  public DailyResponse(String status, TimeSeriesMeta meta, java.util.List<TimeSeriesValue> values) {
    super(status, meta, values);
  }

  /**
   * Gets the daily interval from the metadata.
   *
   * <p>This method provides a convenient way to verify that the response
   * contains daily data. The interval should always be {@link #DAILY_INTERVAL} for DailyResponse.</p>
   *
   * @return the interval (should always be {@link #DAILY_INTERVAL})
   */
  public String getDailyInterval() {
    return getMeta() != null ? getMeta().getInterval() : null;
  }

  /**
   * Verifies that this response contains daily data.
   *
   * <p>This method checks if the interval in the metadata is {@link #DAILY_INTERVAL},
   * which should always be true for DailyResponse objects.</p>
   *
   * @return true if the interval is {@link #DAILY_INTERVAL}, false otherwise
   */
  public boolean isDailyData() {
    String interval = getDailyInterval();
    return DAILY_INTERVAL.equals(interval);
  }

  /**
   * Gets the latest daily close price.
   *
   * <p>This method provides a convenient way to get the most recent
   * closing price from the daily data. It returns the close price
   * from the first value in the values array (assuming descending order).</p>
   *
   * @return the latest daily close price, or null if no data is available
   */
  public String getLatestClose() {
    if (getValues() != null && !getValues().isEmpty()) {
      return getValues().get(0).getClose();
    }
    return null;
  }

  /**
   * Gets the latest daily close price as a double.
   *
   * <p>This method provides a convenient way to get the most recent
   * closing price as a numeric value for calculations.</p>
   *
   * @return the latest daily close price as a double, or null if parsing fails
   */
  public Double getLatestCloseAsDouble() {
    String close = getLatestClose();
    if (close != null) {
      try {
        return Double.parseDouble(close);
      } catch (NumberFormatException e) {
        return null;
      }
    }
    return null;
  }

  /**
   * Gets the latest daily close price as a float.
   *
   * <p>This method provides a convenient way to get the most recent
   * closing price as a numeric value for calculations.</p>
   *
   * @return the latest daily close price as a float, or null if parsing fails
   */
  public Float getLatestCloseAsFloat() {
    String close = getLatestClose();
    if (close != null) {
      try {
        return Float.parseFloat(close);
      } catch (NumberFormatException e) {
        return null;
      }
    }
    return null;
  }

  /**
   * Gets the latest daily date.
   *
   * <p>This method provides a convenient way to get the most recent
   * date from the daily data.</p>
   *
   * @return the latest daily date, or null if no data is available
   */
  public String getLatestDate() {
    if (getValues() != null && !getValues().isEmpty()) {
      return getValues().get(0).getDatetime();
    }
    return null;
  }

  /**
   * Gets the number of daily data points.
   *
   * <p>This method provides a convenient way to get the count of
   * daily data points in the response.</p>
   *
   * @return the number of daily data points, or 0 if no data is available
   */
  public int getDailyDataCount() {
    return getValues() != null ? getValues().size() : 0;
  }

  /**
   * Checks if the response contains any daily data.
   *
   * <p>This method provides a convenient way to check if the response
   * has any daily data points.</p>
   *
   * @return true if the response contains daily data, false otherwise
   */
  public boolean hasDailyData() {
    return getValues() != null && !getValues().isEmpty();
  }

  /**
   * Gets a specific daily data point by index.
   *
   * <p>This method provides a convenient way to access a specific
   * daily data point from the response.</p>
   *
   * @param index the index of the daily data point (0-based)
   * @return the daily data point at the specified index, or null if index is out of bounds
   */
  public TimeSeriesValue getDailyDataPoint(int index) {
    if (getValues() != null && index >= 0 && index < getValues().size()) {
      return getValues().get(index);
    }
    return null;
  }

  /**
   * Gets all daily data points as a list.
   *
   * <p>This method provides a convenient way to get all daily data points
   * from the response.</p>
   *
   * @return a list of daily data points, or an empty list if no data is available
   */
  public java.util.List<TimeSeriesValue> getDailyDataPoints() {
    return getValues() != null ? new java.util.ArrayList<>(getValues()) : new java.util.ArrayList<>();
  }

  @Override
  public String toString() {
    return "DailyResponse{"
        + "status='" + getStatus() + '\''
        + ", meta=" + getMeta()
        + ", values=" + getValues()
        + ", dailyDataCount=" + getDailyDataCount()
        + ", isDailyData=" + isDailyData()
        + '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    return true; // DailyResponse has no additional fields beyond TimeSeriesResponse
  }

  @Override
  public int hashCode() {
    return super.hashCode(); // DailyResponse has no additional fields beyond TimeSeriesResponse
  }
} 