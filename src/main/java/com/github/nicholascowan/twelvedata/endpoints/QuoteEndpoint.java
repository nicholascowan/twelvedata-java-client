package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.models.ModelUtils;
import com.github.nicholascowan.twelvedata.models.QuoteResponse;
import java.time.ZoneId;

/** Endpoint for real-time quotes. */
public class QuoteEndpoint extends Endpoint {

  /**
   * Constructs a new QuoteEndpoint with the given context.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   */
  public QuoteEndpoint(TwelveDataContext context) {
    super(context);
  }

  /**
   * Constructs a new QuoteEndpoint with the given context and symbol.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol
   */
  public QuoteEndpoint(TwelveDataContext context, String symbol) {
    super(context);
    addParam("symbol", symbol);
  }

  /**
   * Constructs a new QuoteEndpoint with the given context and parameters.
   *
   * @param context the TwelveData context containing configuration and HTTP client
   * @param symbol the stock symbol
   * @param interval the time interval
   * @param exchange the exchange name
   * @param country the country code
   * @param volumeTimePeriod the volume time period
   * @param type the security type
   * @param dp the decimal places
   * @param timezone the timezone
   * @param prepost whether to include pre/post market data
   * @param micCode the MIC code
   * @param eod whether to include end-of-day data
   * @param rollingPeriod the rolling period
   */
  public QuoteEndpoint(
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

  // Builder methods for fluent API
  public QuoteEndpoint symbol(String symbol) {
    addParam("symbol", symbol);
    return this;
  }

  public QuoteEndpoint interval(String interval) {
    addParam("interval", interval);
    return this;
  }

  public QuoteEndpoint exchange(String exchange) {
    addParam("exchange", exchange);
    return this;
  }

  public QuoteEndpoint country(String country) {
    addParam("country", country);
    return this;
  }

  public QuoteEndpoint volumeTimePeriod(String volumeTimePeriod) {
    addParam("volume_time_period", volumeTimePeriod);
    return this;
  }

  public QuoteEndpoint volumeTimePeriod(Integer volumeTimePeriod) {
    addParam("volume_time_period", volumeTimePeriod);
    return this;
  }

  public QuoteEndpoint type(String type) {
    addParam("type", type);
    return this;
  }

  public QuoteEndpoint dp(Integer dp) {
    addParam("dp", dp);
    return this;
  }

  public QuoteEndpoint timezone(String timezone) {
    addParam("timezone", timezone);
    return this;
  }

  /**
   * Sets the timezone using a ZoneId.
   *
   * @param zoneId the ZoneId for the timezone (e.g., ZoneId.of("America/New_York"))
   * @return this QuoteEndpoint for method chaining
   */
  public QuoteEndpoint timezone(ZoneId zoneId) {
    addParam("timezone", zoneId.getId());
    return this;
  }

  public QuoteEndpoint prepost(String prepost) {
    addParam("prepost", prepost);
    return this;
  }

  public QuoteEndpoint prepost(Boolean prepost) {
    addParam("prepost", prepost);
    return this;
  }

  public QuoteEndpoint micCode(String micCode) {
    addParam("mic_code", micCode);
    return this;
  }

  public QuoteEndpoint eod(String eod) {
    addParam("eod", eod);
    return this;
  }

  public QuoteEndpoint eod(Boolean eod) {
    addParam("eod", eod);
    return this;
  }

  public QuoteEndpoint rollingPeriod(String rollingPeriod) {
    addParam("rolling_period", rollingPeriod);
    return this;
  }

  public QuoteEndpoint rollingPeriod(Integer rollingPeriod) {
    addParam("rolling_period", rollingPeriod);
    return this;
  }

  public QuoteEndpoint figi(String figi) {
    addParam("figi", figi);
    return this;
  }

  public QuoteEndpoint isin(String isin) {
    addParam("isin", isin);
    return this;
  }

  public QuoteEndpoint cusip(String cusip) {
    addParam("cusip", cusip);
    return this;
  }

  public QuoteEndpoint format(String format) {
    addParam("format", format);
    return this;
  }

  public QuoteEndpoint delimiter(String delimiter) {
    addParam("delimiter", delimiter);
    return this;
  }

  public QuoteResponse asObject()
      throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException {
    return ModelUtils.toQuoteResponse(asJson());
  }
}
