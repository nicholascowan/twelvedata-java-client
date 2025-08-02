package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.endpoints.Price;
import com.github.nicholascowan.twelvedata.http.DefaultHttpClient;
import com.github.nicholascowan.twelvedata.models.PriceResponse;
import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Price using MockWebServer. Note: Exception testing is handled by the
 * consolidated ExceptionTest class.
 */
@Tag("UnitTest")
class PriceTest {
  private MockWebServer mockWebServer;
  private Price endpoint;

  @BeforeEach
  void setUp() throws Exception {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    String baseUrl = mockWebServer.url("/").toString();
    DefaultHttpClient httpClient = new DefaultHttpClient(baseUrl, 30000);
    TwelveDataContext context =
        new TwelveDataContext("test-api-key", baseUrl, httpClient, new HashMap<>());
    endpoint = new Price(context);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  void testPriceCreation() {
    assertNotNull(endpoint);
  }

  @Test
  void testPriceBasicRequest() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    PriceResponse priceResponse = endpoint.symbol("AAPL").asObject();
    // Verify response
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
    assertEquals(150.25, priceResponse.getPriceAsDouble());
    assertEquals(150.25f, priceResponse.getPriceAsFloat());
  }

  @Test
  void testPriceWithAllParameters() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request with all parameters
    PriceResponse priceResponse =
        endpoint
            .symbol("AAPL")
            .exchange("NASDAQ")
            .country("US")
            .type("Common Stock")
            .dp(2)
            .prepost(true)
            .micCode("XNGS")
            .figi("BBG000B9XRY4")
            .isin("US0378331005")
            .cusip("037833100")
            .format("JSON")
            .delimiter(", ")
            .asObject();
    // Verify response
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithDifferentExchanges() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with different exchanges
    PriceResponse priceResponse = endpoint.symbol("AAPL").exchange("NASDAQ").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithDifferentCountries() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with different countries
    PriceResponse priceResponse = endpoint.symbol("AAPL").country("US").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithDifferentTypes() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with different types
    PriceResponse priceResponse = endpoint.symbol("AAPL").type("Common Stock").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithDifferentDecimalPlaces() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with different decimal places
    PriceResponse priceResponse = endpoint.symbol("AAPL").dp(2).asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithPrepostSettings() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with prepost settings
    PriceResponse priceResponse = endpoint.symbol("AAPL").prepost(true).asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithDifferentMicCodes() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with different MIC codes
    PriceResponse priceResponse = endpoint.symbol("AAPL").micCode("XNGS").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithFigi() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with FIGI
    PriceResponse priceResponse = endpoint.symbol("AAPL").figi("BBG000B9XRY4").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithIsin() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with ISIN
    PriceResponse priceResponse = endpoint.symbol("AAPL").isin("US0378331005").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithCusip() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with CUSIP
    PriceResponse priceResponse = endpoint.symbol("AAPL").cusip("037833100").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithDifferentFormats() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with different formats
    PriceResponse priceResponse = endpoint.symbol("AAPL").format("JSON").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceWithDelimiter() throws Exception {
    // Setup mock response
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Test with delimiter
    PriceResponse priceResponse = endpoint.symbol("AAPL").delimiter(", ").asObject();
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
  }

  @Test
  void testPriceErrorHandling() throws Exception {
    // Setup mock response with error
    String errorResponse =
        """
        {
        "code": 400,
        "message": "Bad Request",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(400)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect exception
    assertThrows(
        Exception.class,
        () -> {
          endpoint.symbol("INVALID").asObject();
        });
  }

  @Test
  void testPriceResponseDataTypes() throws Exception {
    // Setup mock response with different numeric formats
    String response =
        """
        {
        "price": "150.25",
        "status": "ok"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse().setBody(response).addHeader("Content-Type", "application/json"));
    // Execute request
    PriceResponse priceResponse = endpoint.symbol("AAPL").asObject();
    // Verify data types
    assertNotNull(priceResponse);
    assertEquals("150.25", priceResponse.getPrice());
    assertEquals(150.25, priceResponse.getPriceAsDouble());
    assertEquals(150.25f, priceResponse.getPriceAsFloat());
  }
  // Note: Exception testing is now handled by the consolidated ExceptionTest class
}
