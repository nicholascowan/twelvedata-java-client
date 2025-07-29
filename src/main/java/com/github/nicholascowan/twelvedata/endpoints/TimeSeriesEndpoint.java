package com.github.nicholascowan.twelvedata.endpoints;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;
import com.github.nicholascowan.twelvedata.models.ModelUtils;

/**
 * Endpoint for time series data (OHLC).
 */
public class TimeSeriesEndpoint extends Endpoint {
    
    public TimeSeriesEndpoint(TwelveDataContext context) {
        super(context);
        this.isPrice = true;
    }
    
    public TimeSeriesEndpoint(TwelveDataContext context, String symbol, String interval) {
        super(context);
        this.isPrice = true;
        addParam("symbol", symbol);
        addParam("interval", interval);
    }
    
    public TimeSeriesEndpoint(TwelveDataContext context, String symbol, String interval, 
                             String exchange, String country, String type, Integer outputsize,
                             String startDate, String endDate, Integer dp, String timezone,
                             String order, String prepost, String date, String micCode,
                             String previousClose, String adjust) {
        super(context);
        this.isPrice = true;
        addParam("symbol", symbol);
        addParam("interval", interval);
        addParam("exchange", exchange);
        addParam("country", country);
        addParam("type", type);
        addParam("outputsize", outputsize);
        addParam("start_date", startDate);
        addParam("end_date", endDate);
        addParam("dp", dp);
        addParam("timezone", timezone);
        addParam("order", order);
        addParam("prepost", prepost);
        addParam("date", date);
        addParam("mic_code", micCode);
        addParam("previous_close", previousClose);
        addParam("adjust", adjust);
    }
    
    @Override
    protected String getEndpointName() {
        return "time_series";
    }
    
    // Builder methods for fluent API
    public TimeSeriesEndpoint symbol(String symbol) {
        addParam("symbol", symbol);
        return this;
    }
    
    public TimeSeriesEndpoint interval(String interval) {
        addParam("interval", interval);
        return this;
    }
    
    public TimeSeriesEndpoint exchange(String exchange) {
        addParam("exchange", exchange);
        return this;
    }
    
    public TimeSeriesEndpoint country(String country) {
        addParam("country", country);
        return this;
    }
    
    public TimeSeriesEndpoint type(String type) {
        addParam("type", type);
        return this;
    }
    
    public TimeSeriesEndpoint outputsize(Integer outputsize) {
        addParam("outputsize", outputsize);
        return this;
    }
    
    public TimeSeriesEndpoint startDate(String startDate) {
        addParam("start_date", startDate);
        return this;
    }
    
    public TimeSeriesEndpoint endDate(String endDate) {
        addParam("end_date", endDate);
        return this;
    }
    
    public TimeSeriesEndpoint dp(Integer dp) {
        addParam("dp", dp);
        return this;
    }
    
    public TimeSeriesEndpoint timezone(String timezone) {
        addParam("timezone", timezone);
        return this;
    }
    
    public TimeSeriesEndpoint order(String order) {
        addParam("order", order);
        return this;
    }
    
    public TimeSeriesEndpoint prepost(String prepost) {
        addParam("prepost", prepost);
        return this;
    }
    
    public TimeSeriesEndpoint date(String date) {
        addParam("date", date);
        return this;
    }
    
    public TimeSeriesEndpoint micCode(String micCode) {
        addParam("mic_code", micCode);
        return this;
    }
    
    public TimeSeriesEndpoint previousClose(String previousClose) {
        addParam("previous_close", previousClose);
        return this;
    }
    
    public TimeSeriesEndpoint adjust(String adjust) {
        addParam("adjust", adjust);
        return this;
    }
    
    /**
     * Get time series data as a typed model object.
     * 
     * @return TimeSeriesResponse object
     * @throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException if the request fails
     */
    public TimeSeriesResponse asModel() throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException {
        return ModelUtils.toTimeSeriesResponse(asJson());
    }
} 