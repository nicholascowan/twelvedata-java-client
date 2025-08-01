package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeriesEndpoint;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Real API integration tests for TimeSeries endpoint. These tests make actual HTTP requests to the
 * TwelveData API.
 */
@Tag("IntegrationTest")
public class TimeSeriesEndpointIT {
  private static final Logger logger = LoggerFactory.getLogger(TimeSeriesEndpointIT.class);
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
    // Log object results
    logger.info("✅ Real TimeSeries API test successful!");
    logger.info("Symbol: {}", response.getMeta().getSymbol());
    logger.info("Interval: {}", response.getMeta().getInterval());
    logger.info("Exchange: {}", response.getMeta().getExchange());
    logger.info("Currency: {}", response.getMeta().getCurrency());
    logger.info("Data Points: {}", response.getValues().size());
    logger.info("Latest Close: {}", response.getValues().get(0).getClose());
    logger.info("Latest Volume: {}", response.getValues().get(0).getVolume());
    // Execute request with JSON response
    JsonNode jsonResponse = endpoint.symbol("AAPL").interval("1day").asJson();
    // Verify JSON response
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.has("meta"));
    assertTrue(jsonResponse.has("values"));
    // Log JSON result
    logger.info("✅ Real TimeSeries API JSON test successful!");
    logger.info("JSON Response:\n{}", jsonResponse.toPrettyString());
  }
}
