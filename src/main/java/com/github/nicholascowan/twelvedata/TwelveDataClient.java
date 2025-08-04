package com.github.nicholascowan.twelvedata;

import com.github.nicholascowan.twelvedata.config.TwelveDataConfig;
import com.github.nicholascowan.twelvedata.endpoints.Daily;
import com.github.nicholascowan.twelvedata.endpoints.EndOfDay;
import com.github.nicholascowan.twelvedata.endpoints.Price;
import com.github.nicholascowan.twelvedata.endpoints.Quote;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeries;
import com.github.nicholascowan.twelvedata.models.DailyResponse;
import com.github.nicholascowan.twelvedata.http.DefaultHttpClient;
import com.github.nicholascowan.twelvedata.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Main client class for the TwelveData API. Provides access to all API endpoints and functionality
 * for retrieving financial market data.
 *
 * <p>This client supports the following endpoints:
 *
 * <ul>
 *   <li><strong>Time Series</strong> - Historical OHLC (Open, High, Low, Close) data
 *   <li><strong>Daily</strong> - Daily time series data (convenience wrapper for 1day interval)
 *   <li><strong>Quote</strong> - Real-time quote information
 *   <li><strong>Price</strong> - Current price data
 *   <li><strong>EndOfDay</strong> - End-of-day closing price data
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * TwelveDataClient client = new TwelveDataClient("your-api-key");
 *
 * // Get time series data
 * TimeSeries ts = client.timeSeries("AAPL", "1day")
 *     .outputsize(5)
 *     .timezone("America/New_York");
 * JsonNode data = ts.asJson();
 *
 * // Get quote data
 * JsonNode quote = client.quote("AAPL").asJson();
 *
 * // Get price data
 * JsonNode price = client.price("AAPL").asJson();
 * 
 * // Get daily time series data
 * DailyResponse dailyData = client.daily("AAPL").asObject();
 * 
 * // Get end-of-day data
 * JsonNode eod = client.endOfDay("AAPL").asJson();
 * }</pre>
 *
 * @see TimeSeries
 * @see Daily
 * @see DailyResponse
 * @see Quote
 * @see Price
 * @see EndOfDay
 * @see TwelveDataContext
 */
@Component
public class TwelveDataClient {

  private final TwelveDataContext context;

  /**
   * Creates a client with the provided API key and default configuration.
   *
   * @param apiKey the API key for authentication with TwelveData
   * @throws IllegalArgumentException if apiKey is null or empty
   */
  public TwelveDataClient(String apiKey) {
    this(apiKey, "https://api.twelvedata.com", new DefaultHttpClient("https://api.twelvedata.com"));
  }

  /**
   * Creates a client with the provided API key and custom base URL.
   *
   * @param apiKey the API key for authentication with TwelveData
   * @param baseUrl the base URL for the API (e.g., "https://api.twelvedata.com")
   * @throws IllegalArgumentException if apiKey or baseUrl is null or empty
   */
  public TwelveDataClient(String apiKey, String baseUrl) {
    this(apiKey, baseUrl, new DefaultHttpClient(baseUrl));
  }

  /**
   * Creates a client with the provided API key, base URL, and HTTP client.
   *
   * @param apiKey the API key for authentication with TwelveData
   * @param baseUrl the base URL for the API
   * @param httpClient the HTTP client to use for API requests
   * @throws IllegalArgumentException if any parameter is null
   */
  public TwelveDataClient(String apiKey, String baseUrl, HttpClient httpClient) {
    Map<String, String> defaults = new HashMap<>();
    defaults.put("outputsize", "30");
    defaults.put("timezone", "Exchange");
    defaults.put("order", "desc");
    defaults.put("prepost", "false");
    defaults.put("dp", "5");

    this.context = new TwelveDataContext(apiKey, baseUrl, httpClient, defaults);
  }

  /**
   * Creates a client with Spring configuration.
   *
   * @param config the Spring configuration object containing API settings
   * @throws IllegalArgumentException if config is null
   */
  @Autowired
  public TwelveDataClient(TwelveDataConfig config) {
    this.context =
        new TwelveDataContext(
            config.getApi().getBaseUrl(), // Note: API key should be injected separately
            config,
            new DefaultHttpClient(config.getApi().getBaseUrl(), config.getApi().getTimeout()));
  }

  /**
   * Creates a time series endpoint for getting OHLC (Open, High, Low, Close) data.
   *
   * @return a new TimeSeries instance
   */
  public TimeSeries timeSeries() {
    return new TimeSeries(context);
  }

  /**
   * Creates a time series endpoint with symbol and interval.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @param interval the time interval (e.g., "1min", "5min", "1day", "1week")
   * @return a new TimeSeries instance configured with the symbol and interval
   */
  public TimeSeries timeSeries(String symbol, String interval) {
    return new TimeSeries(context, symbol, interval);
  }

  /**
   * Creates a time series endpoint with all parameters.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @param interval the time interval (e.g., "1min", "5min", "1day", "1week")
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE")
   * @param country the country code (e.g., "US", "CA")
   * @param type the security type (e.g., "Common Stock", "ETF")
   * @param outputsize the number of data points to return (1-5000)
   * @param startDate the start date in YYYY-MM-DD format
   * @param endDate the end date in YYYY-MM-DD format
   * @param dp the number of decimal places for price values
   * @param timezone the timezone for the data (e.g., "America/New_York")
   * @param order the sort order ("asc" or "desc")
   * @param prepost whether to include pre/post market data ("true" or "false")
   * @param date the specific date in YYYY-MM-DD format
   * @param micCode the Market Identifier Code
   * @param previousClose the previous close price
   * @param adjust whether to adjust for splits/dividends ("true" or "false")
   * @return a new TimeSeries instance with all parameters configured
   */
  public TimeSeries timeSeries(
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
    return new TimeSeries(
        context,
        symbol,
        interval,
        exchange,
        country,
        type,
        outputsize,
        startDate,
        endDate,
        dp,
        timezone,
        order,
        prepost,
        date,
        micCode,
        previousClose,
        adjust);
  }

  /**
   * Creates a daily endpoint for getting daily time series data.
   *
   * @return a new Daily instance
   */
  public Daily daily() {
    return new Daily(context);
  }

  /**
   * Creates a daily endpoint with symbol.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @return a new Daily instance configured with the symbol
   */
  public Daily daily(String symbol) {
    return new Daily(context, symbol);
  }

  /**
   * Creates a daily endpoint with all parameters.
   *
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
   * @param prepost whether to include pre/post market data ("true" or "false")
   * @param date the specific date for data retrieval (YYYY-MM-DD format)
   * @param micCode the Market Identifier Code
   * @param previousClose the previous close price
   * @param adjust whether to adjust for splits/dividends ("true" or "false")
   * @return a new Daily instance with all parameters configured
   */
  public Daily daily(
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
    return new Daily(
        context,
        symbol,
        exchange,
        country,
        type,
        outputsize,
        startDate,
        endDate,
        dp,
        timezone,
        order,
        prepost,
        date,
        micCode,
        previousClose,
        adjust);
  }

  /**
   * Creates a quote endpoint for getting real-time quotes.
   *
   * @return a new Quote instance
   */
  public Quote quote() {
    return new Quote(context);
  }

  /**
   * Creates a quote endpoint with symbol.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @return a new Quote instance configured with the symbol
   */
  public Quote quote(String symbol) {
    return new Quote(context, symbol);
  }

  /**
   * Creates a price endpoint for getting real-time prices.
   *
   * @return a new Price instance
   */
  public Price price() {
    return new Price(context);
  }

  /**
   * Creates a price endpoint with symbol.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @return a new Price instance configured with the symbol
   */
  public Price price(String symbol) {
    return new Price(context, symbol);
  }

  /**
   * Creates an end-of-day endpoint for getting end-of-day closing prices.
   *
   * @return a new EndOfDay instance
   */
  public EndOfDay endOfDay() {
    return new EndOfDay(context);
  }

  /**
   * Creates an end-of-day endpoint with symbol.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @return a new EndOfDay instance configured with the symbol
   */
  public EndOfDay endOfDay(String symbol) {
    return new EndOfDay(context, symbol);
  }

  /**
   * Creates an end-of-day endpoint with all parameters.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @param figi the Financial Instrument Global Identifier
   * @param isin the International Securities Identification Number
   * @param cusip the CUSIP identifier
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE")
   * @param micCode the Market Identifier Code (e.g., "XNAS", "XNYS")
   * @param country the country code (e.g., "US", "CA")
   * @param type the security type (e.g., "Common Stock", "ETF")
   * @param date the specific date for data retrieval (YYYY-MM-DD format)
   * @param prepost whether to include pre/post market data
   * @param dp the decimal places for price formatting
   * @return a new EndOfDay instance with all parameters configured
   */
  public EndOfDay endOfDay(
      String symbol,
      String figi,
      String isin,
      String cusip,
      String exchange,
      String micCode,
      String country,
      String type,
      String date,
      Boolean prepost,
      Integer dp) {
    return new EndOfDay(
        context,
        symbol,
        figi,
        isin,
        cusip,
        exchange,
        micCode,
        country,
        type,
        date,
        prepost,
        dp);
  }

  /**
   * Gets the underlying context used by this client.
   *
   * @return the TwelveDataContext instance
   */
  public TwelveDataContext getContext() {
    return context;
  }

  /**
   * Updates the default parameters used for API requests.
   *
   * @param newDefaults a map of parameter names to values to set as defaults
   * @throws IllegalArgumentException if newDefaults is null
   */
  public void updateDefaults(Map<String, String> newDefaults) {
    context.updateDefaults(newDefaults);
  }
}
