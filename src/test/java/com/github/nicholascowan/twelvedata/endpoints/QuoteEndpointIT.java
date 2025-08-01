package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.endpoints.QuoteEndpoint;
import com.github.nicholascowan.twelvedata.models.QuoteResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Real API integration tests for Quote endpoint. These tests make actual HTTP requests to the
 * TwelveData API.
 */
@Tag("IntegrationTest")
public class QuoteEndpointIT {
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
    // Print object results
    System.out.println("✅ Real Quote API test successful!");
    System.out.println("Symbol: " + response.getSymbol());
    System.out.println("Name: " + response.getName());
    System.out.println("Exchange: " + response.getExchange());
    System.out.println("Currency: " + response.getCurrency());
    System.out.println("Close Price: " + response.getClose());
    System.out.println("Volume: " + response.getVolume());
    System.out.println(
        "52-Week Range: "
            + response.getFiftyTwoWeek().getLow()
            + " - "
            + response.getFiftyTwoWeek().getHigh());
    // Execute request with JSON response
    JsonNode jsonResponse = endpoint.symbol("AAPL").asJson();
    // Verify JSON response
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.has("symbol"));
    assertTrue(jsonResponse.has("name"));
    assertTrue(jsonResponse.has("exchange"));
    // Print JSON result
    System.out.println("\n✅ Real Quote API JSON test successful!");
    System.out.println("JSON Response:");
    System.out.println(jsonResponse.toPrettyString());
  }
}
