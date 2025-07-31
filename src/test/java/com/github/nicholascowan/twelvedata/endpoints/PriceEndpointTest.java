package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.models.PriceResponse;
import org.junit.jupiter.api.Test;

/**
 * Real API integration tests for Price endpoint. These tests make actual HTTP requests to the
 * TwelveData API.
 */
public class PriceEndpointTest {
  @Test
  void testPriceApi() throws Exception {
    // Create client with demo API key
    TwelveDataClient client = new TwelveDataClient("demo");
    PriceEndpoint endpoint = client.price();
    // Execute request with object response
    PriceResponse response = endpoint.symbol("AAPL").asObject();
    // Verify object response
    assertNotNull(response);
    assertNotNull(response.getPrice());
    // Print object results
    System.out.println("✅ Real Price API test successful!");
    System.out.println("Symbol: AAPL");
    System.out.println("Price: " + response.getPrice());
    System.out.println("Price as Double: " + response.getPriceAsDouble());
    // Execute request with JSON response
    JsonNode jsonResponse = endpoint.symbol("AAPL").asJson();
    // Verify JSON response
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.has("price"));
    // Print JSON result
    System.out.println("\n✅ Real Price API JSON test successful!");
    System.out.println("JSON Response:");
    System.out.println(jsonResponse.toPrettyString());
  }
}
