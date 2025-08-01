package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.endpoints.QuoteEndpoint;
import com.github.nicholascowan.twelvedata.models.QuoteResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Real API integration tests for Quote endpoint. These tests make actual HTTP requests to the
 * TwelveData API.
 */
@Tag("IntegrationTest")
public class QuoteEndpointIT {
  private static final Logger logger = LoggerFactory.getLogger(QuoteEndpointIT.class);
  @Test
  void testQuoteApi() throws Exception {
    // Create client with demo API key
    TwelveDataClient client = new TwelveDataClient("demo");
    QuoteEndpoint endpoint = client.quote();
    // Execute request with object response
    QuoteResponse response = endpoint.symbol("AAPL").asObject();
    // Verify object response
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    assertNotNull(response.getName());
    assertNotNull(response.getExchange());
    assertNotNull(response.getCurrency());
    assertNotNull(response.getClose());
    // Log object results
    logger.info("✅ Real Quote API test successful!");
    logger.info("Symbol: {}", response.getSymbol());
    logger.info("Name: {}", response.getName());
    logger.info("Exchange: {}", response.getExchange());
    logger.info("Currency: {}", response.getCurrency());
    logger.info("Close Price: {}", response.getClose());
    logger.info("Volume: {}", response.getVolume());
    logger.info("52-Week Range: {} - {}", 
        response.getFiftyTwoWeek().getLow(), 
        response.getFiftyTwoWeek().getHigh());
    // Execute request with JSON response
    JsonNode jsonResponse = endpoint.symbol("AAPL").asJson();
    // Verify JSON response
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.has("symbol"));
    assertTrue(jsonResponse.has("name"));
    assertTrue(jsonResponse.has("exchange"));
    // Log JSON result
    logger.info("✅ Real Quote API JSON test successful!");
    logger.info("JSON Response:\n{}", jsonResponse.toPrettyString());
  }
}
