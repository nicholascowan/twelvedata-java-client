package com.github.nicholascowan.twelvedata;

import com.github.nicholascowan.twelvedata.endpoints.TimeSeriesEndpoint;
import com.github.nicholascowan.twelvedata.endpoints.QuoteEndpoint;
import com.github.nicholascowan.twelvedata.endpoints.PriceEndpoint;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic tests for the TwelveData client.
 */
public class TwelveDataClientTest {
    
    private TwelveDataClient client;
    
    @BeforeEach
    void setUp() {
        client = new TwelveDataClient("demo");
    }
    
    @Test
    @DisplayName("Client creation should work correctly")
    void testClientCreation() {
        assertNotNull(client);
        assertNotNull(client.getContext());
        assertEquals("demo", client.getContext().getApiKey());
    }
    
    @Test
    @DisplayName("Time Series endpoint should be created correctly")
    void testTimeSeriesEndpoint() {
        TimeSeriesEndpoint endpoint = client.timeSeries();
        assertNotNull(endpoint);
        assertTrue(endpoint.isPrice());
    }
    
    @Test
    @DisplayName("Time Series with symbol and interval should work")
    void testTimeSeriesWithSymbolAndInterval() {
        TimeSeriesEndpoint endpoint = client.timeSeries("AAPL", "1day");
        assertNotNull(endpoint);
        assertTrue(endpoint.isPrice());
    }
    
    @Test
    @DisplayName("Quote endpoint should be created correctly")
    void testQuoteEndpoint() {
        QuoteEndpoint endpoint = client.quote();
        assertNotNull(endpoint);
    }
    
    @Test
    @DisplayName("Quote with symbol should work")
    void testQuoteWithSymbol() {
        QuoteEndpoint endpoint = client.quote("AAPL");
        assertNotNull(endpoint);
    }
    
    @Test
    @DisplayName("Price endpoint should be created correctly")
    void testPriceEndpoint() {
        PriceEndpoint endpoint = client.price();
        assertNotNull(endpoint);
    }
    
    @Test
    @DisplayName("Price with symbol should work")
    void testPriceWithSymbol() {
        PriceEndpoint endpoint = client.price("AAPL");
        assertNotNull(endpoint);
    }
    
    @Test
    @DisplayName("Basic API calls should return valid responses")
    void testBasicApiCalls() throws Exception {
        // Test time series
        TimeSeriesEndpoint tsEndpoint = client.timeSeries("AAPL", "1day").outputsize(1);
        JsonNode tsResponse = tsEndpoint.asJson();
        assertNotNull(tsResponse);
        assertTrue(tsResponse.has("status"));
        assertEquals("ok", tsResponse.get("status").asText());
        
        // Test quote
        QuoteEndpoint quoteEndpoint = client.quote("AAPL");
        JsonNode quoteResponse = quoteEndpoint.asJson();
        assertNotNull(quoteResponse);
        assertTrue(quoteResponse.has("symbol"));
        assertEquals("AAPL", quoteResponse.get("symbol").asText());
        
        // Test price
        PriceEndpoint priceEndpoint = client.price("AAPL");
        JsonNode priceResponse = priceEndpoint.asJson();
        assertNotNull(priceResponse);
        assertTrue(priceResponse.has("price"));
        assertTrue(priceResponse.get("price").isTextual());
    }
} 