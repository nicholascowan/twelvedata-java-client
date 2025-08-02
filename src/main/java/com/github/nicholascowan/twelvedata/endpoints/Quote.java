package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
import com.github.nicholascowan.twelvedata.models.ModelUtils;
import com.github.nicholascowan.twelvedata.models.QuoteResponse;
import java.time.ZoneId;

/**
 * Provides access to real-time quote data from the TwelveData API.
 * 
 * <p>This endpoint allows you to retrieve comprehensive quote information for stocks, ETFs, 
 * and other financial instruments. The Quote endpoint provides detailed market data including 
 * bid/ask prices, volume, open/high/low/close prices, and various technical indicators.</p>
 * 
 * <p>Example usage:</p>
 * <pre>{@code
 * TwelveDataClient client = new TwelveDataClient("your-api-key");
 * 
 * // Get basic quote data
 * QuoteResponse response = client.quote("AAPL").asObject();
 * 
 * // Get quote with specific exchange and timezone
 * QuoteResponse response = client.quote("AAPL")
 *     .exchange("NASDAQ")
 *     .timezone("America/New_York")
 *     .dp(2)
 *     .asObject();
 * }</pre>
 * 
 * <p>The fluent API allows for method chaining to configure multiple parameters:</p>
 * <pre>{@code
 * Quote quote = client.quote("MSFT")
 *     .exchange("NASDAQ")
 *     .timezone(ZoneId.of("America/New_York"))
 *     .prepost(true)
 *     .eod(true);
 * }</pre>
 * 
 * @see TwelveDataClient#quote()
 * @see TwelveDataClient#quote(String)
 * @see QuoteResponse
 * @see TwelveDataException
 */
public class Quote extends Endpoint {

  /**
   * Constructs a new Quote endpoint with the given context.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   */
  public Quote(TwelveDataContext context) {
    super(context);
  }

  /**
   * Constructs a new Quote endpoint with the given context and symbol.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   */
  public Quote(TwelveDataContext context, String symbol) {
    super(context);
    addParam("symbol", symbol);
  }

  /**
   * Constructs a new Quote endpoint with the given context and all parameters.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @param interval the time interval for the data (e.g., "1min", "5min", "1day")
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE")
   * @param country the country code (e.g., "US", "CA")
   * @param volumeTimePeriod the volume time period for calculations
   * @param type the security type (e.g., "Common Stock", "ETF")
   * @param dp the decimal places for price formatting
   * @param timezone the timezone for the data (e.g., "America/New_York")
   * @param prepost whether to include pre/post market data ("true"/"false")
   * @param micCode the Market Identifier Code (e.g., "XNAS", "XNYS")
   * @param eod whether to include end-of-day data ("true"/"false")
   * @param rollingPeriod the rolling period for calculations
   */
  public Quote(
      TwelveDataContext context,
      String symbol,
      String interval,
      String exchange,
      String country,
      String volumeTimePeriod,
      String type,
      Integer dp,
      String timezone,
      String prepost,
      String micCode,
      String eod,
      String rollingPeriod) {
    super(context);
    addParam("symbol", symbol);
    addParam("interval", interval);
    addParam("exchange", exchange);
    addParam("country", country);
    addParam("volume_time_period", volumeTimePeriod);
    addParam("type", type);
    addParam("dp", dp);
    addParam("timezone", timezone);
    addParam("prepost", prepost);
    addParam("mic_code", micCode);
    addParam("eod", eod);
    addParam("rolling_period", rollingPeriod);
  }

  @Override
  protected String getEndpointName() {
    return "quote";
  }

  /**
   * Sets the stock symbol for the quote request.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   * @return this Quote instance for method chaining
   */
  public Quote symbol(String symbol) {
    addParam("symbol", symbol);
    return this;
  }

  /**
   * Sets the time interval for the quote data.
   *
   * @param interval the time interval (e.g., "1min", "5min", "15min", "30min", "45min", "1h", "2h", "4h", "1day", "1week", "1month")
   * @return this Quote instance for method chaining
   */
  public Quote interval(String interval) {
    addParam("interval", interval);
    return this;
  }

  /**
   * Sets the exchange for the quote request.
   *
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE", "LSE")
   * @return this Quote instance for method chaining
   */
  public Quote exchange(String exchange) {
    addParam("exchange", exchange);
    return this;
  }

  /**
   * Sets the country code for the quote request.
   *
   * @param country the country code (e.g., "US", "CA", "GB")
   * @return this Quote instance for method chaining
   */
  public Quote country(String country) {
    addParam("country", country);
    return this;
  }

  /**
   * Sets the volume time period for calculations.
   *
   * @param volumeTimePeriod the volume time period (e.g., "1", "5", "10", "15", "30", "45", "60")
   * @return this Quote instance for method chaining
   */
  public Quote volumeTimePeriod(String volumeTimePeriod) {
    addParam("volume_time_period", volumeTimePeriod);
    return this;
  }

  /**
   * Sets the volume time period for calculations.
   *
   * @param volumeTimePeriod the volume time period as an integer
   * @return this Quote instance for method chaining
   */
  public Quote volumeTimePeriod(Integer volumeTimePeriod) {
    addParam("volume_time_period", volumeTimePeriod);
    return this;
  }

  /**
   * Sets the security type for the quote request.
   *
   * @param type the security type (e.g., "Common Stock", "ETF", "Bond")
   * @return this Quote instance for method chaining
   */
  public Quote type(String type) {
    addParam("type", type);
    return this;
  }

  /**
   * Sets the decimal places for price formatting.
   *
   * @param dp the number of decimal places (e.g., 2 for "150.25", 4 for "150.2500")
   * @return this Quote instance for method chaining
   */
  public Quote dp(Integer dp) {
    addParam("dp", dp);
    return this;
  }

  /**
   * Sets the timezone for the quote data.
   *
   * @param timezone the timezone string (e.g., "America/New_York", "Europe/London")
   * @return this Quote instance for method chaining
   */
  public Quote timezone(String timezone) {
    addParam("timezone", timezone);
    return this;
  }

  /**
   * Sets the timezone for the quote data using a ZoneId.
   *
   * <p>This method provides type-safe timezone setting using Java's ZoneId class.</p>
   *
   * @param zoneId the ZoneId for the timezone (e.g., ZoneId.of("America/New_York"))
   * @return this Quote instance for method chaining
   */
  public Quote timezone(ZoneId zoneId) {
    addParam("timezone", zoneId.getId());
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost "true" to include pre/post market data, "false" otherwise
   * @return this Quote instance for method chaining
   */
  public Quote prepost(String prepost) {
    addParam("prepost", prepost);
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost true to include pre/post market data, false otherwise
   * @return this Quote instance for method chaining
   */
  public Quote prepost(Boolean prepost) {
    addParam("prepost", prepost);
    return this;
  }

  /**
   * Sets the Market Identifier Code (MIC) for the request.
   *
   * @param micCode the MIC code (e.g., "XNAS" for NASDAQ, "XNYS" for NYSE)
   * @return this Quote instance for method chaining
   */
  public Quote micCode(String micCode) {
    addParam("mic_code", micCode);
    return this;
  }

  /**
   * Sets whether to include end-of-day data.
   *
   * @param eod "true" to include end-of-day data, "false" otherwise
   * @return this Quote instance for method chaining
   */
  public Quote eod(String eod) {
    addParam("eod", eod);
    return this;
  }

  /**
   * Sets whether to include end-of-day data.
   *
   * @param eod true to include end-of-day data, false otherwise
   * @return this Quote instance for method chaining
   */
  public Quote eod(Boolean eod) {
    addParam("eod", eod);
    return this;
  }

  /**
   * Sets the rolling period for calculations.
   *
   * @param rollingPeriod the rolling period as a string
   * @return this Quote instance for method chaining
   */
  public Quote rollingPeriod(String rollingPeriod) {
    addParam("rolling_period", rollingPeriod);
    return this;
  }

  /**
   * Sets the rolling period for calculations.
   *
   * @param rollingPeriod the rolling period as an integer
   * @return this Quote instance for method chaining
   */
  public Quote rollingPeriod(Integer rollingPeriod) {
    addParam("rolling_period", rollingPeriod);
    return this;
  }

  /**
   * Sets the Financial Instrument Global Identifier (FIGI) for the request.
   *
   * @param figi the FIGI identifier
   * @return this Quote instance for method chaining
   */
  public Quote figi(String figi) {
    addParam("figi", figi);
    return this;
  }

  /**
   * Sets the International Securities Identification Number (ISIN) for the request.
   *
   * @param isin the ISIN identifier
   * @return this Quote instance for method chaining
   */
  public Quote isin(String isin) {
    addParam("isin", isin);
    return this;
  }

  /**
   * Sets the CUSIP identifier for the request.
   *
   * @param cusip the CUSIP identifier
   * @return this Quote instance for method chaining
   */
  public Quote cusip(String cusip) {
    addParam("cusip", cusip);
    return this;
  }

  /**
   * Sets the output format for the response.
   *
   * @param format the output format ("JSON", "CSV")
   * @return this Quote instance for method chaining
   */
  public Quote format(String format) {
    addParam("format", format);
    return this;
  }

  /**
   * Sets the delimiter for CSV format responses.
   *
   * @param delimiter the delimiter character (e.g., ",", ";", "|")
   * @return this Quote instance for method chaining
   */
  public Quote delimiter(String delimiter) {
    addParam("delimiter", delimiter);
    return this;
  }

  /**
   * Executes the quote request and returns the response as a typed object.
   *
   * <p>This method makes the actual API call to retrieve quote data based on the 
   * configured parameters. The response is automatically parsed into a 
   * {@link QuoteResponse} object containing comprehensive market data.</p>
   *
   * @return a {@link QuoteResponse} object containing the quote data
   * @throws TwelveDataException if the API request fails or returns an error
   * 
   * @see QuoteResponse
   * @see TwelveDataException
   */
  public QuoteResponse asObject() throws TwelveDataException {
    return ModelUtils.toQuoteResponse(asJson());
  }
}
