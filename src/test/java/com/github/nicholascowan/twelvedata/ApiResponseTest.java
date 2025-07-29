package com.github.nicholascowan.twelvedata;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeriesEndpoint;
import com.github.nicholascowan.twelvedata.endpoints.QuoteEndpoint;
import com.github.nicholascowan.twelvedata.endpoints.PriceEndpoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for validating API responses contain expected properties.
 */
public class ApiResponseTest {
    
    private TwelveDataClient client;
    
    @BeforeEach
    void setUp() {
        client = new TwelveDataClient("demo");
    }
    
    @Test
    @DisplayName("Time Series API response should contain expected properties")
    void testTimeSeriesResponseProperties() throws Exception {
        // Given
        TimeSeriesEndpoint endpoint = client.timeSeries("AAPL", "1day")
            .outputsize(5)
            .timezone("America/New_York");
        
        // When
        JsonNode response = endpoint.asJson();
        
        // Then
        assertNotNull(response, "Response should not be null");
        assertTrue(response.has("status"), "Response should have 'status' field");
        assertEquals("ok", response.get("status").asText(), "Status should be 'ok'");
        
        // Validate meta object
        assertTrue(response.has("meta"), "Response should have 'meta' object");
        JsonNode meta = response.get("meta");
        
        assertTrue(meta.has("symbol"), "Meta should have 'symbol' field");
        assertEquals("AAPL", meta.get("symbol").asText(), "Symbol should be AAPL");
        
        assertTrue(meta.has("interval"), "Meta should have 'interval' field");
        assertEquals("1day", meta.get("interval").asText(), "Interval should be 1day");
        
        assertTrue(meta.has("currency"), "Meta should have 'currency' field");
        assertEquals("USD", meta.get("currency").asText(), "Currency should be USD");
        
        assertTrue(meta.has("exchange_timezone"), "Meta should have 'exchange_timezone' field");
        assertEquals("America/New_York", meta.get("exchange_timezone").asText(), "Exchange timezone should be America/New_York");
        
        assertTrue(meta.has("exchange"), "Meta should have 'exchange' field");
        assertEquals("NASDAQ", meta.get("exchange").asText(), "Exchange should be NASDAQ");
        
        assertTrue(meta.has("mic_code"), "Meta should have 'mic_code' field");
        assertEquals("XNGS", meta.get("mic_code").asText(), "MIC code should be XNGS");
        
        assertTrue(meta.has("type"), "Meta should have 'type' field");
        assertEquals("Common Stock", meta.get("type").asText(), "Type should be Common Stock");
        
        // Validate values array
        assertTrue(response.has("values"), "Response should have 'values' array");
        JsonNode values = response.get("values");
        assertTrue(values.isArray(), "Values should be an array");
        assertTrue(values.size() > 0, "Values array should not be empty");
        assertTrue(values.size() <= 5, "Values array should have at most 5 elements (outputsize=5)");
        
        // Validate first value object
        JsonNode firstValue = values.get(0);
        assertTrue(firstValue.has("datetime"), "Value should have 'datetime' field");
        assertTrue(firstValue.has("open"), "Value should have 'open' field");
        assertTrue(firstValue.has("high"), "Value should have 'high' field");
        assertTrue(firstValue.has("low"), "Value should have 'low' field");
        assertTrue(firstValue.has("close"), "Value should have 'close' field");
        assertTrue(firstValue.has("volume"), "Value should have 'volume' field");
        
        // Validate data types
        assertTrue(firstValue.get("datetime").isTextual(), "Datetime should be a string");
        assertTrue(firstValue.get("open").isTextual(), "Open should be a string");
        assertTrue(firstValue.get("high").isTextual(), "High should be a string");
        assertTrue(firstValue.get("low").isTextual(), "Low should be a string");
        assertTrue(firstValue.get("close").isTextual(), "Close should be a string");
        assertTrue(firstValue.get("volume").isTextual(), "Volume should be a string");
    }
    
    @Test
    @DisplayName("Quote API response should contain expected properties")
    void testQuoteResponseProperties() throws Exception {
        // Given
        QuoteEndpoint endpoint = client.quote("AAPL");
        
        // When
        JsonNode response = endpoint.asJson();
        
        // Then
        assertNotNull(response, "Response should not be null");
        
        // Validate basic quote properties
        assertTrue(response.has("symbol"), "Response should have 'symbol' field");
        assertEquals("AAPL", response.get("symbol").asText(), "Symbol should be AAPL");
        
        assertTrue(response.has("name"), "Response should have 'name' field");
        assertEquals("Apple Inc.", response.get("name").asText(), "Name should be Apple Inc.");
        
        assertTrue(response.has("exchange"), "Response should have 'exchange' field");
        assertEquals("NASDAQ", response.get("exchange").asText(), "Exchange should be NASDAQ");
        
        assertTrue(response.has("mic_code"), "Response should have 'mic_code' field");
        assertEquals("XNGS", response.get("mic_code").asText(), "MIC code should be XNGS");
        
        assertTrue(response.has("currency"), "Response should have 'currency' field");
        assertEquals("USD", response.get("currency").asText(), "Currency should be USD");
        
        assertTrue(response.has("datetime"), "Response should have 'datetime' field");
        assertTrue(response.has("timestamp"), "Response should have 'timestamp' field");
        assertTrue(response.has("last_quote_at"), "Response should have 'last_quote_at' field");
        
        // Validate price data
        assertTrue(response.has("open"), "Response should have 'open' field");
        assertTrue(response.has("high"), "Response should have 'high' field");
        assertTrue(response.has("low"), "Response should have 'low' field");
        assertTrue(response.has("close"), "Response should have 'close' field");
        assertTrue(response.has("volume"), "Response should have 'volume' field");
        assertTrue(response.has("previous_close"), "Response should have 'previous_close' field");
        assertTrue(response.has("change"), "Response should have 'change' field");
        assertTrue(response.has("percent_change"), "Response should have 'percent_change' field");
        assertTrue(response.has("average_volume"), "Response should have 'average_volume' field");
        assertTrue(response.has("is_market_open"), "Response should have 'is_market_open' field");
        
        // Validate 52-week data
        assertTrue(response.has("fifty_two_week"), "Response should have 'fifty_two_week' object");
        JsonNode fiftyTwoWeek = response.get("fifty_two_week");
        
        assertTrue(fiftyTwoWeek.has("low"), "52-week should have 'low' field");
        assertTrue(fiftyTwoWeek.has("high"), "52-week should have 'high' field");
        assertTrue(fiftyTwoWeek.has("low_change"), "52-week should have 'low_change' field");
        assertTrue(fiftyTwoWeek.has("high_change"), "52-week should have 'high_change' field");
        assertTrue(fiftyTwoWeek.has("low_change_percent"), "52-week should have 'low_change_percent' field");
        assertTrue(fiftyTwoWeek.has("high_change_percent"), "52-week should have 'high_change_percent' field");
        assertTrue(fiftyTwoWeek.has("range"), "52-week should have 'range' field");
        
        // Validate data types
        assertTrue(response.get("symbol").isTextual(), "Symbol should be a string");
        assertTrue(response.get("name").isTextual(), "Name should be a string");
        assertTrue(response.get("exchange").isTextual(), "Exchange should be a string");
        assertTrue(response.get("currency").isTextual(), "Currency should be a string");
        assertTrue(response.get("open").isTextual(), "Open should be a string");
        assertTrue(response.get("close").isTextual(), "Close should be a string");
        assertTrue(response.get("volume").isTextual(), "Volume should be a string");
        assertTrue(response.get("is_market_open").isBoolean(), "is_market_open should be a boolean");
    }
    
    @Test
    @DisplayName("Price API response should contain expected properties")
    void testPriceResponseProperties() throws Exception {
        // Given
        PriceEndpoint endpoint = client.price("AAPL");
        
        // When
        JsonNode response = endpoint.asJson();
        
        // Then
        assertNotNull(response, "Response should not be null");
        
        // Validate price response structure
        assertTrue(response.has("price"), "Response should have 'price' field");
        assertTrue(response.get("price").isTextual(), "Price should be a string");
        
        // Validate price format (should be a decimal number)
        String price = response.get("price").asText();
        assertTrue(price.matches("\\d+\\.\\d+"), "Price should be in decimal format");
        
        // Validate price is reasonable (between $1 and $10000 for AAPL)
        double priceValue = Double.parseDouble(price);
        assertTrue(priceValue > 1.0, "Price should be greater than $1");
        assertTrue(priceValue < 10000.0, "Price should be less than $10000");
    }
    
    @Test
    @DisplayName("Time Series CSV response should contain expected format")
    void testTimeSeriesCsvResponse() throws Exception {
        // Given
        TimeSeriesEndpoint endpoint = client.timeSeries("AAPL", "1day")
            .outputsize(3)
            .timezone("America/New_York");
        
        // When
        String csvResponse = endpoint.asCsv();
        
        // Then
        assertNotNull(csvResponse, "CSV response should not be null");
        assertFalse(csvResponse.isEmpty(), "CSV response should not be empty");
        
        // Split into lines
        String[] lines = csvResponse.split("\n");
        assertTrue(lines.length > 1, "CSV should have at least header and one data row");
        
        // Validate header
        String header = lines[0];
        assertTrue(header.contains("datetime"), "Header should contain 'datetime'");
        assertTrue(header.contains("open"), "Header should contain 'open'");
        assertTrue(header.contains("high"), "Header should contain 'high'");
        assertTrue(header.contains("low"), "Header should contain 'low'");
        assertTrue(header.contains("close"), "Header should contain 'close'");
        assertTrue(header.contains("volume"), "Header should contain 'volume'");
        
        // Validate data rows
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (!line.isEmpty()) {
                String[] fields = line.split(";");
                assertEquals(6, fields.length, "Each data row should have 6 fields");
                
                // Validate datetime format (YYYY-MM-DD or YYYY-MM-DD HH:MM:SS)
                assertTrue(fields[0].matches("\\d{4}-\\d{2}-\\d{2}( \\d{2}:\\d{2}:\\d{2})?"), 
                    "Datetime should be in YYYY-MM-DD or YYYY-MM-DD HH:MM:SS format");
                
                // Validate numeric fields
                for (int j = 1; j < 5; j++) {
                    assertTrue(fields[j].matches("\\d+\\.\\d+"), 
                        "Price field " + j + " should be a decimal number");
                }
                
                // Validate volume (integer)
                assertTrue(fields[5].matches("\\d+"), "Volume should be an integer");
            }
        }
    }
    
    @Test
    @DisplayName("Generated URL should contain expected parameters")
    void testGeneratedUrl() {
        // Given
        TimeSeriesEndpoint endpoint = client.timeSeries("AAPL", "1day")
            .outputsize(5)
            .timezone("America/New_York");
        
        // When
        String url = endpoint.asUrl();
        
        // Then
        assertNotNull(url, "URL should not be null");
        assertTrue(url.startsWith("https://api.twelvedata.com/time_series"), 
            "URL should start with the correct endpoint");
        
        // Validate required parameters
        assertTrue(url.contains("symbol=AAPL"), "URL should contain symbol parameter");
        assertTrue(url.contains("interval=1day"), "URL should contain interval parameter");
        assertTrue(url.contains("outputsize=5"), "URL should contain outputsize parameter");
        assertTrue(url.contains("timezone=America/New_York"), "URL should contain timezone parameter");
        assertTrue(url.contains("apikey=demo"), "URL should contain API key");
        
        // Validate default parameters
        assertTrue(url.contains("prepost=false"), "URL should contain default prepost parameter");
        assertTrue(url.contains("dp=5"), "URL should contain default dp parameter");
        assertTrue(url.contains("order=desc"), "URL should contain default order parameter");
    }
    
    @Test
    @DisplayName("Time Series with different intervals should work")
    void testTimeSeriesDifferentIntervals() throws Exception {
        String[] intervals = {"1min", "5min", "15min", "30min", "45min", "1h", "2h", "4h", "1day", "1week", "1month"};
        
        for (String interval : intervals) {
            // Given
            TimeSeriesEndpoint endpoint = client.timeSeries("AAPL", interval)
                .outputsize(1);
            
            // When
            JsonNode response = endpoint.asJson();
            
            // Then
            assertNotNull(response, "Response should not be null for interval: " + interval);
            assertTrue(response.has("status"), "Response should have status for interval: " + interval);
            assertEquals("ok", response.get("status").asText(), "Status should be ok for interval: " + interval);
            
            if (response.has("meta")) {
                JsonNode meta = response.get("meta");
                assertEquals(interval, meta.get("interval").asText(), 
                    "Interval should match for: " + interval);
            }
        }
    }
    
    @Test
    @DisplayName("API should work with different symbols")
    void testDifferentSymbols() throws Exception {
        // Use only AAPL for demo API key to avoid rate limiting
        String symbol = "AAPL";
        
        // Test time series
        TimeSeriesEndpoint tsEndpoint = client.timeSeries(symbol, "1day").outputsize(1);
        JsonNode tsResponse = tsEndpoint.asJson();
        assertNotNull(tsResponse, "Time series response should not be null for: " + symbol);
        assertTrue(tsResponse.has("status"), "Time series should have status for: " + symbol);
        assertEquals("ok", tsResponse.get("status").asText(), "Time series status should be ok for: " + symbol);
        
        if (tsResponse.has("meta")) {
            JsonNode meta = tsResponse.get("meta");
            assertEquals(symbol, meta.get("symbol").asText(), "Symbol should match for: " + symbol);
        }
        
        // Test quote
        QuoteEndpoint quoteEndpoint = client.quote(symbol);
        JsonNode quoteResponse = quoteEndpoint.asJson();
        assertNotNull(quoteResponse, "Quote response should not be null for: " + symbol);
        assertTrue(quoteResponse.has("symbol"), "Quote should have symbol for: " + symbol);
        assertEquals(symbol, quoteResponse.get("symbol").asText(), "Quote symbol should match for: " + symbol);
        
        // Test price
        PriceEndpoint priceEndpoint = client.price(symbol);
        JsonNode priceResponse = priceEndpoint.asJson();
        assertNotNull(priceResponse, "Price response should not be null for: " + symbol);
        assertTrue(priceResponse.has("price"), "Price should have price field for: " + symbol);
        assertTrue(priceResponse.get("price").isTextual(), "Price should be textual for: " + symbol);
    }
} 