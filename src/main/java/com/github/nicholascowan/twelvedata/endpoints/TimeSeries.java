package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
import com.github.nicholascowan.twelvedata.models.ModelUtils;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;
import java.time.ZoneId;

/**
 * Provides access to time series data (OHLC - Open, High, Low, Close) from the TwelveData API.
 * 
 * <p>This endpoint allows you to retrieve historical price data for stocks, ETFs, and other 
 * financial instruments. The TimeSeries endpoint provides comprehensive OHLC data with various 
 * time intervals, date ranges, and technical analysis capabilities.</p>
 * 
 * <p>Example usage:</p>
 * <pre>{@code
 * TwelveDataClient client = new TwelveDataClient("your-api-key");
 * 
 * // Get daily time series data
 * TimeSeriesResponse response = client.timeSeries("AAPL", "1day")
 *     .outputsize(30)
 *     .asObject();
 * 
 * // Get intraday data with specific parameters
 * TimeSeriesResponse response = client.timeSeries("AAPL", "1min")
 *     .outputsize(100)
 *     .timezone("America/New_York")
 *     .prepost(true)
 *     .asObject();
 * }</pre>
 * 
 * <p>The fluent API allows for method chaining to configure multiple parameters:</p>
 * <pre>{@code
 * TimeSeries ts = client.timeSeries("MSFT", "5min")
 *     .outputsize(50)
 *     .timezone(ZoneId.of("America/New_York"))
 *     .startDate("2024-01-01")
 *     .endDate("2024-01-31")
 *     .order("desc");
 * }</pre>
 * 
 * @see TwelveDataClient#timeSeries()
 * @see TwelveDataClient#timeSeries(String, String)
 * @see TimeSeriesResponse
 * @see TwelveDataException
 */
public class TimeSeries extends Endpoint {

  /**
   * Constructs a new TimeSeries endpoint with the given context.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   */
  public TimeSeries(TwelveDataContext context) {
    super(context);
    this.isPrice = true;
  }

  /**
   * Constructs a new TimeSeries endpoint with the given context, symbol, and interval.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   * @param interval the time interval for the data (e.g., "1min", "5min", "1day", "1week")
   */
  public TimeSeries(TwelveDataContext context, String symbol, String interval) {
    super(context);
    this.isPrice = true;
    addParam("symbol", symbol);
    addParam("interval", interval);
  }

  /**
   * Constructs a new TimeSeries endpoint with the given context and all parameters.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @param interval the time interval for the data (e.g., "1min", "5min", "1day")
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE")
   * @param country the country code (e.g., "US", "CA")
   * @param type the security type (e.g., "Common Stock", "ETF")
   * @param outputsize the number of data points to return
   * @param startDate the start date for the data range (YYYY-MM-DD format)
   * @param endDate the end date for the data range (YYYY-MM-DD format)
   * @param dp the decimal places for price formatting
   * @param timezone the timezone for the data (e.g., "America/New_York")
   * @param order the sort order ("asc" or "desc")
   * @param prepost whether to include pre/post market data ("true"/"false")
   * @param date the specific date for data retrieval (YYYY-MM-DD format)
   * @param micCode the Market Identifier Code (e.g., "XNAS", "XNYS")
   * @param previousClose the previous close price for calculations
   * @param adjust the adjustment type ("split", "dividend", "split,div")
   */
  public TimeSeries(
      TwelveDataContext context,
      String symbol,
      String interval,
      String exchange,
      String country,
      String type,
      Integer outputsize,
      String startDate,
      String endDate,
      Integer dp,
      String timezone,
      String order,
      String prepost,
      String date,
      String micCode,
      String previousClose,
      String adjust) {
    super(context);
    this.isPrice = true;
    addParam("symbol", symbol);
    addParam("interval", interval);
    addParam("exchange", exchange);
    addParam("country", country);
    addParam("type", type);
    addParam("outputsize", outputsize);
    addParam("start_date", startDate);
    addParam("end_date", endDate);
    addParam("dp", dp);
    addParam("timezone", timezone);
    addParam("order", order);
    addParam("prepost", prepost);
    addParam("date", date);
    addParam("mic_code", micCode);
    addParam("previous_close", previousClose);
    addParam("adjust", adjust);
  }

  @Override
  protected String getEndpointName() {
    return "time_series";
  }

  /**
   * Sets the stock symbol for the time series request.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries symbol(String symbol) {
    addParam("symbol", symbol);
    return this;
  }

  /**
   * Sets the time interval for the time series data.
   *
   * @param interval the time interval (e.g., "1min", "5min", "15min", "30min", "45min", "1h", "2h", "4h", "1day", "1week", "1month")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries interval(String interval) {
    addParam("interval", interval);
    return this;
  }

  /**
   * Sets the exchange for the time series request.
   *
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE", "LSE")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries exchange(String exchange) {
    addParam("exchange", exchange);
    return this;
  }

  /**
   * Sets the country code for the time series request.
   *
   * @param country the country code (e.g., "US", "CA", "GB")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries country(String country) {
    addParam("country", country);
    return this;
  }

  /**
   * Sets the security type for the time series request.
   *
   * @param type the security type (e.g., "Common Stock", "ETF", "Bond")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries type(String type) {
    addParam("type", type);
    return this;
  }

  /**
   * Sets the number of data points to return.
   *
   * @param outputsize the number of data points (1-5000, default is 30)
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries outputsize(Integer outputsize) {
    addParam("outputsize", outputsize);
    return this;
  }

  /**
   * Sets the start date for the data range.
   *
   * @param startDate the start date in YYYY-MM-DD format
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries startDate(String startDate) {
    addParam("start_date", startDate);
    return this;
  }

  /**
   * Sets the end date for the data range.
   *
   * @param endDate the end date in YYYY-MM-DD format
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries endDate(String endDate) {
    addParam("end_date", endDate);
    return this;
  }

  /**
   * Sets the decimal places for price formatting.
   *
   * @param dp the number of decimal places (e.g., 2 for "150.25", 4 for "150.2500")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries dp(Integer dp) {
    addParam("dp", dp);
    return this;
  }

  /**
   * Sets the timezone for the time series data.
   *
   * @param timezone the timezone string (e.g., "America/New_York", "Europe/London")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries timezone(String timezone) {
    addParam("timezone", timezone);
    return this;
  }

  /**
   * Sets the timezone for the time series data using a ZoneId.
   *
   * <p>This method provides type-safe timezone setting using Java's ZoneId class.</p>
   *
   * @param zoneId the ZoneId for the timezone (e.g., ZoneId.of("America/New_York"))
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries timezone(ZoneId zoneId) {
    addParam("timezone", zoneId.getId());
    return this;
  }

  /**
   * Sets the sort order for the time series data.
   *
   * @param order the sort order ("asc" for ascending, "desc" for descending)
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries order(String order) {
    addParam("order", order);
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost "true" to include pre/post market data, "false" otherwise
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries prepost(String prepost) {
    addParam("prepost", prepost);
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost true to include pre/post market data, false otherwise
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries prepost(Boolean prepost) {
    addParam("prepost", prepost);
    return this;
  }

  /**
   * Sets the specific date for data retrieval.
   *
   * @param date the specific date in YYYY-MM-DD format
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries date(String date) {
    addParam("date", date);
    return this;
  }

  /**
   * Sets the Market Identifier Code (MIC) for the request.
   *
   * @param micCode the MIC code (e.g., "XNAS" for NASDAQ, "XNYS" for NYSE)
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries micCode(String micCode) {
    addParam("mic_code", micCode);
    return this;
  }

  /**
   * Sets the previous close price for calculations.
   *
   * @param previousClose the previous close price
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries previousClose(String previousClose) {
    addParam("previous_close", previousClose);
    return this;
  }

  /**
   * Sets the adjustment type for the data.
   *
   * @param adjust the adjustment type ("split", "dividend", "split,div")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries adjust(String adjust) {
    addParam("adjust", adjust);
    return this;
  }

  /**
   * Sets the Financial Instrument Global Identifier (FIGI) for the request.
   *
   * @param figi the FIGI identifier
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries figi(String figi) {
    addParam("figi", figi);
    return this;
  }

  /**
   * Sets the International Securities Identification Number (ISIN) for the request.
   *
   * @param isin the ISIN identifier
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries isin(String isin) {
    addParam("isin", isin);
    return this;
  }

  /**
   * Sets the CUSIP identifier for the request.
   *
   * @param cusip the CUSIP identifier
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries cusip(String cusip) {
    addParam("cusip", cusip);
    return this;
  }

  /**
   * Sets the output format for the response.
   *
   * @param format the output format ("JSON", "CSV")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries format(String format) {
    addParam("format", format);
    return this;
  }

  /**
   * Sets the delimiter for CSV format responses.
   *
   * @param delimiter the delimiter character (e.g., ",", ";", "|")
   * @return this TimeSeries instance for method chaining
   */
  public TimeSeries delimiter(String delimiter) {
    addParam("delimiter", delimiter);
    return this;
  }

  /**
   * Executes the time series request and returns the response as a typed object.
   *
   * <p>This method makes the actual API call to retrieve time series data based on the 
   * configured parameters. The response is automatically parsed into a 
   * {@link TimeSeriesResponse} object containing historical OHLC data.</p>
   *
   * @return a {@link TimeSeriesResponse} object containing the time series data
   * @throws TwelveDataException if the API request fails or returns an error
   * 
   * @see TimeSeriesResponse
   * @see TwelveDataException
   */
  public TimeSeriesResponse asObject() throws TwelveDataException {
    return ModelUtils.toTimeSeriesResponse(asJson());
  }
}
