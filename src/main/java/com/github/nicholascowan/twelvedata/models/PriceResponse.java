package com.github.nicholascowan.twelvedata.models;

/**
 * Price response for a stock.
 */
public class PriceResponse {
    private String price;
    
    // Default constructor
    public PriceResponse() {}
    
    // Constructor with price
    public PriceResponse(String price) {
        this.price = price;
    }
    
    // Getters and Setters
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    // Helper methods to get numeric values
    public Double getPriceAsDouble() {
        return price != null ? Double.parseDouble(price) : null;
    }
    
    public Float getPriceAsFloat() {
        return price != null ? Float.parseFloat(price) : null;
    }
    
    @Override
    public String toString() {
        return "PriceResponse{" +
                "price='" + price + '\'' +
                '}';
    }
} 