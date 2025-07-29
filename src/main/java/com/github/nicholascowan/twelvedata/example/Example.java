package com.github.nicholascowan.twelvedata.example;

import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeriesEndpoint;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Example usage of the TwelveData Java client.
 */
public class Example {
    
    public static void main(String[] args) {
        try {
            // Get API key from command line arguments or use demo
            String apiKey = args.length > 0 ? args[0] : "demo";
            System.out.println("Using API key: " + (apiKey.equals("demo") ? "demo (free tier)" : apiKey.substring(0, 4) + "..."));
            
            // Initialize client with API key
            TwelveDataClient client = new TwelveDataClient(apiKey);
            
            System.out.println("=== TwelveData Java Client Example ===\n");
            
            // Example 1: Get time series data
            System.out.println("1. Getting time series data for AAPL:");
            TimeSeriesEndpoint ts = client.timeSeries("AAPL", "1day")
                .outputsize(5)
                .timezone("America/New_York");
            
            JsonNode data = ts.asJson();
            System.out.println("JSON Response:");
            System.out.println(data.toPrettyString());
            System.out.println();
            
            // Example 2: Get CSV data
            System.out.println("2. Getting CSV data for AAPL:");
            String csvData = ts.asCsv();
            System.out.println("CSV Response:");
            System.out.println(csvData);
            System.out.println();
            
            // Example 3: Get URL
            System.out.println("3. Generated URL:");
            String url = ts.asUrl();
            System.out.println(url);
            System.out.println();
            
            // Example 4: Get quote data
            System.out.println("4. Getting quote data for AAPL:");
            JsonNode quoteData = client.quote("AAPL").asJson();
            System.out.println("Quote Response:");
            System.out.println(quoteData.toPrettyString());
            System.out.println();
            
            // Example 5: Get price data
            System.out.println("5. Getting price data for AAPL:");
            JsonNode priceData = client.price("AAPL").asJson();
            System.out.println("Price Response:");
            System.out.println(priceData.toPrettyString());
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 