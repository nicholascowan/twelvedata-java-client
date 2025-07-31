package com.github.nicholascowan.twelvedata.models;

/** 52-week high/low data for a stock. */
public class FiftyTwoWeek {
  private String low;
  private String high;
  private String lowChange;
  private String highChange;
  private String lowChangePercent;
  private String highChangePercent;
  private String range;

  /** Default constructor for FiftyTwoWeek. */
  public FiftyTwoWeek() {}

  /**
   * Constructs a new FiftyTwoWeek with all fields.
   *
   * @param low the 52-week low price
   * @param high the 52-week high price
   * @param lowChange the change from 52-week low
   * @param highChange the change from 52-week high
   * @param lowChangePercent the percentage change from 52-week low
   * @param highChangePercent the percentage change from 52-week high
   * @param range the 52-week price range
   */
  public FiftyTwoWeek(
      String low,
      String high,
      String lowChange,
      String highChange,
      String lowChangePercent,
      String highChangePercent,
      String range) {
    this.low = low;
    this.high = high;
    this.lowChange = lowChange;
    this.highChange = highChange;
    this.lowChangePercent = lowChangePercent;
    this.highChangePercent = highChangePercent;
    this.range = range;
  }

  // Getters and Setters
  public String getLow() {
    return low;
  }

  public void setLow(String low) {
    this.low = low;
  }

  public String getHigh() {
    return high;
  }

  public void setHigh(String high) {
    this.high = high;
  }

  public String getLowChange() {
    return lowChange;
  }

  public void setLowChange(String lowChange) {
    this.lowChange = lowChange;
  }

  public String getHighChange() {
    return highChange;
  }

  public void setHighChange(String highChange) {
    this.highChange = highChange;
  }

  public String getLowChangePercent() {
    return lowChangePercent;
  }

  public void setLowChangePercent(String lowChangePercent) {
    this.lowChangePercent = lowChangePercent;
  }

  public String getHighChangePercent() {
    return highChangePercent;
  }

  public void setHighChangePercent(String highChangePercent) {
    this.highChangePercent = highChangePercent;
  }

  public String getRange() {
    return range;
  }

  public void setRange(String range) {
    this.range = range;
  }

  // Helper methods to get numeric values
  public Double getLowAsDouble() {
    return low != null ? Double.parseDouble(low) : null;
  }

  public Double getHighAsDouble() {
    return high != null ? Double.parseDouble(high) : null;
  }

  public Double getLowChangeAsDouble() {
    return lowChange != null ? Double.parseDouble(lowChange) : null;
  }

  public Double getHighChangeAsDouble() {
    return highChange != null ? Double.parseDouble(highChange) : null;
  }

  public Double getLowChangePercentAsDouble() {
    return lowChangePercent != null ? Double.parseDouble(lowChangePercent) : null;
  }

  public Double getHighChangePercentAsDouble() {
    return highChangePercent != null ? Double.parseDouble(highChangePercent) : null;
  }

  @Override
  public String toString() {
    return "FiftyTwoWeek{"
        + "low='"
        + low
        + '\''
        + ", high='"
        + high
        + '\''
        + ", lowChange='"
        + lowChange
        + '\''
        + ", highChange='"
        + highChange
        + '\''
        + ", lowChangePercent='"
        + lowChangePercent
        + '\''
        + ", highChangePercent='"
        + highChangePercent
        + '\''
        + ", range='"
        + range
        + '\''
        + '}';
  }
}
