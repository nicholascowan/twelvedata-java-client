package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.models.EndOfDayResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for EndOfDay endpoint.
 * 
 * <p>These tests require a valid API key and internet connection to run.
 * They are marked with the "IntegrationTest" tag and should be run separately
 * from unit tests.</p>
 * 
 * <p>Note: These tests use the actual TwelveData API and may consume API credits.</p>
 */
@Tag("IntegrationTest")
class EndOfDayIT {

  private static final String API_KEY = System.getenv("TWELVEDATA_API_KEY");

  @Test
  void testEndOfDayBasicRequest() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    EndOfDayResponse response = client.endOfDay("AAPL").asObject();

    assertNotNull(response);
    assertNotNull(response.getSymbol());
    assertNotNull(response.getExchange());
    assertNotNull(response.getClose());
    assertNotNull(response.getDatetime());
    assertNotNull(response.getCurrency());
  }

  @Test
  void testEndOfDayWithSpecificDate() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    EndOfDayResponse response = client.endOfDay("AAPL")
        .date("2024-01-15")
        .asObject();

    assertNotNull(response);
    assertEquals("AAPL", response.getSymbol());
    assertEquals("2024-01-15", response.getDatetime());
    assertNotNull(response.getClose());
  }

  @Test
  void testEndOfDayWithExchange() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    EndOfDayResponse response = client.endOfDay("MSFT")
        .exchange("NASDAQ")
        .asObject();

    assertNotNull(response);
    assertEquals("MSFT", response.getSymbol());
    assertEquals("NASDAQ", response.getExchange());
    assertNotNull(response.getClose());
  }

  @Test
  void testEndOfDayWithDecimalPlaces() throws Exception {
    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("Skipping integration test - no API key provided");
      return;
    }

    TwelveDataClient client = new TwelveDataClient(API_KEY);
    EndOfDayResponse response = client.endOfDay("GOOGL")
        .dp(2)
        .asObject();

    assertNotNull(response);
    assertEquals("GOOGL", response.getSymbol());
    assertNotNull(response.getClose());
    // Verify the close price has at most 2 decimal places
    String close = response.getClose();
    if (close.contains(".")) {
      String[] parts = close.split("\\.");
      assertTrue(parts[1].length() <= 2, "Close price should have at most 2 decimal places");
    }
  }
} 