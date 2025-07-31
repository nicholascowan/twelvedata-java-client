package com.github.nicholascowan.twelvedata.http;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.exceptions.BadRequestException;
import com.github.nicholascowan.twelvedata.exceptions.InvalidApiKeyException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests for the DefaultHttpClient. */
public class DefaultHttpClientTest {

  private MockWebServer mockWebServer;
  private DefaultHttpClient httpClient;

  @BeforeEach
  void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    httpClient = new DefaultHttpClient(mockWebServer.url("/").toString());
  }

  @AfterEach
  void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  void testSuccessfulGet() throws Exception {
    String expectedResponse = "{\"status\":\"ok\",\"data\":\"test\"}";
    mockWebServer.enqueue(
        new MockResponse().setBody(expectedResponse).addHeader("Content-Type", "application/json"));

    Map<String, String> params = new HashMap<>();
    params.put("symbol", "AAPL");

    String response = httpClient.get("/test", params);
    assertEquals(expectedResponse, response);
  }

  @Test
  void testBadRequestError() {
    mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody("Bad Request"));

    Map<String, String> params = new HashMap<>();

    assertThrows(
        BadRequestException.class,
        () -> {
          httpClient.get("/test", params);
        });
  }

  @Test
  void testInvalidApiKeyError() {
    mockWebServer.enqueue(new MockResponse().setResponseCode(401).setBody("Invalid API Key"));

    Map<String, String> params = new HashMap<>();

    assertThrows(
        InvalidApiKeyException.class,
        () -> {
          httpClient.get("/test", params);
        });
  }

  @Test
  void testJsonErrorResponse() {
    String errorResponse = "{\"status\":\"error\",\"code\":400,\"message\":\"Invalid symbol\"}";
    mockWebServer.enqueue(
        new MockResponse().setBody(errorResponse).addHeader("Content-Type", "application/json"));

    Map<String, String> params = new HashMap<>();

    assertThrows(
        BadRequestException.class,
        () -> {
          httpClient.get("/test", params);
        });
  }

  @Test
  void testCsvResponse() throws Exception {
    String csvResponse = "datetime,open,high,low,close,volume\n2023-01-01,100,101,99,100.5,1000";
    mockWebServer.enqueue(
        new MockResponse().setBody(csvResponse).addHeader("Content-Type", "text/csv"));

    Map<String, String> params = new HashMap<>();
    params.put("format", "CSV");

    String response = httpClient.getCsv("/test", params);
    assertEquals(csvResponse, response);
  }
}
