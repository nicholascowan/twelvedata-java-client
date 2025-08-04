package com.github.nicholascowan.twelvedata.models;

/**
 * Response model for the EndOfDay endpoint.
 * 
 * <p>This class represents the response from the TwelveData API's EndOfDay endpoint,
 * which provides end-of-day price data for financial instruments.</p>
 * 
 * <p>Example response:</p>
 * <pre>{@code
 * {
 *     "symbol": "AAPL",
 *     "exchange": "NASDAQ",
 *     "mic_code": "XNAS",
 *     "currency": "USD",
 *     "datetime": "2021-09-16",
 *     "close": "148.79"
 * }
 * }</pre>
 * 
 * @see com.github.nicholascowan.twelvedata.endpoints.EndOfDay
 */
public class EndOfDayResponse {
  private String symbol;
  private String exchange;
  private String micCode;
  private String currency;
  private String datetime;
  private String close;

  /**
   * Default constructor.
   */
  public EndOfDayResponse() {}

  /**
   * Constructor with all fields.
   *
   * @param symbol the stock symbol
   * @param exchange the exchange name
   * @param micCode the Market Identifier Code
   * @param currency the currency code
   * @param datetime the date and time
   * @param close the closing price
   */
  public EndOfDayResponse(String symbol, String exchange, String micCode, 
                         String currency, String datetime, String close) {
    this.symbol = symbol;
    this.exchange = exchange;
    this.micCode = micCode;
    this.currency = currency;
    this.datetime = datetime;
    this.close = close;
  }

  /**
   * Gets the stock symbol.
   *
   * @return the stock symbol
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Sets the stock symbol.
   *
   * @param symbol the stock symbol
   */
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Gets the exchange name.
   *
   * @return the exchange name
   */
  public String getExchange() {
    return exchange;
  }

  /**
   * Sets the exchange name.
   *
   * @param exchange the exchange name
   */
  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  /**
   * Gets the Market Identifier Code (MIC).
   *
   * @return the MIC code
   */
  public String getMicCode() {
    return micCode;
  }

  /**
   * Sets the Market Identifier Code (MIC).
   *
   * @param micCode the MIC code
   */
  public void setMicCode(String micCode) {
    this.micCode = micCode;
  }

  /**
   * Gets the currency code.
   *
   * @return the currency code
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Sets the currency code.
   *
   * @param currency the currency code
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * Gets the date and time.
   *
   * @return the date and time
   */
  public String getDatetime() {
    return datetime;
  }

  /**
   * Sets the date and time.
   *
   * @param datetime the date and time
   */
  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }

  /**
   * Gets the closing price as a string.
   *
   * @return the closing price
   */
  public String getClose() {
    return close;
  }

  /**
   * Sets the closing price.
   *
   * @param close the closing price
   */
  public void setClose(String close) {
    this.close = close;
  }

  /**
   * Gets the closing price as a double.
   *
   * @return the closing price as a double, or null if parsing fails
   */
  public Double getCloseAsDouble() {
    try {
      return close != null ? Double.parseDouble(close) : null;
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * Gets the closing price as a float.
   *
   * @return the closing price as a float, or null if parsing fails
   */
  public Float getCloseAsFloat() {
    try {
      return close != null ? Float.parseFloat(close) : null;
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  public String toString() {
    return "EndOfDayResponse{"
        + "symbol='" + symbol + '\''
        + ", exchange='" + exchange + '\''
        + ", micCode='" + micCode + '\''
        + ", currency='" + currency + '\''
        + ", datetime='" + datetime + '\''
        + ", close='" + close + '\''
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
    EndOfDayResponse that = (EndOfDayResponse) obj;
    return java.util.Objects.equals(symbol, that.symbol)
        && java.util.Objects.equals(exchange, that.exchange)
        && java.util.Objects.equals(micCode, that.micCode)
        && java.util.Objects.equals(currency, that.currency)
        && java.util.Objects.equals(datetime, that.datetime)
        && java.util.Objects.equals(close, that.close);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(symbol, exchange, micCode, currency, datetime, close);
  }
} 