package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.models.ModelUtils;
import com.github.nicholascowan.twelvedata.models.PriceResponse;

/** Endpoint for real-time prices. */
public class PriceEndpoint extends Endpoint {

  /**
   * Constructs a new PriceEndpoint with the given context.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   */
  public PriceEndpoint(TwelveDataContext context) {
    super(context);
  }

  /**
   * Constructs a new PriceEndpoint with the given context and symbol.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol
   */
  public PriceEndpoint(TwelveDataContext context, String symbol) {
    super(context);
    addParam("symbol", symbol);
  }

  /**
   * Constructs a new PriceEndpoint with the given context and parameters.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol
   * @param exchange the exchange name
   * @param country the country code
   * @param type the security type
   * @param dp the decimal places
   * @param prepost whether to include pre/post market data
   * @param micCode the MIC code
   */
  public PriceEndpoint(
      TwelveDataContext context,
      String symbol,
      String exchange,
      String country,
      String type,
      Integer dp,
      String prepost,
      String micCode) {
    super(context);
    addParam("symbol", symbol);
    addParam("exchange", exchange);
    addParam("country", country);
    addParam("type", type);
    addParam("dp", dp);
    addParam("prepost", prepost);
    addParam("mic_code", micCode);
  }

  @Override
  protected String getEndpointName() {
    return "price";
  }

  // Builder methods for fluent API
  public PriceEndpoint symbol(String symbol) {
    addParam("symbol", symbol);
    return this;
  }

  public PriceEndpoint exchange(String exchange) {
    addParam("exchange", exchange);
    return this;
  }

  public PriceEndpoint country(String country) {
    addParam("country", country);
    return this;
  }

  public PriceEndpoint type(String type) {
    addParam("type", type);
    return this;
  }

  public PriceEndpoint dp(Integer dp) {
    addParam("dp", dp);
    return this;
  }

  public PriceEndpoint prepost(String prepost) {
    addParam("prepost", prepost);
    return this;
  }

  public PriceEndpoint prepost(Boolean prepost) {
    addParam("prepost", prepost);
    return this;
  }

  public PriceEndpoint micCode(String micCode) {
    addParam("mic_code", micCode);
    return this;
  }

  public PriceEndpoint figi(String figi) {
    addParam("figi", figi);
    return this;
  }

  public PriceEndpoint isin(String isin) {
    addParam("isin", isin);
    return this;
  }

  public PriceEndpoint cusip(String cusip) {
    addParam("cusip", cusip);
    return this;
  }

  public PriceEndpoint format(String format) {
    addParam("format", format);
    return this;
  }

  public PriceEndpoint delimiter(String delimiter) {
    addParam("delimiter", delimiter);
    return this;
  }

  /**
   * Get price data as a typed model object.
   *
   * @return PriceResponse object
   * @throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException if the request fails
   */
  public PriceResponse asObject()
      throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException {
    return ModelUtils.toPriceResponse(asJson());
  }
}
