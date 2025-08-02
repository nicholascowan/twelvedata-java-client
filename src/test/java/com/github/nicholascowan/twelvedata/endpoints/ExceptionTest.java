package com.github.nicholascowan.twelvedata.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.endpoints.Price;
import com.github.nicholascowan.twelvedata.exceptions.*;
import com.github.nicholascowan.twelvedata.http.DefaultHttpClient;
import java.util.HashMap;
import java.util.Map;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Simple exception testing for all endpoints. Since all endpoints throw the same exceptions, we
 * test using Price as a representative example.
 */
@Tag("UnitTest")
class ExceptionTest {
  private MockWebServer mockWebServer;
  private Price endpoint;

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
    endpoint = new Price(context);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  void testBadRequestException() throws Exception {
    // Setup mock response with 400 Bad Request
    String errorResponse =
        """
        {
        "code": 400,
        "message": "Bad Request - Invalid symbol",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(400)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    BadRequestException exception =
        assertThrows(
            BadRequestException.class,
            () -> {
              endpoint.symbol("INVALID_SYMBOL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Bad Request - Invalid symbol"));
    assertEquals(400, exception.getErrorCode());
  }

  @Test
  void testUnauthorizedException() throws Exception {
    // Setup mock response with 401 Unauthorized
    String errorResponse =
        """
        {
        "code": 401,
        "message": "Unauthorized - Invalid API key",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(401)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    InvalidApiKeyException exception =
        assertThrows(
            InvalidApiKeyException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Unauthorized - Invalid API key"));
    assertEquals(401, exception.getErrorCode());
  }

  @Test
  void testForbiddenException() throws Exception {
    // Setup mock response with 403 Forbidden
    String errorResponse =
        """
        {
        "code": 403,
        "message": "Forbidden - Access denied",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(403)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    ForbiddenException exception =
        assertThrows(
            ForbiddenException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Forbidden - Access denied"));
    assertEquals(403, exception.getErrorCode());
  }

  @Test
  void testNotFoundException() throws Exception {
    // Setup mock response with 404 Not Found
    String errorResponse =
        """
        {
        "code": 404,
        "message": "Not Found - Symbol not found",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(404)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    NotFoundException exception =
        assertThrows(
            NotFoundException.class,
            () -> {
              endpoint.symbol("NONEXISTENT").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Not Found - Symbol not found"));
    assertEquals(404, exception.getErrorCode());
  }

  @Test
  void testRateLimitException() throws Exception {
    // Setup mock response with 429 Rate Limit Exceeded
    String errorResponse =
        """
        {
        "code": 429,
        "message": "Rate limit exceeded",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(429)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    RateLimitException exception =
        assertThrows(
            RateLimitException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Rate limit exceeded"));
    assertEquals(429, exception.getErrorCode());
  }

  @Test
  void testInternalServerException() throws Exception {
    // Setup mock response with 500 Internal Server Error
    String errorResponse =
        """
        {
        "code": 500,
        "message": "Internal server error",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(500)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    InternalServerException exception =
        assertThrows(
            InternalServerException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Internal server error"));
    assertEquals(500, exception.getErrorCode());
  }

  @Test
  void testServerErrorException() throws Exception {
    // Setup mock response with 502 Bad Gateway
    String errorResponse =
        """
        {
        "code": 502,
        "message": "Bad Gateway",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(502)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    ServerErrorException exception =
        assertThrows(
            ServerErrorException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Bad Gateway"));
    assertEquals(502, exception.getErrorCode());
  }

  @Test
  void testParameterTooLongException() throws Exception {
    // Setup mock response with 414 URI Too Long
    String errorResponse =
        """
        {
        "code": 414,
        "message": "Parameter too long",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(414)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    ParameterTooLongException exception =
        assertThrows(
            ParameterTooLongException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Parameter too long"));
    assertEquals(414, exception.getErrorCode());
  }

  @Test
  void testInvalidApiKeyException() throws Exception {
    // Setup mock response with 401 and specific invalid API key message
    String errorResponse =
        """
        {
        "code": 401,
        "message": "Invalid API key",
        "status": "error"
        }
        """;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(401)
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    InvalidApiKeyException exception =
        assertThrows(
            InvalidApiKeyException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertTrue(exception.getMessage().contains("Invalid API key"));
    assertEquals(401, exception.getErrorCode());
  }

  @Test
  void testNetworkTimeoutException() throws Exception {
    // Setup mock response with timeout
    mockWebServer.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));
    // Execute request and expect exception
    TwelveDataException exception =
        assertThrows(
            TwelveDataException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
  }

  @Test
  void testMalformedJsonResponse() throws Exception {
    // Setup mock response with malformed JSON
    String malformedJson = " { invalid json } ";
    mockWebServer.enqueue(
        new MockResponse().setBody(malformedJson).addHeader("Content-Type", "application/json"));
    // Execute request and expect exception
    TwelveDataException exception =
        assertThrows(
            TwelveDataException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
  }

  @Test
  void testEmptyResponse() throws Exception {
    // Setup mock response with empty body and error status
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(500)
            .setBody("")
            .addHeader("Content-Type", "application/json"));
    // Execute request and expect specific exception
    InternalServerException exception =
        assertThrows(
            InternalServerException.class,
            () -> {
              endpoint.symbol("AAPL").asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
    assertEquals(500, exception.getErrorCode());
  }

  @Test
  void testMissingRequiredParameter() throws Exception {
    // Test that calling asObject () without required symbol parameter throws
    // exception
    TwelveDataException exception =
        assertThrows(
            TwelveDataException.class,
            () -> {
              endpoint.asObject();
            });
    // Verify exception details
    assertNotNull(exception.getMessage());
  }
}
