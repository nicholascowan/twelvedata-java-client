package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeriesEndpoint;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Real API integration tests for TimeSeries endpoint. These tests make actual HTTP requests to the
 * TwelveData API.
 */
@Tag("IntegrationTest")
public class TimeSeriesEndpointIT {
  @Test
  void testTimeSeriesApi() throws Exception {
    // Create client with demo API key
    TwelveDataClient client = new TwelveDataClient("demo");
    TimeSeriesEndpoint endpoint = client.timeSeries();
    // Execute request with object response
    TimeSeriesResponse response = endpoint.symbol("AAPL").interval("1day").asObject();
    // Verify object response
    assertNotNull(response);
    assertNotNull(response.getMeta());
    assertNotNull(response.getValues());
    assertTrue(response.getValues().size() > 0);
    // Print object results
    System.out.println("✅ Real TimeSeries API test successful!");
    System.out.println("Symbol: " + response.getMeta().getSymbol());
    System.out.println("Interval: " + response.getMeta().getInterval());
    System.out.println("Exchange: " + response.getMeta().getExchange());
    System.out.println("Currency: " + response.getMeta().getCurrency());
    System.out.println("Data Points: " + response.getValues().size());
    System.out.println("Latest Close: " + response.getValues().get(0).getClose());
    System.out.println("Latest Volume: " + response.getValues().get(0).getVolume());
    // Execute request with JSON response
    JsonNode jsonResponse = endpoint.symbol("AAPL").interval("1day").asJson();
    // Verify JSON response
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.has("meta"));
    assertTrue(jsonResponse.has("values"));
    // Print JSON result
    System.out.println("\n✅ Real TimeSeries API JSON test successful!");
    System.out.println("JSON Response:");
    System.out.println(jsonResponse.toPrettyString());
  }
}
