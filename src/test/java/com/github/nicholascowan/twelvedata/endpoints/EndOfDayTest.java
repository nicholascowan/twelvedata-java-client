package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.endpoints.EndOfDay;
import com.github.nicholascowan.twelvedata.http.DefaultHttpClient;
import com.github.nicholascowan.twelvedata.models.EndOfDayResponse;
import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for EndOfDay using MockWebServer. Note: Exception testing is handled by the
 * consolidated ExceptionTest class.
 */
@Tag("UnitTest")
class EndOfDayTest {
  private MockWebServer mockWebServer;
  private EndOfDay endpoint;

  @BeforeEach
  void setUp() throws Exception {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    String baseUrl = mockWebServer.url("/").toString();
    DefaultHttpClient httpClient = new DefaultHttpClient(baseUrl, 30000);
    TwelveDataContext context =
        new TwelveDataContext("test-api-key", baseUrl, httpClient, new HashMap<>());
    endpoint = new EndOfDay(context);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  void testEndOfDayCreation() {
    assertNotNull(endpoint);
  }

  @Test
  void testEndOfDayBasicRequest() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "AAPL",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "148.79"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("AAPL").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("AAPL", eodResponse.getSymbol());
    assertEquals("NASDAQ", eodResponse.getExchange());
    assertEquals("XNAS", eodResponse.getMicCode());
    assertEquals("USD", eodResponse.getCurrency());
    assertEquals("2021-09-16", eodResponse.getDatetime());
    assertEquals("148.79", eodResponse.getClose());
    assertEquals(148.79, eodResponse.getCloseAsDouble());
    assertEquals(148.79f, eodResponse.getCloseAsFloat());
  }

  @Test
  void testEndOfDayWithAllParameters() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "AAPL",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "148.79"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request with all parameters
    EndOfDayResponse eodResponse =
        endpoint
            .symbol("AAPL")
            .figi("BBG000BHTMY7")
            .isin("US0378331005")
            .cusip("594918104")
            .exchange("NASDAQ")
            .micCode("XNAS")
            .country("US")
            .type("Common Stock")
            .date("2021-09-16")
            .prepost(false)
            .dp(2)
            .format("JSON")
            .delimiter(",")
            .asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("AAPL", eodResponse.getSymbol());
    assertEquals("NASDAQ", eodResponse.getExchange());
    assertEquals("148.79", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithDifferentExchanges() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "MSFT",
        "exchange": "NYSE",
        "mic_code": "XNYS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "299.50"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("MSFT").exchange("NYSE").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("MSFT", eodResponse.getSymbol());
    assertEquals("NYSE", eodResponse.getExchange());
    assertEquals("XNYS", eodResponse.getMicCode());
    assertEquals("299.50", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithDifferentCountries() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "TSLA",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "759.49"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("TSLA").country("US").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("TSLA", eodResponse.getSymbol());
    assertEquals("NASDAQ", eodResponse.getExchange());
    assertEquals("759.49", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithDifferentTypes() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "SPY",
        "exchange": "ARCA",
        "mic_code": "ARCX",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "447.23"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("SPY").type("ETF").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("SPY", eodResponse.getSymbol());
    assertEquals("ARCA", eodResponse.getExchange());
    assertEquals("ARCX", eodResponse.getMicCode());
    assertEquals("447.23", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithDifferentDecimalPlaces() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "GOOGL",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "2758.50"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("GOOGL").dp(2).asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("GOOGL", eodResponse.getSymbol());
    assertEquals("2758.50", eodResponse.getClose());
    assertEquals(2758.50, eodResponse.getCloseAsDouble());
  }

  @Test
  void testEndOfDayWithPrepostSettings() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "AMZN",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "3429.26"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("AMZN").prepost(true).asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("AMZN", eodResponse.getSymbol());
    assertEquals("3429.26", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithDifferentMicCodes() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "NVDA",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "220.48"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("NVDA").micCode("XNAS").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("NVDA", eodResponse.getSymbol());
    assertEquals("XNAS", eodResponse.getMicCode());
    assertEquals("220.48", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithFigi() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "META",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "347.71"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("META").figi("BBG000BHTMY7").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("META", eodResponse.getSymbol());
    assertEquals("347.71", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithIsin() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "NFLX",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "589.35"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("NFLX").isin("US0378331005").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("NFLX", eodResponse.getSymbol());
    assertEquals("589.35", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithCusip() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "CRM",
        "exchange": "NYSE",
        "mic_code": "XNYS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "271.73"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("CRM").cusip("594918104").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("CRM", eodResponse.getSymbol());
    assertEquals("271.73", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithDifferentFormats() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "ADBE",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "659.83"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("ADBE").format("JSON").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("ADBE", eodResponse.getSymbol());
    assertEquals("659.83", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithDelimiter() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "PYPL",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "298.54"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("PYPL").delimiter(",").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("PYPL", eodResponse.getSymbol());
    assertEquals("298.54", eodResponse.getClose());
  }

  @Test
  void testEndOfDayWithSpecificDate() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "symbol": "INTC",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "53.28"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("INTC").date("2021-09-16").asObject();
    // Verify response
    assertNotNull(eodResponse);
    assertEquals("INTC", eodResponse.getSymbol());
    assertEquals("2021-09-16", eodResponse.getDatetime());
    assertEquals("53.28", eodResponse.getClose());
  }

  @Test
  void testEndOfDayErrorHandling() throws Exception {
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
  void testEndOfDayResponseDataTypes() throws Exception {
    // Setup mock response with different data types
    String response =
        """
        {
        "symbol": "AAPL",
        "exchange": "NASDAQ",
        "mic_code": "XNAS",
        "currency": "USD",
        "datetime": "2021-09-16",
        "close": "148.79"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    EndOfDayResponse eodResponse = endpoint.symbol("AAPL").asObject();
    // Verify data types
    assertNotNull(eodResponse);
    assertTrue(eodResponse.getSymbol() instanceof String);
    assertTrue(eodResponse.getExchange() instanceof String);
    assertTrue(eodResponse.getMicCode() instanceof String);
    assertTrue(eodResponse.getCurrency() instanceof String);
    assertTrue(eodResponse.getDatetime() instanceof String);
    assertTrue(eodResponse.getClose() instanceof String);
    assertTrue(eodResponse.getCloseAsDouble() instanceof Double);
    assertTrue(eodResponse.getCloseAsFloat() instanceof Float);
  }

  @Test
  void testEndOfDayUrlGeneration() {
    // Configure endpoint
    endpoint.symbol("AAPL")
        .exchange("NASDAQ")
        .country("US")
        .type("Common Stock")
        .dp(2)
        .date("2021-09-16");
    
    // Get URL and verify it contains expected parameters
    String url = endpoint.asUrl();
    assertTrue(url.contains("symbol=AAPL"));
    assertTrue(url.contains("exchange=NASDAQ"));
    assertTrue(url.contains("country=US"));
    assertTrue(url.contains("type=Common Stock"));
    assertTrue(url.contains("dp=2"));
    assertTrue(url.contains("date=2021-09-16"));
  }

  @Test
  void testEndOfDayConstructorWithSymbol() {
    // Test constructor with symbol
    EndOfDay eodWithSymbol = new EndOfDay(endpoint.context, "AAPL");
    assertNotNull(eodWithSymbol);
    assertTrue(eodWithSymbol.isPrice());
  }

  @Test
  void testEndOfDayConstructorWithAllParameters() {
    // Test constructor with all parameters
    EndOfDay eodWithAllParams = new EndOfDay(
        endpoint.context,
        "AAPL",
        "BBG000BHTMY7",
        "US0378331005",
        "594918104",
        "NASDAQ",
        "XNAS",
        "US",
        "Common Stock",
        "2021-09-16",
        false,
        2);
    assertNotNull(eodWithAllParams);
    assertTrue(eodWithAllParams.isPrice());
  }
} 