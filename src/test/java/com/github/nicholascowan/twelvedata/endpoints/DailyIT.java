package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for Daily endpoint.
 * 
 * <p>These tests require a valid API key and internet connection to run.
 * They are marked with the "IntegrationTest" tag and should be run separately
 * from unit tests.</p>
 * 
 * <p>Note: These tests use the actual TwelveData API and may consume API credits.</p>
 */
@Tag("IntegrationTest")
class DailyIT {

  private static final String API_KEY = System.getenv("TWELVEDATA_API_KEY");

  @Test
  void testDailyBasicRequest() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    TimeSeriesResponse response = client.daily("AAPL").asObject();

    assertNotNull(response);
    assertNotNull(response.getStatus());
    assertNotNull(response.getMeta());
    assertNotNull(response.getMeta().getSymbol());
    assertNotNull(response.getMeta().getInterval());
    assertEquals("1day", response.getMeta().getInterval());
    assertNotNull(response.getValues());
    assertFalse(response.getValues().isEmpty());
  }

  @Test
  void testDailyWithOutputSize() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    TimeSeriesResponse response = client.daily("MSFT")
        .outputsize(5)
        .asObject();

    assertNotNull(response);
    assertEquals("MSFT", response.getMeta().getSymbol());
    assertEquals("1day", response.getMeta().getInterval());
    assertNotNull(response.getValues());
    assertTrue(response.getValues().size() <= 5);
  }

  @Test
  void testDailyWithDateRange() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    TimeSeriesResponse response = client.daily("GOOGL")
        .startDate("2024-01-01")
        .endDate("2024-01-05")
        .asObject();

    assertNotNull(response);
    assertEquals("GOOGL", response.getMeta().getSymbol());
    assertEquals("1day", response.getMeta().getInterval());
    assertNotNull(response.getValues());
    assertFalse(response.getValues().isEmpty());
  }

  @Test
  void testDailyWithExchange() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    TimeSeriesResponse response = client.daily("TSLA")
        .exchange("NASDAQ")
        .asObject();

    assertNotNull(response);
    assertEquals("TSLA", response.getMeta().getSymbol());
    assertEquals("1day", response.getMeta().getInterval());
    assertEquals("NASDAQ", response.getMeta().getExchange());
  }

  @Test
  void testDailyWithDecimalPlaces() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    TimeSeriesResponse response = client.daily("AMZN")
        .dp(2)
        .asObject();

    assertNotNull(response);
    assertEquals("AMZN", response.getMeta().getSymbol());
    assertEquals("1day", response.getMeta().getInterval());
    assertNotNull(response.getValues());
    assertFalse(response.getValues().isEmpty());
    
    // Verify the close price has at most 2 decimal places
    String close = response.getValues().get(0).getClose();
    if (close.contains(".")) {
      String[] parts = close.split("\\.");
      assertTrue(parts[1].length() <= 2, "Close price should have at most 2 decimal places");
    }
  }
} 