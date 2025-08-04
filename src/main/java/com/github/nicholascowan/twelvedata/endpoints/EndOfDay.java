package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
import com.github.nicholascowan.twelvedata.models.ModelUtils;
import com.github.nicholascowan.twelvedata.models.EndOfDayResponse;
import io.micrometer.core.annotation.Timed;

/**
 * Provides access to end-of-day price data from the TwelveData API.
 * 
 * <p>This endpoint allows you to retrieve end-of-day closing prices for stocks, ETFs, and other 
 * financial instruments. The EndOfDay endpoint provides the closing price for a specific date 
 * with comprehensive instrument metadata.</p>
 * 
 * <p>Example usage:</p>
 * <pre>{@code
 * TwelveDataClient client = new TwelveDataClient("your-api-key");
 * 
 * // Get end-of-day data for a symbol
 * EndOfDayResponse response = client.endOfDay("AAPL")
 *     .date("2024-01-15")
 *     .asObject();
 * 
 * // Get end-of-day data with specific parameters
 * EndOfDayResponse response = client.endOfDay("AAPL")
 *     .exchange("NASDAQ")
 *     .dp(2)
 *     .asObject();
 * }</pre>
 * 
 * <p>The fluent API allows for method chaining to configure multiple parameters:</p>
 * <pre>{@code
 * EndOfDay eod = client.endOfDay("MSFT")
 *     .exchange("NASDAQ")
 *     .country("US")
 *     .type("Common Stock")
 *     .dp(2)
 *     .date("2024-01-15");
 * }</pre>
 * 
 * @see TwelveDataClient#endOfDay()
 * @see TwelveDataClient#endOfDay(String)
 * @see EndOfDayResponse
 * @see TwelveDataException
 */
public class EndOfDay extends Endpoint {

  /**
   * Constructs a new EndOfDay endpoint with the given context.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   */
  public EndOfDay(TwelveDataContext context) {
    super(context);
    this.isPrice = true;
  }

  /**
   * Constructs a new EndOfDay endpoint with the given context and symbol.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   */
  public EndOfDay(TwelveDataContext context, String symbol) {
    super(context);
    this.isPrice = true;
    addParam(ApiParameters.SYMBOL, symbol);
  }

  /**
   * Constructs a new EndOfDay endpoint with the given context and all parameters.
   *
   * @param context the TwelveData context containing configuration and HTTP client
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
   */
  public EndOfDay(
      TwelveDataContext context,
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
    super(context);
    this.isPrice = true;
    addParam(ApiParameters.SYMBOL, symbol);
    addParam(ApiParameters.FIGI, figi);
    addParam(ApiParameters.ISIN, isin);
    addParam(ApiParameters.CUSIP, cusip);
    addParam(ApiParameters.EXCHANGE, exchange);
    addParam(ApiParameters.MIC_CODE, micCode);
    addParam(ApiParameters.COUNTRY, country);
    addParam(ApiParameters.TYPE, type);
    addParam(ApiParameters.DATE, date);
    addParam(ApiParameters.PREPOST, prepost);
    addParam(ApiParameters.DP, dp);
  }

  @Override
  protected String getEndpointName() {
    return "eod";
  }

  /**
   * Sets the stock symbol for the end-of-day request.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay symbol(String symbol) {
    addParam(ApiParameters.SYMBOL, symbol);
    return this;
  }

  /**
   * Sets the Financial Instrument Global Identifier (FIGI) for the request.
   *
   * @param figi the FIGI identifier (e.g., "BBG000BHTMY7")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay figi(String figi) {
    addParam(ApiParameters.FIGI, figi);
    return this;
  }

  /**
   * Sets the International Securities Identification Number (ISIN) for the request.
   *
   * @param isin the ISIN identifier (e.g., "US0378331005")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay isin(String isin) {
    addParam(ApiParameters.ISIN, isin);
    return this;
  }

  /**
   * Sets the CUSIP identifier for the request.
   *
   * @param cusip the CUSIP identifier (e.g., "594918104")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay cusip(String cusip) {
    addParam(ApiParameters.CUSIP, cusip);
    return this;
  }

  /**
   * Sets the exchange for the end-of-day request.
   *
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE", "LSE")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay exchange(String exchange) {
    addParam(ApiParameters.EXCHANGE, exchange);
    return this;
  }

  /**
   * Sets the Market Identifier Code (MIC) for the request.
   *
   * @param micCode the MIC code (e.g., "XNAS" for NASDAQ, "XNYS" for NYSE)
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay micCode(String micCode) {
    addParam(ApiParameters.MIC_CODE, micCode);
    return this;
  }

  /**
   * Sets the country code for the end-of-day request.
   *
   * @param country the country code (e.g., "US", "CA", "GB")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay country(String country) {
    addParam(ApiParameters.COUNTRY, country);
    return this;
  }

  /**
   * Sets the security type for the end-of-day request.
   *
   * @param type the security type (e.g., "Common Stock", "ETF", "Bond")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay type(String type) {
    addParam(ApiParameters.TYPE, type);
    return this;
  }

  /**
   * Sets the specific date for data retrieval.
   *
   * @param date the specific date in YYYY-MM-DD format (e.g., "2006-01-02")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay date(String date) {
    addParam(ApiParameters.DATE, date);
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost true to include pre/post market data, false otherwise
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay prepost(Boolean prepost) {
    addParam(ApiParameters.PREPOST, prepost);
    return this;
  }

  /**
   * Sets the decimal places for price formatting.
   *
   * @param dp the number of decimal places (0-11, default is 5)
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay dp(Integer dp) {
    addParam(ApiParameters.DP, dp);
    return this;
  }

  /**
   * Sets the output format for the response.
   *
   * @param format the output format ("JSON", "CSV")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay format(String format) {
    addParam(ApiParameters.FORMAT, format);
    return this;
  }

  /**
   * Sets the delimiter for CSV format responses.
   *
   * @param delimiter the delimiter character (e.g., ",", ";", "|")
   * @return this EndOfDay instance for method chaining
   */
  public EndOfDay delimiter(String delimiter) {
    addParam(ApiParameters.DELIMITER, delimiter);
    return this;
  }

    /**
   * Executes the end-of-day request and returns the response as a typed object.
   *
   * <p>This method makes the actual API call to retrieve end-of-day data based on the
   * configured parameters. The response is automatically parsed into a
   * {@link EndOfDayResponse} object containing the closing price and metadata.</p>
   *
   * @return an {@link EndOfDayResponse} object containing the end-of-day data
   * @throws TwelveDataException if the API request fails or returns an error
   *
   * @see EndOfDayResponse
   * @see TwelveDataException
   */
  @Timed(value = "twelvedata.endofday.duration", 
         description = "Time taken for EndOfDay API calls",
         extraTags = {"endpoint", "endofday"})
  public EndOfDayResponse asObject() throws TwelveDataException {
    return ModelUtils.toEndOfDayResponse(asJson());
  }
} 