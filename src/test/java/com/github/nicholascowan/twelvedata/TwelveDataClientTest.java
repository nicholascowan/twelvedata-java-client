package com.github.nicholascowan.twelvedata;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.nicholascowan.twelvedata.endpoints.Price;
import com.github.nicholascowan.twelvedata.endpoints.Quote;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Basic tests for the TwelveData client. */
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
  void testTimeSeries() {
    TimeSeries endpoint = client.timeSeries();
    assertNotNull(endpoint);
    assertTrue(endpoint.isPrice());
  }

  @Test
  @DisplayName("Time Series with symbol and interval should work")
  void testTimeSeriesWithSymbolAndInterval() {
    TimeSeries endpoint = client.timeSeries("AAPL", "1day");
    assertNotNull(endpoint);
    assertTrue(endpoint.isPrice());
  }

  @Test
  @DisplayName("Quote endpoint should be created correctly")
  void testQuote() {
    Quote endpoint = client.quote();
    assertNotNull(endpoint);
  }

  @Test
  @DisplayName("Quote with symbol should work")
  void testQuoteWithSymbol() {
    Quote endpoint = client.quote("AAPL");
    assertNotNull(endpoint);
  }

  @Test
  @DisplayName("Price endpoint should be created correctly")
  void testPrice() {
    Price endpoint = client.price();
    assertNotNull(endpoint);
  }

  @Test
  @DisplayName("Price with symbol should work")
  void testPriceWithSymbol() {
    Price endpoint = client.price("AAPL");
    assertNotNull(endpoint);
  }

  @Test
  @DisplayName("Basic API calls should return valid responses")
  void testBasicApiCalls() throws Exception {
    // Test time series
    TimeSeries tsEndpoint = client.timeSeries("AAPL", "1day").outputsize(1);
    JsonNode tsResponse = tsEndpoint.asJson();
    assertNotNull(tsResponse);
    assertTrue(tsResponse.has("status"));
    assertEquals("ok", tsResponse.get("status").asText());

    // Test quote
    Quote quote = client.quote("AAPL");
    JsonNode quoteResponse = quote.asJson();
    assertNotNull(quoteResponse);
    assertTrue(quoteResponse.has("symbol"));
    assertEquals("AAPL", quoteResponse.get("symbol").asText());

    // Test price
    Price price = client.price("AAPL");
    JsonNode priceResponse = price.asJson();
    assertNotNull(priceResponse);
    assertTrue(priceResponse.has("price"));
    assertTrue(priceResponse.get("price").isTextual());
  }
}
