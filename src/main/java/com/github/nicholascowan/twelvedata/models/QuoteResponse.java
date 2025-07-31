package com.github.nicholascowan.twelvedata.models;

/** Complete quote response for a stock. */
public class QuoteResponse {
  private String symbol;
  private String name;
  private String exchange;
  private String micCode;
  private String currency;
  private String datetime;
  private String timestamp;
  private String lastQuoteAt;
  private String open;
  private String high;
  private String low;
  private String close;
  private String volume;
  private String previousClose;
  private String change;
  private String percentChange;
  private String averageVolume;
  private Boolean isMarketOpen;
  private FiftyTwoWeek fiftyTwoWeek;

  /** Default constructor for QuoteResponse. */
  public QuoteResponse() {}

  /**
   * Constructs a new QuoteResponse with essential fields.
   *
   * @param symbol the stock symbol
   * @param name the company name
   * @param exchange the exchange name
   * @param micCode the MIC code
   * @param currency the currency code
   * @param datetime the date and time
   * @param close the closing price
   */
  public QuoteResponse(
      String symbol,
      String name,
      String exchange,
      String micCode,
      String currency,
      String datetime,
      String close) {
    this.symbol = symbol;
    this.name = name;
    this.exchange = exchange;
    this.micCode = micCode;
    this.currency = currency;
    this.datetime = datetime;
    this.close = close;
  }

  // Getters and Setters
  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getMicCode() {
    return micCode;
  }

  public void setMicCode(String micCode) {
    this.micCode = micCode;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDatetime() {
    return datetime;
  }

  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getLastQuoteAt() {
    return lastQuoteAt;
  }

  public void setLastQuoteAt(String lastQuoteAt) {
    this.lastQuoteAt = lastQuoteAt;
  }

  public String getOpen() {
    return open;
  }

  public void setOpen(String open) {
    this.open = open;
  }

  public String getHigh() {
    return high;
  }

  public void setHigh(String high) {
    this.high = high;
  }

  public String getLow() {
    return low;
  }

  public void setLow(String low) {
    this.low = low;
  }

  public String getClose() {
    return close;
  }

  public void setClose(String close) {
    this.close = close;
  }

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  public String getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(String previousClose) {
    this.previousClose = previousClose;
  }

  public String getChange() {
    return change;
  }

  public void setChange(String change) {
    this.change = change;
  }

  public String getPercentChange() {
    return percentChange;
  }

  public void setPercentChange(String percentChange) {
    this.percentChange = percentChange;
  }

  public String getAverageVolume() {
    return averageVolume;
  }

  public void setAverageVolume(String averageVolume) {
    this.averageVolume = averageVolume;
  }

  public Boolean getIsMarketOpen() {
    return isMarketOpen;
  }

  public void setIsMarketOpen(Boolean isMarketOpen) {
    this.isMarketOpen = isMarketOpen;
  }

  public FiftyTwoWeek getFiftyTwoWeek() {
    return fiftyTwoWeek;
  }

  public void setFiftyTwoWeek(FiftyTwoWeek fiftyTwoWeek) {
    this.fiftyTwoWeek = fiftyTwoWeek;
  }

  // Helper methods to get numeric values
  public Double getOpenAsDouble() {
    return open != null ? Double.parseDouble(open) : null;
  }

  public Double getHighAsDouble() {
    return high != null ? Double.parseDouble(high) : null;
  }

  public Double getLowAsDouble() {
    return low != null ? Double.parseDouble(low) : null;
  }

  public Double getCloseAsDouble() {
    return close != null ? Double.parseDouble(close) : null;
  }

  public Long getVolumeAsLong() {
    return volume != null ? Long.parseLong(volume) : null;
  }

  public Double getPreviousCloseAsDouble() {
    return previousClose != null ? Double.parseDouble(previousClose) : null;
  }

  public Double getChangeAsDouble() {
    return change != null ? Double.parseDouble(change) : null;
  }

  public Double getPercentChangeAsDouble() {
    return percentChange != null ? Double.parseDouble(percentChange) : null;
  }

  public Long getAverageVolumeAsLong() {
    return averageVolume != null ? Long.parseLong(averageVolume) : null;
  }

  public Long getTimestampAsLong() {
    return timestamp != null ? Long.parseLong(timestamp) : null;
  }

  @Override
  public String toString() {
    return "QuoteResponse{"
        + "symbol='"
        + symbol
        + '\''
        + ", name='"
        + name
        + '\''
        + ", exchange='"
        + exchange
        + '\''
        + ", micCode='"
        + micCode
        + '\''
        + ", currency='"
        + currency
        + '\''
        + ", datetime='"
        + datetime
        + '\''
        + ", close='"
        + close
        + '\''
        + ", change='"
        + change
        + '\''
        + ", percentChange='"
        + percentChange
        + '\''
        + ", isMarketOpen="
        + isMarketOpen
        + ", fiftyTwoWeek="
        + fiftyTwoWeek
        + '}';
  }
}
