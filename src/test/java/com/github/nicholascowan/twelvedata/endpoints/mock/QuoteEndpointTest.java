package com.github.nicholascowan.twelvedata.endpoints.mock;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.TestUtils;
import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.endpoints.QuoteEndpoint;
import com.github.nicholascowan.twelvedata.http.DefaultHttpClient;
import com.github.nicholascowan.twelvedata.models.QuoteResponse;
import java.util.HashMap;
import java.util.Map;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive mock tests for Quote endpoint functionality. These tests use MockWebServer to avoid
 * hitting the actual API.
 */
class QuoteEndpointTest {
  private MockWebServer mockWebServer;
  private QuoteEndpoint endpoint;

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
    endpoint = new QuoteEndpoint(context);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  void testQuoteEndpointCreation() {
    assertNotNull(endpoint);
    assertFalse(endpoint.isPrice());
    assertFalse(endpoint.isIndicator());
    assertFalse(endpoint.isOverlay());
    assertFalse(endpoint.isBatch());
  }

  @Test
  void testQuoteBasicRequest() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    QuoteResponse response = endpoint.symbol("AAPL").asObject();
    // Verify response
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    assertEquals("AAPL", response.getSymbol());
    assertNotNull(response.getName());
    assertNotNull(response.getExchange());
    assertNotNull(response.getCurrency());
    assertNotNull(response.getClose());
    assertNotNull(response.getVolume());
    // Verify numeric conversions
    assertNotNull(response.getCloseAsDouble());
    assertNotNull(response.getVolumeAsLong());
    // Verify request was made
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("symbol=AAPL"));
    assertTrue(request.getPath().contains("apikey=test-api-key"));
  }

  @Test
  void testQuoteWithAllParameters() throws Exception {
    // Setup mock response
    String responseWithAllParams =
        """
        {
        "symbol": "AAPL",
        "name": "Apple Inc.",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "150.00",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(responseWithAllParams)
            .addHeader("Content-Type", "application/json"));
    // Execute request with all parameters
    QuoteResponse response =
        endpoint
            .symbol("AAPL")
            .interval("1min")
            .exchange("NASDAQ")
            .country("United States")
            .volumeTimePeriod(9)
            .type("Common Stock")
            .dp(2)
            .timezone("UTC")
            .prepost(true)
            .micCode("XNAS")
            .eod(true)
            .rollingPeriod(24)
            .asObject();
    // Verify response
    assertNotNull(response);
    assertEquals("AAPL", response.getSymbol());
    assertEquals("Apple Inc.", response.getName());
    assertEquals("NASDAQ", response.getExchange());
    assertEquals("XNAS", response.getMicCode());
    assertEquals("USD", response.getCurrency());
    assertEquals("2021-09-16", response.getDatetime());
    assertEquals("150.00", response.getClose());
  }

  @Test
  void testQuoteWithMinimalParameters() throws Exception {
    // Setup mock response
    String responseWithMinimalParams =
        """
        {
        "symbol": "AAPL",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(responseWithMinimalParams)
            .addHeader("Content-Type", "application/json"));
    // Execute request with minimal parameters
    QuoteResponse response = endpoint.symbol("AAPL").asObject();
    // Verify response
    assertNotNull(response);
    assertEquals("AAPL", response.getSymbol());
  }

  @Test
  void testQuoteWithDifferentIntervals() throws Exception {
    String[] intervals = {
      "1min", "5min", "15min", "30min", "45min", "1h", "2h", "4h", "1day", "1week", "1month"
    };
    for (String interval : intervals) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").interval(interval).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct interval
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("interval=" + interval));
    }
  }

  @Test
  void testQuoteWithDifferentExchanges() throws Exception {
    String[] exchanges = {"NASDAQ", "NYSE", "LSE", "TSE"};
    for (String exchange : exchanges) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").exchange(exchange).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct exchange
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("exchange=" + exchange));
    }
  }

  @Test
  void testQuoteWithDifferentCountries() throws Exception {
    String[] countries = {"US", "GB", "JP", "DE"};
    for (String country : countries) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").country(country).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct country
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("country=" + country));
    }
  }

  @Test
  void testQuoteWithVolumeTimePeriods() throws Exception {
    Integer[] volumeTimePeriods = {1, 7, 30, 60, 90};
    for (Integer volumeTimePeriod : volumeTimePeriods) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response =
          endpoint.symbol("AAPL").volumeTimePeriod(volumeTimePeriod).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct volume_time_period
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("volume_time_period=" + volumeTimePeriod));
    }
  }

  @Test
  void testQuoteWithDifferentTypes() throws Exception {
    String[] types = {"Common Stock", "ETF", "Index", "Currency"};
    for (String type : types) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").type(type).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct type
      var request = mockWebServer.takeRequest();
      String expectedType = type.replace(" ", "%20");
      assertTrue(request.getPath().contains("type=" + expectedType));
    }
  }

  @Test
  void testQuoteWithDifferentDecimalPlaces() throws Exception {
    int[] decimalPlaces = {0, 1, 2, 3, 4, 5};
    for (int dp : decimalPlaces) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").dp(dp).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct decimal places
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("dp=" + dp));
    }
  }

  @Test
  void testQuoteWithDifferentTimezones() throws Exception {
    String[] timezones = {"America/New_York", "Europe/London", "Asia/Tokyo", "UTC"};
    for (String timezone : timezones) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").timezone(timezone).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct timezone
      var request = mockWebServer.takeRequest();
      String expectedTimezone = timezone.replace("/", "%2F");
      assertTrue(request.getPath().contains("timezone=" + expectedTimezone));
    }
  }

  @Test
  void testQuoteWithPrepostSettings() throws Exception {
    // Test with prepost = true
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    QuoteResponse response = endpoint.symbol("AAPL").prepost(true).asObject();
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("prepost=true"));
    // Test with prepost = false
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    response = endpoint.symbol("AAPL").prepost(false).asObject();
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("prepost=false"));
  }

  @Test
  void testQuoteWithDifferentMicCodes() throws Exception {
    String[] micCodes = {"XNAS", "XNYS", "XLON", "XTKS"};
    for (String micCode : micCodes) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").micCode(micCode).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct mic_code
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("mic_code=" + micCode));
    }
  }

  @Test
  void testQuoteWithEodSettings() throws Exception {
    // Test with eod = true
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    QuoteResponse response = endpoint.symbol("AAPL").eod(true).asObject();
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("eod=true"));
    // Test with eod = false
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    response = endpoint.symbol("AAPL").eod(false).asObject();
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("eod=false"));
  }

  @Test
  void testQuoteWithRollingPeriods() throws Exception {
    Integer[] rollingPeriods = {1, 7, 24, 48, 168};
    for (Integer rollingPeriod : rollingPeriods) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").rollingPeriod(rollingPeriod).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct rolling_period
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("rolling_period=" + rollingPeriod));
    }
  }

  @Test
  void testQuoteWithFigi() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    QuoteResponse response = endpoint.symbol("AAPL").figi("BBG000B9XRY4").asObject();
    // Verify response
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    // Verify request contains figi
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("figi=BBG000B9XRY4"));
  }

  @Test
  void testQuoteWithIsin() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    QuoteResponse response = endpoint.symbol("AAPL").isin("US0378331005").asObject();
    // Verify response
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    // Verify request contains isin
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("isin=US0378331005"));
  }

  @Test
  void testQuoteWithCusip() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    QuoteResponse response = endpoint.symbol("AAPL").cusip("037833100").asObject();
    // Verify response
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    // Verify request contains cusip
    var request = mockWebServer.takeRequest();
    assertTrue(request.getPath().contains("cusip=037833100"));
  }

  @Test
  void testQuoteWithDifferentFormats() throws Exception {
    String[] formats = {"JSON", "CSV"};
    for (String format : formats) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").format(format).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
      // Verify request contains correct format
      var request = mockWebServer.takeRequest();
      assertTrue(request.getPath().contains("format=" + format));
    }
  }

  @Test
  void testQuoteWithDelimiter() throws Exception {
    String[] delimiters = {", ", "; ", "|", "\t"};
    for (String delimiter : delimiters) {
      // Setup mock response
      mockWebServer.enqueue(
          new MockResponse()
              .setBody(TestUtils.QUOTE_JSON_RESPONSE)
              .addHeader("Content-Type", "application/json"));
      // Execute request
      QuoteResponse response = endpoint.symbol("AAPL").delimiter(delimiter).asObject();
      // Verify response
      assertNotNull(response);
      assertNotNull(response.getSymbol());
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
  void testQuoteErrorHandling() throws Exception {
    // Test 401 Unauthorized
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(401)
            .setBody(TestUtils.UNAUTHORIZED_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    assertThrows(
        Exception.class,
        () -> {
          endpoint.symbol("AAPL").asObject();
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
          endpoint.symbol("AAPL").asObject();
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
          endpoint.symbol("AAPL").asObject();
        });
  }

  @Test
  void testQuoteResponseDataTypes() throws Exception {
    // Setup mock response
    mockWebServer.enqueue(
        new MockResponse()
            .setBody(TestUtils.QUOTE_JSON_RESPONSE)
            .addHeader("Content-Type", "application/json"));
    // Execute request
    QuoteResponse response = endpoint.symbol("AAPL").asObject();
    // Verify response structure
    assertNotNull(response);
    assertNotNull(response.getSymbol());
    assertEquals("AAPL", response.getSymbol());
    assertNotNull(response.getName());
    assertNotNull(response.getExchange());
    assertNotNull(response.getCurrency());
    assertNotNull(response.getClose());
    assertNotNull(response.getVolume());
    // Verify numeric conversions
    assertNotNull(response.getCloseAsDouble());
    assertNotNull(response.getVolumeAsLong());
    assertNotNull(response.getOpenAsDouble());
    assertNotNull(response.getHighAsDouble());
    assertNotNull(response.getLowAsDouble());
    assertNotNull(response.getPreviousCloseAsDouble());
    assertNotNull(response.getChangeAsDouble());
    assertNotNull(response.getPercentChangeAsDouble());
    assertNotNull(response.getAverageVolumeAsLong());
    assertNotNull(response.getTimestampAsLong());
  }
}
