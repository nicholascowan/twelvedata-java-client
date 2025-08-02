package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
import com.github.nicholascowan.twelvedata.models.ModelUtils;
import com.github.nicholascowan.twelvedata.models.PriceResponse;

/**
 * Provides access to real-time price data from the TwelveData API.
 * 
 * <p>This endpoint allows you to retrieve current price information for stocks, ETFs, 
 * and other financial instruments. The Price endpoint supports various parameters 
 * including symbol, exchange, country, and data format preferences.</p>
 * 
 * <p>Example usage:</p>
 * <pre>{@code
 * TwelveDataClient client = new TwelveDataClient("your-api-key");
 * 
 * // Get basic price data
 * PriceResponse response = client.price("AAPL").asObject();
 * 
 * // Get price with specific exchange and format
 * PriceResponse response = client.price("AAPL")
 *     .exchange("NASDAQ")
 *     .country("US")
 *     .dp(2)
 *     .asObject();
 * }</pre>
 * 
 * <p>The fluent API allows for method chaining to configure multiple parameters:</p>
 * <pre>{@code
 * Price price = client.price("MSFT")
 *     .exchange("NASDAQ")
 *     .type("Common Stock")
 *     .prepost(true);
 * }</pre>
 * 
 * @see TwelveDataClient#price()
 * @see TwelveDataClient#price(String)
 * @see PriceResponse
 * @see TwelveDataException
 */
public class Price extends Endpoint {

  /**
   * Constructs a new Price endpoint with the given context.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   */
  public Price(TwelveDataContext context) {
    super(context);
  }

  /**
   * Constructs a new Price endpoint with the given context and symbol.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   */
  public Price(TwelveDataContext context, String symbol) {
    super(context);
    addParam(ApiParameters.SYMBOL, symbol);
  }

  /**
   * Constructs a new Price endpoint with the given context and all parameters.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT")
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE")
   * @param country the country code (e.g., "US", "CA")
   * @param type the security type (e.g., "Common Stock", "ETF")
   * @param dp the decimal places for price formatting
   * @param prepost whether to include pre/post market data ("true"/"false")
   * @param micCode the Market Identifier Code (e.g., "XNAS", "XNYS")
   */
  public Price(
      TwelveDataContext context,
      String symbol,
      String exchange,
      String country,
      String type,
      Integer dp,
      String prepost,
      String micCode) {
    super(context);
    addParam(ApiParameters.SYMBOL, symbol);
    addParam(ApiParameters.EXCHANGE, exchange);
    addParam(ApiParameters.COUNTRY, country);
    addParam(ApiParameters.TYPE, type);
    addParam(ApiParameters.DP, dp);
    addParam(ApiParameters.PREPOST, prepost);
    addParam(ApiParameters.MIC_CODE, micCode);
  }

  @Override
  protected String getEndpointName() {
    return "price";
  }

  /**
   * Sets the stock symbol for the price request.
   *
   * @param symbol the stock symbol (e.g., "AAPL", "MSFT", "GOOGL")
   * @return this Price instance for method chaining
   */
  public Price symbol(String symbol) {
    addParam(ApiParameters.SYMBOL, symbol);
    return this;
  }

  /**
   * Sets the exchange for the price request.
   *
   * @param exchange the exchange name (e.g., "NASDAQ", "NYSE", "LSE")
   * @return this Price instance for method chaining
   */
  public Price exchange(String exchange) {
    addParam(ApiParameters.EXCHANGE, exchange);
    return this;
  }

  /**
   * Sets the country code for the price request.
   *
   * @param country the country code (e.g., "US", "CA", "GB")
   * @return this Price instance for method chaining
   */
  public Price country(String country) {
    addParam(ApiParameters.COUNTRY, country);
    return this;
  }

  /**
   * Sets the security type for the price request.
   *
   * @param type the security type (e.g., "Common Stock", "ETF", "Bond")
   * @return this Price instance for method chaining
   */
  public Price type(String type) {
    addParam(ApiParameters.TYPE, type);
    return this;
  }

  /**
   * Sets the decimal places for price formatting.
   *
   * @param dp the number of decimal places (e.g., 2 for "150.25", 4 for "150.2500")
   * @return this Price instance for method chaining
   */
  public Price dp(Integer dp) {
    addParam(ApiParameters.DP, dp);
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost "true" to include pre/post market data, "false" otherwise
   * @return this Price instance for method chaining
   */
  public Price prepost(String prepost) {
    addParam(ApiParameters.PREPOST, prepost);
    return this;
  }

  /**
   * Sets whether to include pre/post market data.
   *
   * @param prepost true to include pre/post market data, false otherwise
   * @return this Price instance for method chaining
   */
  public Price prepost(Boolean prepost) {
    addParam(ApiParameters.PREPOST, prepost);
    return this;
  }

  /**
   * Sets the Market Identifier Code (MIC) for the request.
   *
   * @param micCode the MIC code (e.g., "XNAS" for NASDAQ, "XNYS" for NYSE)
   * @return this Price instance for method chaining
   */
  public Price micCode(String micCode) {
    addParam(ApiParameters.MIC_CODE, micCode);
    return this;
  }

  /**
   * Sets the Financial Instrument Global Identifier (FIGI) for the request.
   *
   * @param figi the FIGI identifier
   * @return this Price instance for method chaining
   */
  public Price figi(String figi) {
    addParam(ApiParameters.FIGI, figi);
    return this;
  }

  /**
   * Sets the International Securities Identification Number (ISIN) for the request.
   *
   * @param isin the ISIN identifier
   * @return this Price instance for method chaining
   */
  public Price isin(String isin) {
    addParam(ApiParameters.ISIN, isin);
    return this;
  }

  /**
   * Sets the CUSIP identifier for the request.
   *
   * @param cusip the CUSIP identifier
   * @return this Price instance for method chaining
   */
  public Price cusip(String cusip) {
    addParam(ApiParameters.CUSIP, cusip);
    return this;
  }

  /**
   * Sets the output format for the response.
   *
   * @param format the output format ("JSON", "CSV")
   * @return this Price instance for method chaining
   */
  public Price format(String format) {
    addParam(ApiParameters.FORMAT, format);
    return this;
  }

  /**
   * Sets the delimiter for CSV format responses.
   *
   * @param delimiter the delimiter character (e.g., ",", ";", "|")
   * @return this Price instance for method chaining
   */
  public Price delimiter(String delimiter) {
    addParam(ApiParameters.DELIMITER, delimiter);
    return this;
  }

  /**
   * Executes the price request and returns the response as a typed object.
   *
   * <p>This method makes the actual API call to retrieve price data based on the 
   * configured parameters. The response is automatically parsed into a 
   * {@link PriceResponse} object.</p>
   *
   * @return a {@link PriceResponse} object containing the price data
   * @throws TwelveDataException if the API request fails or returns an error
   * 
   * @see PriceResponse
   * @see TwelveDataException
   */
  public PriceResponse asObject() throws TwelveDataException {
    return ModelUtils.toPriceResponse(asJson());
  }
}
