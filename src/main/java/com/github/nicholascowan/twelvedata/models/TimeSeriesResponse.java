package com.github.nicholascowan.twelvedata.models;

import java.util.List;

/**
 * Complete time series response containing meta information and data values.
 */
public class TimeSeriesResponse {
    private String status;
    private TimeSeriesMeta meta;
    private List<TimeSeriesValue> values;
    
    // Default constructor
    public TimeSeriesResponse() {}
    
    // Constructor with all fields
    public TimeSeriesResponse(String status, TimeSeriesMeta meta, List<TimeSeriesValue> values) {
        this.status = status;
        this.meta = meta;
        this.values = values;
    }
    
    // Getters and Setters
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public TimeSeriesMeta getMeta() {
        return meta;
    }
    
    public void setMeta(TimeSeriesMeta meta) {
        this.meta = meta;
    }
    
    public List<TimeSeriesValue> getValues() {
        return values;
    }
    
    public void setValues(List<TimeSeriesValue> values) {
        this.values = values;
    }
    
    // Helper methods
    public boolean isOk() {
        return "ok".equals(status);
    }
    
    public TimeSeriesValue getLatestValue() {
        return values != null && !values.isEmpty() ? values.get(0) : null;
    }
    
    public TimeSeriesValue getOldestValue() {
        return values != null && !values.isEmpty() ? values.get(values.size() - 1) : null;
    }
    
    public int getValueCount() {
        return values != null ? values.size() : 0;
    }
    
    @Override
    public String toString() {
        return "TimeSeriesResponse{" +
                "status='" + status + '\'' +
                ", meta=" + meta +
                ", values=" + (values != null ? values.size() + " items" : "null") +
                '}';
    }
} 