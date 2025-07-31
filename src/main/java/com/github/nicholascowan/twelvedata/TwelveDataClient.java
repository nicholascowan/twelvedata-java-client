package com.github.nicholascowan.twelvedata;

import com.github.nicholascowan.twelvedata.config.TwelveDataConfig;
import com.github.nicholascowan.twelvedata.endpoints.PriceEndpoint;
import com.github.nicholascowan.twelvedata.endpoints.QuoteEndpoint;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeriesEndpoint;
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
 *   <li><strong>Quote</strong> - Real-time quote information
 *   <li><strong>Price</strong> - Current price data
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * TwelveDataClient client = new TwelveDataClient("your-api-key");
 *
 * // Get time series data
 * TimeSeriesEndpoint ts = client.timeSeries("AAPL", "1day")
 *     .outputsize(5)
 *     .timezone("America/New_York");
 * JsonNode data = ts.asJson();
 *
 * // Get quote data
 * JsonNode quote = client.quote("AAPL").asJson();
 *
 * // Get price data
 * JsonNode price = client.price("AAPL").asJson();
 * }</pre>
 *
 * @see TimeSeriesEndpoint
 * @see QuoteEndpoint
 * @see PriceEndpoint
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
   * @return a new TimeSeriesEndpoint instance
   */
  public TimeSeriesEndpoint timeSeries() {
    return new TimeSeriesEndpoint(context);
  }

  /**
   * Creates a time series endpoint with symbol and interval.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @param interval the time interval (e.g., "1min", "5min", "1day", "1week")
   * @return a new TimeSeriesEndpoint instance configured with the symbol and interval
   */
  public TimeSeriesEndpoint timeSeries(String symbol, String interval) {
    return new TimeSeriesEndpoint(context, symbol, interval);
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
   * @return a new TimeSeriesEndpoint instance with all parameters configured
   */
  public TimeSeriesEndpoint timeSeries(
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
    return new TimeSeriesEndpoint(
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
   * Creates a quote endpoint for getting real-time quotes.
   *
   * @return a new QuoteEndpoint instance
   */
  public QuoteEndpoint quote() {
    return new QuoteEndpoint(context);
  }

  /**
   * Creates a quote endpoint with symbol.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @return a new QuoteEndpoint instance configured with the symbol
   */
  public QuoteEndpoint quote(String symbol) {
    return new QuoteEndpoint(context, symbol);
  }

  /**
   * Creates a price endpoint for getting real-time prices.
   *
   * @return a new PriceEndpoint instance
   */
  public PriceEndpoint price() {
    return new PriceEndpoint(context);
  }

  /**
   * Creates a price endpoint with symbol.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @return a new PriceEndpoint instance configured with the symbol
   */
  public PriceEndpoint price(String symbol) {
    return new PriceEndpoint(context, symbol);
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
