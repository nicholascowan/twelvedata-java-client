package com.github.nicholascowan.twelvedata.models;

/**
 * Individual time series data point (OHLCV).
 */
public class TimeSeriesValue {
    private String datetime;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    
    // Default constructor
    public TimeSeriesValue() {}
    
    // Constructor with all fields
    public TimeSeriesValue(String datetime, String open, String high, String low, String close, String volume) {
        this.datetime = datetime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }
    
    // Getters and Setters
    public String getDatetime() {
        return datetime;
    }
    
    public void setDatetime(String datetime) {
        this.datetime = datetime;
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
    
    @Override
    public String toString() {
        return "TimeSeriesValue{" +
                "datetime='" + datetime + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }
} 