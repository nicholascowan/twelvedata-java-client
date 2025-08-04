package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
import com.github.nicholascowan.twelvedata.models.DailyResponse;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;
import com.github.nicholascowan.twelvedata.models.ModelUtils;
import io.micrometer.core.annotation.Timed;
import java.time.ZoneId;

/**
 * Provides access to daily time series data (OHLC - Open, High, Low, Close) from the TwelveData API.
 * 
 * <p>This endpoint is a convenience wrapper around the TimeSeries endpoint with a fixed interval of "1day".
 * It allows you to retrieve daily historical price data for stocks, ETFs, and other financial instruments
 * without having to specify the interval parameter.</p>
 * 
 * <p>Example usage:</p>
 * <pre>{@code
 * TwelveDataClient client = new TwelveDataClient("your-api-key");
 * 
 * // Get daily time series data
 * DailyResponse response = client.daily("AAPL")
 *     .outputsize(30)
 *     .asObject();
 * 
 * // Get daily data with specific parameters
 * DailyResponse response = client.daily("AAPL")
 *     .startDate("2024-01-01")
 *     .endDate("2024-01-31")
 *     .timezone("America/New_York")
 *     .asObject();
 * }</pre>
 * 
 * <p>The fluent API allows for method chaining to configure multiple parameters:</p>
 * <pre>{@code
 * Daily daily = client.daily("MSFT")
 *     .outputsize(50)
 *     .timezone(ZoneId.of("America/New_York"))
 *     .startDate("2024-01-01")
 *     .endDate("2024-01-31")
 *     .order("desc");
 * 
 * DailyResponse response = daily.asObject();
 * }</pre>
 * 
 * @see TwelveDataClient#daily()
 * @see TwelveDataClient#daily(String)
 * @see TimeSeries
 * @see DailyResponse
 * @see TimeSeriesResponse
 * @see TwelveDataException
 */
public class Daily extends TimeSeries {

  /**
   * Constructs a new Daily endpoint with the given context.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   */
  public Daily(TwelveDataContext context) {
    super(context);
    // Set the fixed interval to "1day"
    addParam(ApiParameters.INTERVAL, "1day");
  }

  /**
   * Constructs a new Daily endpoint with the given context and symbol.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   */
  public Daily(TwelveDataContext context, String symbol) {
    super(context, symbol, "1day");
  }

  /**
   * Constructs a new Daily endpoint with the given context and all parameters.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
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
  public Daily(
      TwelveDataContext context,
      String symbol,
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
    super(context, symbol, "1day", exchange, country, type, outputsize, startDate, endDate, dp, timezone, order, prepost, date, micCode, previousClose, adjust);
  }

  @Override
  protected String getEndpointName() {
    return "time_series";
  }

  /**
   * Sets the stock symbol for the daily request.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily symbol(String symbol) {
    super.symbol(symbol);
    return this;
  }

  /**
   * Sets the exchange for the daily request.
   *
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE", "LSE")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily exchange(String exchange) {
    super.exchange(exchange);
    return this;
  }

  /**
   * Sets the country code for the daily request.
   *
   * @param country the country code (e.g., "US", "CA", "GB")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily country(String country) {
    super.country(country);
    return this;
  }

  /**
   * Sets the security type for the daily request.
   *
   * @param type the security type (e.g., "Common Stock", "ETF", "Bond")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily type(String type) {
    super.type(type);
    return this;
  }

  /**
   * Sets the number of data points to return.
   *
   * @param outputsize the number of data points (1-5000, default is 30)
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily outputsize(Integer outputsize) {
    super.outputsize(outputsize);
    return this;
  }

  /**
   * Sets the start date for the data range.
   *
   * @param startDate the start date in YYYY-MM-DD format
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily startDate(String startDate) {
    super.startDate(startDate);
    return this;
  }

  /**
   * Sets the end date for the data range.
   *
   * @param endDate the end date in YYYY-MM-DD format
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily endDate(String endDate) {
    super.endDate(endDate);
    return this;
  }

  /**
   * Sets the decimal places for price formatting.
   *
   * @param dp the number of decimal places (e.g., 2 for "150.25", 4 for "150.2500")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily dp(Integer dp) {
    super.dp(dp);
    return this;
  }

  /**
   * Sets the timezone for the daily data.
   *
   * @param timezone the timezone string (e.g., "America/New_York", "Europe/London")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily timezone(String timezone) {
    super.timezone(timezone);
    return this;
  }

  /**
   * Sets the timezone for the daily data using a ZoneId.
   *
   * <p>This method provides type-safe timezone setting using Java's ZoneId class.</p>
   *
   * @param zoneId the ZoneId for the timezone (e.g., ZoneId.of("America/New_York"))
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily timezone(ZoneId zoneId) {
    super.timezone(zoneId);
    return this;
  }

  /**
   * Sets the sort order for the daily data.
   *
   * @param order the sort order ("asc" for ascending, "desc" for descending)
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily order(String order) {
    super.order(order);
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost "true" to include pre/post market data, "false" otherwise
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily prepost(String prepost) {
    super.prepost(prepost);
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost true to include pre/post market data, false otherwise
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily prepost(Boolean prepost) {
    super.prepost(prepost);
    return this;
  }

  /**
   * Sets the specific date for data retrieval.
   *
   * @param date the specific date in YYYY-MM-DD format
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily date(String date) {
    super.date(date);
    return this;
  }

  /**
   * Sets the Market Identifier Code (MIC) for the request.
   *
   * @param micCode the MIC code (e.g., "XNAS" for NASDAQ, "XNYS" for NYSE)
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily micCode(String micCode) {
    super.micCode(micCode);
    return this;
  }

  /**
   * Sets the previous close price for calculations.
   *
   * @param previousClose the previous close price
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily previousClose(String previousClose) {
    super.previousClose(previousClose);
    return this;
  }

  /**
   * Sets the adjustment type for the data.
   *
   * @param adjust the adjustment type ("split", "dividend", "split,div")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily adjust(String adjust) {
    super.adjust(adjust);
    return this;
  }

  /**
   * Sets the Financial Instrument Global Identifier (FIGI) for the request.
   *
   * @param figi the FIGI identifier
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily figi(String figi) {
    super.figi(figi);
    return this;
  }

  /**
   * Sets the International Securities Identification Number (ISIN) for the request.
   *
   * @param isin the ISIN identifier
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily isin(String isin) {
    super.isin(isin);
    return this;
  }

  /**
   * Sets the CUSIP identifier for the request.
   *
   * @param cusip the CUSIP identifier
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily cusip(String cusip) {
    super.cusip(cusip);
    return this;
  }

  /**
   * Sets the output format for the response.
   *
   * @param format the output format ("JSON", "CSV")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily format(String format) {
    super.format(format);
    return this;
  }

  /**
   * Sets the delimiter for CSV format responses.
   *
   * @param delimiter the delimiter character (e.g., ",", ";", "|")
   * @return this Daily instance for method chaining
   */
  @Override
  public Daily delimiter(String delimiter) {
    super.delimiter(delimiter);
    return this;
  }

  /**
   * Executes the daily request and returns the response as a typed object.
   *
   * <p>This method makes the actual API call to retrieve daily time series data based on the 
   * configured parameters. The response is automatically parsed into a 
   * {@link DailyResponse} object containing historical OHLC data.</p>
   *
   * @return a {@link DailyResponse} object containing the daily time series data
   * @throws TwelveDataException if the API request fails or returns an error
   * 
   * @see DailyResponse
   * @see TwelveDataException
   */
  @Override
  @Timed(value = "twelvedata.daily.duration", 
         description = "Time taken for Daily API calls",
         extraTags = {"endpoint", "daily"})
  public DailyResponse asObject() throws TwelveDataException {
    return ModelUtils.toDailyResponse(asJson());
  }
} 