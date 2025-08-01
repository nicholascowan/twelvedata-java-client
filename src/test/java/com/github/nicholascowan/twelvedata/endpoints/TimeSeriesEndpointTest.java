package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.nicholascowan.twelvedata.TestUtils;
import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeriesEndpoint;
import com.github.nicholascowan.twelvedata.http.DefaultHttpClient;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;
import java.util.HashMap;
import java.util.Map;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive mock tests for TimeSeries endpoint functionality. These tests use MockWebServer to
 * avoid hitting the actual API.
 */
@Tag("UnitTest")
class TimeSeriesEndpointTest {
  private MockWebServer mockWebServer;
  private TimeSeriesEndpoint endpoint;

  @BeforeEach
  void setUp() throws Exception {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    // Create client with mock server URL
    Map<String, String> defaults = new HashMap<>();
    String baseUrl = "http://localhost:" + mockWebServer.getPort();
    DefaultHttpClient httpClient = new DefaultHttpClient(baseUrl, 30000);
    TwelveDataContext context =
        new TwelveDataContext("test-api-key", baseUrl, httpClient, defaults);
    endpoint = new TimeSeriesEndpoint(context);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  void testTimeSeriesEndpointCreation() {
    assertNotNull(endpoint);
    assertTrue(endpoint.isPrice());
    assertFalse(endpoint.isIndicator());
    assertFalse(endpoint.isOverlay());
    assertFalse(endpoint.isBatch());
  }

  @Test
  void testBasicTimeSeriesRequest() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    TimeSeriesResponse response = endpoint.symbol("AAPL").interval("1min").asObject();
    // Verify response
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    assertNotNull(response.getMeta());
    assertEquals("AAPL", response.getMeta().getSymbol());
    assertEquals("1min", response.getMeta().getInterval());
    assertEquals("USD", response.getMeta().getCurrency());
    assertEquals("America/New_York", response.getMeta().getExchangeTimezone());
    assertEquals("NASDAQ", response.getMeta().getExchange());
    assertEquals("XNAS", response.getMeta().getMicCode());
    assertEquals("Common Stock", response.getMeta().getType());
    // Verify values
    assertNotNull(response.getValues());
    assertEquals(2, response.getValues().size());
    var firstValue = response.getValues().get(0);
    assertEquals("2021-09-16 15:59:00", firstValue.getDatetime());
    assertEquals("148.73500", firstValue.getOpen());
    assertEquals("148.86000", firstValue.getHigh());
    assertEquals("148.73000", firstValue.getLow());
    assertEquals("148.85001", firstValue.getClose());
    assertEquals("624277", firstValue.getVolume());
    // Verify request
    var request = mockWebServer.takeRequest();
    assertEquals("GET", request.getMethod());
    assertTrue(request.getPath().contains("/time_series"));
    assertTrue(request.getPath().contains("symbol=AAPL"));
    assertTrue(request.getPath().contains("interval=1min"));
    assertTrue(request.getPath().contains("apikey=test-api-key"));
  }

  @Test
  void testTimeSeriesWithAllParameters() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request with all parameters
    TimeSeriesResponse response =
        endpoint
            .symbol("AAPL")
            .interval("1min")
            .startDate("2021-09-16")
            .endDate("2021-09-17")
            .outputsize(100)
            .timezone("America/New_York")
            .prepost(true)
            .micCode("XNAS")
            .figi("BBG000B9XRY4")
            .isin("US0378331005")
            .cusip("037833100")
            .format("JSON")
            .delimiter(", ")
            .asObject();
    // Verify response
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    // Verify request contains all parameters
    var request = mockWebServer.takeRequest();
    String path = request.getPath();
    assertTrue(path.contains("symbol=AAPL"));
    assertTrue(path.contains("interval=1min"));
    assertTrue(path.contains("start_date=2021-09-16"));
    assertTrue(path.contains("end_date=2021-09-17"));
    assertTrue(path.contains("outputsize=100"));
    assertTrue(path.contains("timezone=America%2FNew_York"));
    assertTrue(path.contains("prepost=true"));
    assertTrue(path.contains("mic_code=XNAS"));
    assertTrue(path.contains("figi=BBG000B9XRY4"));
    assertTrue(path.contains("isin=US0378331005"));
    assertTrue(path.contains("cusip=037833100"));
    assertTrue(path.contains("format=JSON"));
    assertTrue(path.contains("delimiter=%2C"));
  }

  @Test
  void testTimeSeriesWithDifferentIntervals() throws Exception {
    String[] intervals = {
      "1min", "5min", "15min", "30min", "45min", "1h", "2h", "4h", "1day", "1week", "1month"
    };
    for (String interval : intervals) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      TimeSeriesResponse response = endpoint.symbol("AAPL").interval(interval).asObject();
      // Verify response
      assertNotNull(response);
      assertEquals("ok", response.getStatus());
      // Verify request contains correct interval
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("interval=" + interval));
    }
  }

  @Test
  void testTimeSeriesWithDifferentOutputSizes() throws Exception {
    int[] outputSizes = {
      1, 5, 10, 15, 30, 60, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000
    };
    for (int outputSize : outputSizes) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      TimeSeriesResponse response =
          endpoint.symbol("AAPL").interval("1min").outputsize(outputSize).asObject();
      // Verify response
      assertNotNull(response);
      assertEquals("ok", response.getStatus());
      // Verify request contains correct output size
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("outputsize=" + outputSize));
    }
  }

  @Test
  void testTimeSeriesWithDifferentFormats() throws Exception {
    String[] formats = {"JSON", "CSV"};
    for (String format : formats) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      TimeSeriesResponse response =
          endpoint.symbol("AAPL").interval("1min").format(format).asObject();
      // Verify response
      assertNotNull(response);
      assertEquals("ok", response.getStatus());
      // Verify request contains correct format
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("format=" + format));
    }
  }

  @Test
  void testTimeSeriesWithPrepostParameter() throws Exception {
    // Test with prepost=true
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    TimeSeriesResponse response = endpoint.symbol("AAPL").interval("1min").prepost(true).asObject();
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("prepost=true"));
    // Test with prepost=false
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    response = endpoint.symbol("AAPL").interval("1min").prepost(false).asObject();
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("prepost=false"));
  }

  @Test
  void testTimeSeriesWithDateParameters() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request with date parameters
    TimeSeriesResponse response =
        endpoint
            .symbol("AAPL")
            .interval("1day")
            .startDate("2021-01-01")
            .endDate("2021-12-31")
            .asObject();
    // Verify response
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    // Verify request contains date parameters
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("start_date=2021-01-01"));
    assertTrue(request.getPath().contains("end_date=2021-12-31"));
  }

  @Test
  void testTimeSeriesWithTimezoneParameter() throws Exception {
    String[] timezones = {"America/New_York", "Europe/London", "Asia/Tokyo", "UTC"};
    for (String timezone : timezones) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      TimeSeriesResponse response =
          endpoint.symbol("AAPL").interval("1min").timezone(timezone).asObject();
      // Verify response
      assertNotNull(response);
      assertEquals("ok", response.getStatus());
      // Verify request contains correct timezone
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("timezone=" + timezone.replace("/", "%2F")));
    }
  }

  @Test
  void testTimeSeriesWithIdentifierParameters() throws Exception {
    // Test with FIGI
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    TimeSeriesResponse response = endpoint.interval("1min").figi("BBG000B9XRY4").asObject();
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("figi=BBG000B9XRY4"));
    // Test with ISIN
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    response = endpoint.interval("1min").isin("US0378331005").asObject();
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("isin=US0378331005"));
    // Test with CUSIP
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    response = endpoint.interval("1min").cusip("037833100").asObject();
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("cusip=037833100"));
  }

  @Test
  void testTimeSeriesWithMICCode() throws Exception {
    String[] micCodes = {"XNAS", "XLON", "XTKS", "XAMS"};
    for (String micCode : micCodes) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      TimeSeriesResponse response =
          endpoint.symbol("AAPL").interval("1min").micCode(micCode).asObject();
      // Verify response
      assertNotNull(response);
      assertEquals("ok", response.getStatus());
      // Verify request contains correct MIC code
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("mic_code=" + micCode));
    }
  }

  @Test
  void testTimeSeriesWithDelimiter() throws Exception {
    String[] delimiters = {", ", "; ", "|", "\t"};
    for (String delimiter : delimiters) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      TimeSeriesResponse response =
          endpoint.symbol("AAPL").interval("1min").delimiter(delimiter).asObject();
      // Verify response
      assertNotNull(response);
      assertEquals("ok", response.getStatus());
      // Verify request contains correct delimiter
      var request = mockWebServer.takeRequest();
      String expectedDelimiter;
      if (delimiter.equals(", ")) {
        expectedDelimiter = "%2C";
      } else if (delimiter.equals("; ")) {
        expectedDelimiter = "%3B";
      } else if (delimiter.equals("|")) {
        expectedDelimiter = "%7C";
      } else if (delimiter.equals("\t")) {
        expectedDelimiter = "%09";
      } else {
        expectedDelimiter = delimiter;
      }
      assertTrue(request.getPath().contains("delimiter=" + expectedDelimiter));
    }
  }

  @Test
  void testTimeSeriesErrorHandling() throws Exception {
    // Test 400 Bad Request
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(400)
            .setBody(TestUtils.ERROR_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    assertThrows(
        Exception.class,
        () -> {
          endpoint.symbol("").interval("1min").asObject();
        });
    // Test 401 Unauthorized
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(401)
            .setBody(TestUtils.UNAUTHORIZED_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    assertThrows(
        Exception.class,
        () -> {
          endpoint.symbol("AAPL").interval("1min").asObject();
        });
    // Test 429 Rate Limit
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(429)
            .setBody(TestUtils.RATE_LIMIT_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    assertThrows(
        Exception.class,
        () -> {
          endpoint.symbol("AAPL").interval("1min").asObject();
        });
    // Test 500 Server Error
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(500)
            .setBody(TestUtils.SERVER_ERROR_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    assertThrows(
        Exception.class,
        () -> {
          endpoint.symbol("AAPL").interval("1min").asObject();
        });
  }

  @Test
  void testTimeSeriesResponseDataTypes() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.TIME_SERIES_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    TimeSeriesResponse response = endpoint.symbol("AAPL").interval("1min").asObject();
    // Verify response structure
    assertNotNull(response);
    assertEquals("ok", response.getStatus());
    // Verify meta data
    assertNotNull(response.getMeta());
    assertEquals("AAPL", response.getMeta().getSymbol());
    assertEquals("1min", response.getMeta().getInterval());
    assertEquals("USD", response.getMeta().getCurrency());
    assertEquals("America/New_York", response.getMeta().getExchangeTimezone());
    assertEquals("NASDAQ", response.getMeta().getExchange());
    assertEquals("XNAS", response.getMeta().getMicCode());
    assertEquals("Common Stock", response.getMeta().getType());
    // Verify values data
    assertNotNull(response.getValues());
    assertEquals(2, response.getValues().size());
    var firstValue = response.getValues().get(0);
    assertEquals("2021-09-16 15:59:00", firstValue.getDatetime());
    assertEquals("148.73500", firstValue.getOpen());
    assertEquals("148.86000", firstValue.getHigh());
    assertEquals("148.73000", firstValue.getLow());
    assertEquals("148.85001", firstValue.getClose());
    assertEquals("624277", firstValue.getVolume());
    // Test numeric conversion methods
    assertEquals(148.73500, firstValue.getOpenAsDouble());
    assertEquals(148.86000, firstValue.getHighAsDouble());
    assertEquals(148.73000, firstValue.getLowAsDouble());
    assertEquals(148.85001, firstValue.getCloseAsDouble());
    assertEquals(624277L, firstValue.getVolumeAsLong());
  }

  @Test
  void testTimeSeriesNullHandling() throws Exception {
    // Setup mock response with null values
    String responseWithNulls =
        """
        {
        "meta": {
        "symbol": "AAPL",
        "interval": "1min",
        "currency": "USD",
        "exchange_timezone": "America/New_York",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "type": "Common Stock"
        } ,
        "values": [
        {
        "datetime": "2021-09-16 15:59:00",
        "open": null,
        "high": null,
        "low": null,
        "close": null,
        "volume": null
        }
        ],
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(responseWithNulls)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    TimeSeriesResponse response = endpoint.symbol("AAPL").interval("1min").asObject();
    // Verify null handling
    assertNotNull(response);
    var firstValue = response.getValues().get(0);
    assertNull(firstValue.getOpenAsDouble());
    assertNull(firstValue.getHighAsDouble());
    assertNull(firstValue.getLowAsDouble());
    assertNull(firstValue.getCloseAsDouble());
    assertNull(firstValue.getVolumeAsLong());
  }

  @Test
  void testTimeSeriesInvalidNumericHandling() throws Exception {
    // Setup mock response with invalid numeric values
    String responseWithInvalidNumbers =
        """
        {
        "meta": {
        "symbol": "AAPL",
        "interval": "1min",
        "currency": "USD",
        "exchange_timezone": "America/New_York",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "type": "Common Stock"
        } ,
        "values": [
        {
        "datetime": "2021-09-16 15:59:00",
        "open": "not-a-number",
        "high": "invalid",
        "low": "N/A",
        "close": "error",
        "volume": "text"
        }
        ],
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(responseWithInvalidNumbers)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    TimeSeriesResponse response = endpoint.symbol("AAPL").interval("1min").asObject();
    // Verify invalid numeric handling
    assertNotNull(response);
    var firstValue = response.getValues().get(0);
    // These should throw NumberFormatException when called
    assertThrows(NumberFormatException.class, () -> firstValue.getOpenAsDouble());
    assertThrows(NumberFormatException.class, () -> firstValue.getHighAsDouble());
    assertThrows(NumberFormatException.class, () -> firstValue.getLowAsDouble());
    assertThrows(NumberFormatException.class, () -> firstValue.getCloseAsDouble());
    assertThrows(NumberFormatException.class, () -> firstValue.getVolumeAsLong());
  }
}
