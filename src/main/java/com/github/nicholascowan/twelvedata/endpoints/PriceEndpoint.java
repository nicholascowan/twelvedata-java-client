package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;

/**
 * Endpoint for real-time prices.
 */
public class PriceEndpoint extends Endpoint {
    
    public PriceEndpoint(TwelveDataContext context) {
        super(context);
    }
    
    public PriceEndpoint(TwelveDataContext context, String symbol) {
        super(context);
        addParam("symbol", symbol);
    }
    
    public PriceEndpoint(TwelveDataContext context, String symbol, String exchange,
                        String country, String type, Integer dp, String prepost, String micCode) {
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
    
    public PriceEndpoint micCode(String micCode) {
        addParam("mic_code", micCode);
        return this;
    }
} 