package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;

/**
 * Endpoint for real-time quotes.
 */
public class QuoteEndpoint extends Endpoint {
    
    public QuoteEndpoint(TwelveDataContext context) {
        super(context);
    }
    
    public QuoteEndpoint(TwelveDataContext context, String symbol) {
        super(context);
        addParam("symbol", symbol);
    }
    
    public QuoteEndpoint(TwelveDataContext context, String symbol, String interval,
                        String exchange, String country, String volumeTimePeriod,
                        String type, Integer dp, String timezone, String prepost,
                        String micCode, String eod, String rollingPeriod) {
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
    
    public QuoteEndpoint prepost(String prepost) {
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
    
    public QuoteEndpoint rollingPeriod(String rollingPeriod) {
        addParam("rolling_period", rollingPeriod);
        return this;
    }
} 