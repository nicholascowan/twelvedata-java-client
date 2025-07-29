package com.github.nicholascowan.twelvedata.models;

/**
 * Meta information for time series data.
 */
public class TimeSeriesMeta {
    private String symbol;
    private String interval;
    private String currency;
    private String exchangeTimezone;
    private String exchange;
    private String micCode;
    private String type;
    
    // Default constructor
    public TimeSeriesMeta() {}
    
    // Constructor with all fields
    public TimeSeriesMeta(String symbol, String interval, String currency, 
                         String exchangeTimezone, String exchange, String micCode, String type) {
        this.symbol = symbol;
        this.interval = interval;
        this.currency = currency;
        this.exchangeTimezone = exchangeTimezone;
        this.exchange = exchange;
        this.micCode = micCode;
        this.type = type;
    }
    
    // Getters and Setters
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public String getInterval() {
        return interval;
    }
    
    public void setInterval(String interval) {
        this.interval = interval;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getExchangeTimezone() {
        return exchangeTimezone;
    }
    
    public void setExchangeTimezone(String exchangeTimezone) {
        this.exchangeTimezone = exchangeTimezone;
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
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "TimeSeriesMeta{" +
                "symbol='" + symbol + '\'' +
                ", interval='" + interval + '\'' +
                ", currency='" + currency + '\'' +
                ", exchangeTimezone='" + exchangeTimezone + '\'' +
                ", exchange='" + exchange + '\'' +
                ", micCode='" + micCode + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
} 