package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.endpoints.Price;
import com.github.nicholascowan.twelvedata.models.PriceResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Real API integration tests for Price endpoint. These tests make actual HTTP requests to the
 * TwelveData API.
 */
@Tag("IntegrationTest")
public class PriceIT {
  private static final Logger logger = LoggerFactory.getLogger(PriceIT.class);
  @Test
  void testPriceApi() throws Exception {
    // Create client with demo API key
    TwelveDataClient client = new TwelveDataClient("demo");
    Price endpoint = client.price();
    // Execute request with object response
    PriceResponse response = endpoint.symbol("AAPL").asObject();
    // Verify object response
    assertNotNull(response);
    assertNotNull(response.getPrice());
    // Log object results
    logger.info("✅ Real Price API test successful!");
    logger.info("Symbol: AAPL");
    logger.info("Price: {}", response.getPrice());
    logger.info("Price as Double: {}", response.getPriceAsDouble());
    // Execute request with JSON response
    JsonNode jsonResponse = endpoint.symbol("AAPL").asJson();
    // Verify JSON response
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.has("price"));
    // Log JSON result
    logger.info("✅ Real Price API JSON test successful!");
    logger.info("JSON Response:\n{}", jsonResponse.toPrettyString());
  }
}
