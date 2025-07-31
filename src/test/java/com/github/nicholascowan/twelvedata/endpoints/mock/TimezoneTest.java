package com.github.nicholascowan.twelvedata.endpoints.mock;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.config.TwelveDataConfig;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Test class for timezone functionality with ZoneId support. */
class TimezoneTest {

  private TwelveDataClient client;

  @BeforeEach
  void setUp() {
    client = new TwelveDataClient("test-key");
  }

  @Test
  @DisplayName("TimeSeries endpoint should support ZoneId timezone")
  void testTimeSeriesWithZoneId() {
    // Test with different ZoneId values
    ZoneId[] zoneIds = {
      ZoneId.of("America/New_York"),
      ZoneId.of("Europe/London"),
      ZoneId.of("Asia/Tokyo"),
      ZoneId.of("UTC")
    };

    for (ZoneId zoneId : zoneIds) {
      // Test that the overloaded method compiles and returns the endpoint
      var endpoint = client.timeSeries("AAPL", "1day").timezone(zoneId);
      
      // Verify the endpoint is not null
      assertNotNull(endpoint, "Endpoint should not be null for ZoneId: " + zoneId);
      
      // Verify we can chain additional methods
      var chainedEndpoint = endpoint.outputsize(5);
      assertNotNull(chainedEndpoint, "Chained endpoint should not be null for ZoneId: " + zoneId);
    }
  }

  @Test
  @DisplayName("Quote endpoint should support ZoneId timezone")
  void testQuoteWithZoneId() {
    // Test with different ZoneId values
    ZoneId[] zoneIds = {
      ZoneId.of("America/New_York"),
      ZoneId.of("Europe/London"),
      ZoneId.of("Asia/Tokyo"),
      ZoneId.of("UTC")
    };

    for (ZoneId zoneId : zoneIds) {
      // Test that the overloaded method compiles and returns the endpoint
      var endpoint = client.quote("AAPL").timezone(zoneId);
      
      // Verify the endpoint is not null
      assertNotNull(endpoint, "Endpoint should not be null for ZoneId: " + zoneId);
      
      // Verify we can chain additional methods
      var chainedEndpoint = endpoint.dp(2);
      assertNotNull(chainedEndpoint, "Chained endpoint should not be null for ZoneId: " + zoneId);
    }
  }

  @Test
  @DisplayName("String and ZoneId timezone methods should both work")
  void testStringAndZoneIdMethods() {
    String timezoneString = "America/New_York";
    ZoneId zoneId = ZoneId.of(timezoneString);
    
    // Test TimeSeries endpoint - both methods should work
    var timeSeriesString = client.timeSeries("AAPL", "1day").timezone(timezoneString);
    var timeSeriesZoneId = client.timeSeries("AAPL", "1day").timezone(zoneId);
    
    assertNotNull(timeSeriesString, "String timezone method should work");
    assertNotNull(timeSeriesZoneId, "ZoneId timezone method should work");
    
    // Test Quote endpoint - both methods should work
    var quoteString = client.quote("AAPL").timezone(timezoneString);
    var quoteZoneId = client.quote("AAPL").timezone(zoneId);
    
    assertNotNull(quoteString, "String timezone method should work");
    assertNotNull(quoteZoneId, "ZoneId timezone method should work");
  }

  @Test
  @DisplayName("System default ZoneId should work")
  void testSystemDefaultZoneId() {
    ZoneId systemDefault = ZoneId.systemDefault();
    
    // Test TimeSeries endpoint
    var timeSeriesEndpoint = client.timeSeries("AAPL", "1day").timezone(systemDefault);
    assertNotNull(timeSeriesEndpoint, "System default ZoneId should work for TimeSeries");
    
    // Test Quote endpoint
    var quoteEndpoint = client.quote("AAPL").timezone(systemDefault);
    assertNotNull(quoteEndpoint, "System default ZoneId should work for Quote");
  }

  @Test
  @DisplayName("Common timezone ZoneIds should work")
  void testCommonTimezoneZoneIds() {
    // Test common timezone ZoneIds
    ZoneId[] commonZoneIds = {
      ZoneId.of("America/New_York"),
      ZoneId.of("America/Los_Angeles"),
      ZoneId.of("Europe/London"),
      ZoneId.of("Europe/Paris"),
      ZoneId.of("Asia/Tokyo"),
      ZoneId.of("Asia/Shanghai"),
      ZoneId.of("Australia/Sydney"),
      ZoneId.of("UTC"),
      ZoneId.of("GMT")
    };

    for (ZoneId zoneId : commonZoneIds) {
      // Test TimeSeries
      var timeSeriesEndpoint = client.timeSeries("AAPL", "1day").timezone(zoneId);
      assertNotNull(timeSeriesEndpoint, "TimeSeries should work with ZoneId: " + zoneId);
      
      // Test Quote
      var quoteEndpoint = client.quote("AAPL").timezone(zoneId);
      assertNotNull(quoteEndpoint, "Quote should work with ZoneId: " + zoneId);
    }
  }
} 