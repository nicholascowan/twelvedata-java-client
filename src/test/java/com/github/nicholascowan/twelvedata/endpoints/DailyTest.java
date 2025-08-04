package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.endpoints.Daily;
import com.github.nicholascowan.twelvedata.http.DefaultHttpClient;
import com.github.nicholascowan.twelvedata.models.DailyResponse;

import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Daily using MockWebServer. Note: Exception testing is handled by the
 * consolidated ExceptionTest class.
 */
@Tag("UnitTest")
class DailyTest {
  private MockWebServer mockWebServer;
  private Daily endpoint;

  @BeforeEach
  void setUp() throws Exception {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    String baseUrl = mockWebServer.url("/").toString();
    DefaultHttpClient httpClient = new DefaultHttpClient(baseUrl, 30000);
    TwelveDataContext context =
        new TwelveDataContext("test-api-key", baseUrl, httpClient, new HashMap<>());
    endpoint = new Daily(context);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  void testDailyCreation() {
    assertNotNull(endpoint);
  }

  @Test
  void testDailyBasicRequest() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "AAPL",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "185.59",
                "high": "186.12",
                "low": "183.62",
                "close": "185.14",
                "volume": "52464180"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse dailyResponse = endpoint.symbol("AAPL").asObject();
    // Verify response
    assertNotNull(dailyResponse);
    assertEquals("ok", dailyResponse.getStatus());
    assertNotNull(dailyResponse.getMeta());
    assertEquals("AAPL", dailyResponse.getMeta().getSymbol());
    assertEquals("1day", dailyResponse.getMeta().getInterval());
    assertEquals("NASDAQ", dailyResponse.getMeta().getExchange());
    assertNotNull(dailyResponse.getValues());
    assertEquals(1, dailyResponse.getValues().size());
    assertEquals("2024-01-15", dailyResponse.getValues().get(0).getDatetime());
    assertEquals("185.14", dailyResponse.getValues().get(0).getClose());
    
    // Test DailyResponse specific methods
    assertTrue(dailyResponse.isDailyData());
    assertEquals("1day", dailyResponse.getDailyInterval());
    assertEquals("185.14", dailyResponse.getLatestClose());
    assertEquals(185.14, dailyResponse.getLatestCloseAsDouble());
    assertEquals(185.14f, dailyResponse.getLatestCloseAsFloat());
    assertEquals("2024-01-15", dailyResponse.getLatestDate());
    assertEquals(1, dailyResponse.getDailyDataCount());
    assertTrue(dailyResponse.hasDailyData());
    assertNotNull(dailyResponse.getDailyDataPoint(0));
    assertNotNull(dailyResponse.getDailyDataPoints());
  }

  @Test
  void testDailyWithAllParameters() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "AAPL",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "185.59",
                "high": "186.12",
                "low": "183.62",
                "close": "185.14",
                "volume": "52464180"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request with all parameters
    DailyResponse dailyResponse =
        endpoint
            .symbol("AAPL")
            .exchange("NASDAQ")
            .country("US")
            .type("Common Stock")
            .outputsize(30)
            .startDate("2024-01-01")
            .endDate("2024-01-31")
            .dp(2)
            .timezone("America/New_York")
            .order("desc")
            .prepost(false)
            .date("2024-01-15")
            .micCode("XNAS")
            .previousClose("184.50")
            .adjust("split")
            .figi("BBG000B9XRY4")
            .isin("US0378331005")
            .cusip("037833100")
            .format("JSON")
            .delimiter(",")
            .asObject();
    // Verify response
    assertNotNull(dailyResponse);
    assertEquals("ok", dailyResponse.getStatus());
    assertEquals("AAPL", dailyResponse.getMeta().getSymbol());
    assertEquals("1day", dailyResponse.getMeta().getInterval());
  }

  @Test
  void testDailyWithDifferentExchanges() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "MSFT",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "374.69",
                "high": "376.04",
                "low": "372.96",
                "close": "374.69",
                "volume": "22113980"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse dailyResponse = endpoint.symbol("MSFT").exchange("NASDAQ").asObject();
    // Verify response
    assertNotNull(dailyResponse);
    assertEquals("MSFT", dailyResponse.getMeta().getSymbol());
    assertEquals("NASDAQ", dailyResponse.getMeta().getExchange());
    assertEquals("374.69", dailyResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithDifferentCountries() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "TSLA",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "237.49",
                "high": "242.19",
                "low": "235.51",
                "close": "237.49",
                "volume": "108059580"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse dailyResponse = endpoint.symbol("TSLA").country("US").asObject();
    // Verify response
    assertNotNull(dailyResponse);
    assertEquals("TSLA", dailyResponse.getMeta().getSymbol());
    assertEquals("237.49", dailyResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithDifferentTypes() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "SPY",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "ARCA",
            "mic_code": "ARCX",
            "type": "ETF"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "476.12",
                "high": "477.23",
                "low": "474.89",
                "close": "476.12",
                "volume": "98765432"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse dailyResponse = endpoint.symbol("SPY").type("ETF").asObject();
    // Verify response
    assertNotNull(dailyResponse);
    assertEquals("SPY", dailyResponse.getMeta().getSymbol());
    assertEquals("ETF", dailyResponse.getMeta().getType());
    assertEquals("476.12", dailyResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithDifferentDecimalPlaces() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "GOOGL",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "140.93",
                "high": "142.15",
                "low": "140.12",
                "close": "140.93",
                "volume": "23456789"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse dailyResponse = endpoint.symbol("GOOGL").dp(2).asObject();
    // Verify response
    assertNotNull(dailyResponse);
    assertEquals("GOOGL", dailyResponse.getMeta().getSymbol());
    assertEquals("140.93", dailyResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithPrepostSettings() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "AMZN",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "151.94",
                "high": "153.27",
                "low": "151.12",
                "close": "151.94",
                "volume": "45678901"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("AMZN").prepost(true).asObject();
    // Verify response
    assertNotNull(timeSeriesResponse);
    assertEquals("AMZN", timeSeriesResponse.getMeta().getSymbol());
    assertEquals("151.94", timeSeriesResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithDifferentMicCodes() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "NVDA",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "522.53",
                "high": "525.18",
                "low": "520.91",
                "close": "522.53",
                "volume": "34567890"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("NVDA").micCode("XNAS").asObject();
    // Verify response
    assertNotNull(timeSeriesResponse);
    assertEquals("NVDA", timeSeriesResponse.getMeta().getSymbol());
    assertEquals("XNAS", timeSeriesResponse.getMeta().getMicCode());
    assertEquals("522.53", timeSeriesResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithFigi() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "META",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "374.58",
                "high": "376.92",
                "low": "373.15",
                "close": "374.58",
                "volume": "23456789"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("META").figi("BBG000BHTMY7").asObject();
    // Verify response
    assertNotNull(timeSeriesResponse);
    assertEquals("META", timeSeriesResponse.getMeta().getSymbol());
    assertEquals("374.58", timeSeriesResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithIsin() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "NFLX",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "492.42",
                "high": "495.18",
                "low": "490.75",
                "close": "492.42",
                "volume": "12345678"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("NFLX").isin("US0378331005").asObject();
    // Verify response
    assertNotNull(timeSeriesResponse);
    assertEquals("NFLX", timeSeriesResponse.getMeta().getSymbol());
    assertEquals("492.42", timeSeriesResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithCusip() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "CRM",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NYSE",
            "mic_code": "XNYS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "284.96",
                "high": "287.23",
                "low": "283.45",
                "close": "284.96",
                "volume": "8765432"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("CRM").cusip("594918104").asObject();
    // Verify response
    assertNotNull(timeSeriesResponse);
    assertEquals("CRM", timeSeriesResponse.getMeta().getSymbol());
    assertEquals("284.96", timeSeriesResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithDifferentFormats() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "ADBE",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "596.47",
                "high": "598.92",
                "low": "594.18",
                "close": "596.47",
                "volume": "6543210"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("ADBE").format("JSON").asObject();
    // Verify response
    assertNotNull(timeSeriesResponse);
    assertEquals("ADBE", timeSeriesResponse.getMeta().getSymbol());
    assertEquals("596.47", timeSeriesResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithDelimiter() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "PYPL",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "61.47",
                "high": "62.18",
                "low": "60.92",
                "close": "61.47",
                "volume": "5432109"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("PYPL").delimiter(",").asObject();
    // Verify response
    assertNotNull(timeSeriesResponse);
    assertEquals("PYPL", timeSeriesResponse.getMeta().getSymbol());
    assertEquals("61.47", timeSeriesResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyWithSpecificDate() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "INTC",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "47.93",
                "high": "48.27",
                "low": "47.45",
                "close": "47.93",
                "volume": "4321098"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("INTC").date("2024-01-15").asObject();
    // Verify response
    assertNotNull(timeSeriesResponse);
    assertEquals("INTC", timeSeriesResponse.getMeta().getSymbol());
    assertEquals("2024-01-15", timeSeriesResponse.getValues().get(0).getDatetime());
    assertEquals("47.93", timeSeriesResponse.getValues().get(0).getClose());
  }

  @Test
  void testDailyErrorHandling() throws Exception {
    // Setup mock error response
    String errorResponse =
        """
        {
        "status": "error",
        "code": 400,
        "message": "Invalid symbol"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(errorResponse).addHeader("Content-Type", "application/json"));
    // Execute request and expect exception
    assertThrows(Exception.class, () -> endpoint.symbol("INVALID").asObject());
  }

  @Test
  void testDailyResponseDataTypes() throws Exception {
    // Setup mock response with different data types
    String response =
        """
        {
        "status": "ok",
        "meta": {
            "symbol": "AAPL",
            "interval": "1day",
            "currency": "USD",
            "exchange_timezone": "America/New_York",
            "exchange": "NASDAQ",
            "mic_code": "XNAS",
            "type": "Common Stock"
        },
        "values": [
            {
                "datetime": "2024-01-15",
                "open": "185.59",
                "high": "186.12",
                "low": "183.62",
                "close": "185.14",
                "volume": "52464180"
            }
        ]
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    DailyResponse timeSeriesResponse = endpoint.symbol("AAPL").asObject();
    // Verify data types
    assertNotNull(timeSeriesResponse);
    assertTrue(timeSeriesResponse.getStatus() instanceof String);
    assertNotNull(timeSeriesResponse.getMeta());
    assertTrue(timeSeriesResponse.getMeta().getSymbol() instanceof String);
    assertTrue(timeSeriesResponse.getMeta().getInterval() instanceof String);
    assertTrue(timeSeriesResponse.getMeta().getExchange() instanceof String);
    assertNotNull(timeSeriesResponse.getValues());
    assertTrue(timeSeriesResponse.getValues().get(0).getDatetime() instanceof String);
    assertTrue(timeSeriesResponse.getValues().get(0).getClose() instanceof String);
  }

  @Test
  void testDailyUrlGeneration() {
    // Configure endpoint
    endpoint.symbol("AAPL")
        .exchange("NASDAQ")
        .country("US")
        .type("Common Stock")
        .dp(2)
        .date("2024-01-15");
    
    // Get URL and verify it contains expected parameters
    String url = endpoint.asUrl();
    assertTrue(url.contains("symbol=AAPL"));
    assertTrue(url.contains("interval=1day"));
    assertTrue(url.contains("exchange=NASDAQ"));
    assertTrue(url.contains("country=US"));
    assertTrue(url.contains("type=Common Stock"));
    assertTrue(url.contains("dp=2"));
    assertTrue(url.contains("date=2024-01-15"));
  }

  @Test
  void testDailyConstructorWithSymbol() {
    // Test constructor with symbol
    Daily dailyWithSymbol = new Daily(endpoint.context, "AAPL");
    assertNotNull(dailyWithSymbol);
    assertTrue(dailyWithSymbol.isPrice());
  }

  @Test
  void testDailyConstructorWithAllParameters() {
    // Test constructor with all parameters
    Daily dailyWithAllParams = new Daily(
        endpoint.context,
        "AAPL",
        "NASDAQ",
        "US",
        "Common Stock",
        30,
        "2024-01-01",
        "2024-01-31",
        2,
        "America/New_York",
        "desc",
        "false",
        "2024-01-15",
        "XNAS",
        "184.50",
        "split");
    assertNotNull(dailyWithAllParams);
    assertTrue(dailyWithAllParams.isPrice());
  }

  @Test
  void testDailyFixedInterval() {
    // Verify that the interval is always set to "1day"
    Daily daily = new Daily(endpoint.context);
    String url = daily.symbol("AAPL").asUrl();
    assertTrue(url.contains("interval=1day"));
    
    // Verify that the interval is set correctly in constructor with symbol
    Daily dailyWithSymbol = new Daily(endpoint.context, "AAPL");
    String url2 = dailyWithSymbol.asUrl();
    assertTrue(url2.contains("interval=1day"));
  }
} 