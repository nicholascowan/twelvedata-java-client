package com.github.nicholascowan.twelvedata.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nicholascowan.twelvedata.exceptions.BadRequestException;
import com.github.nicholascowan.twelvedata.exceptions.ForbiddenException;
import com.github.nicholascowan.twelvedata.exceptions.InternalServerException;
import com.github.nicholascowan.twelvedata.exceptions.InvalidApiKeyException;
import com.github.nicholascowan.twelvedata.exceptions.NotFoundException;
import com.github.nicholascowan.twelvedata.exceptions.ParameterTooLongException;
import com.github.nicholascowan.twelvedata.exceptions.RateLimitException;
import com.github.nicholascowan.twelvedata.exceptions.ServerErrorException;
import com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default HTTP client implementation using OkHttp.
 *
 * <p>This implementation provides HTTP client functionality for the TwelveData API using the OkHttp
 * library. It handles JSON and CSV responses, error mapping, and request configuration.
 *
 * <p>Features:
 *
 * <ul>
 *   <li>Configurable timeouts for connect, read, and write operations
 *   <li>Automatic source parameter addition for monitoring
 *   <li>JSON response parsing and error handling
 *   <li>CSV response support
 *   <li>HTTP status code to exception mapping
 * </ul>
 *
 * <p>Default timeout is 30 seconds for all operations.
 */
public class DefaultHttpClient implements HttpClient {

  private static final Logger logger = LoggerFactory.getLogger(DefaultHttpClient.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private final String baseUrl;
  private final OkHttpClient client;

  /**
   * Creates a new HTTP client with the specified base URL and default timeout (30 seconds).
   *
   * @param baseUrl the base URL for the API (e.g., "https://api.twelvedata.com")
   * @throws IllegalArgumentException if baseUrl is null or empty
   */
  public DefaultHttpClient(String baseUrl) {
    this(baseUrl, 30000);
  }

  /**
   * Creates a new HTTP client with the specified base URL and timeout.
   *
   * @param baseUrl the base URL for the API (e.g., "https://api.twelvedata.com")
   * @param timeoutMs the timeout in milliseconds for connect, read, and write operations
   * @throws IllegalArgumentException if baseUrl is null or empty, or timeoutMs is negative
   */
  public DefaultHttpClient(String baseUrl, int timeoutMs) {
    this.baseUrl = baseUrl;
    this.client =
        new OkHttpClient.Builder()
            .connectTimeout(timeoutMs, TimeUnit.MILLISECONDS)
            .readTimeout(timeoutMs, TimeUnit.MILLISECONDS)
            .writeTimeout(timeoutMs, TimeUnit.MILLISECONDS)
            .build();
  }

  @Override
  public String get(String relativeUrl, Map<String, String> params) throws TwelveDataException {
    try {
      HttpUrl.Builder urlBuilder = HttpUrl.get(baseUrl + relativeUrl).newBuilder();

      // Add source parameter for monitoring
      urlBuilder.addQueryParameter("source", "java");

      // Add all parameters
      for (Map.Entry<String, String> entry : params.entrySet()) {
        if (entry.getValue() != null) {
          urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
      }

      Request request = new Request.Builder().url(urlBuilder.build()).get().build();

      logger.debug("Making GET request to: {}", request.url());

      try (Response response = client.newCall(request).execute()) {
        return handleResponse(response);
      }

    } catch (IOException e) {
      throw new TwelveDataException("HTTP request failed", e);
    }
  }

  @Override
  public String getCsv(String relativeUrl, Map<String, String> params) throws TwelveDataException {
    try {
      HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + relativeUrl).newBuilder();

      // Add source parameter for monitoring
      urlBuilder.addQueryParameter("source", "java");
      urlBuilder.addQueryParameter("format", "CSV");

      // Add all parameters
      for (Map.Entry<String, String> entry : params.entrySet()) {
        if (entry.getValue() != null) {
          urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
      }

      Request request = new Request.Builder().url(urlBuilder.build()).get().build();

      logger.debug("Making CSV GET request to: {}", request.url());

      try (Response response = client.newCall(request).execute()) {
        if (!response.isSuccessful()) {
          throw createException(response.code(), response.body().string());
        }
        return response.body().string();
      }

    } catch (IOException e) {
      throw new TwelveDataException("HTTP request failed", e);
    }
  }

  private String handleResponse(Response response) throws IOException, TwelveDataException {
    String responseBody = response.body().string();

    // Check if it's a batch response or CSV
    String contentType = response.header("Content-Type");
    String isBatch = response.header("Is_batch");

    if ("true".equals(isBatch) || (contentType != null && contentType.contains("text/csv"))) {
      if (!response.isSuccessful()) {
        throw createException(response.code(), responseBody);
      }
      return responseBody;
    }

    if (!response.isSuccessful()) {
      throw createException(response.code(), responseBody);
    }

    // Parse JSON response
    try {
      JsonNode jsonNode = objectMapper.readTree(responseBody);

      // Check if response has status field
      if (jsonNode.has("status")) {
        String status = jsonNode.get("status").asText();
        if ("error".equals(status)) {
          int errorCode = jsonNode.has("code") ? jsonNode.get("code").asInt() : 0;
          String message =
              jsonNode.has("message") ? jsonNode.get("message").asText() : responseBody;
          throw createException(errorCode, message);
        }
      }

      return responseBody;

    } catch (IOException e) {
      // If JSON parsing fails, return the raw response
      return responseBody;
    }
  }

  private TwelveDataException createException(int errorCode, String message) {
    if (errorCode == 400) {
      return new BadRequestException(message);
    } else if (errorCode == 401) {
      return new InvalidApiKeyException(message);
    } else if (errorCode == 403) {
      return new ForbiddenException(message);
    } else if (errorCode == 404) {
      return new NotFoundException(message);
    } else if (errorCode == 414) {
      return new ParameterTooLongException(message);
    } else if (errorCode == 429) {
      return new RateLimitException(message);
    } else if (errorCode == 500) {
      return new InternalServerException(message, errorCode);
    } else if (errorCode == 502) {
      return new ServerErrorException(message, errorCode);
    } else if (errorCode >= 500) {
      return new InternalServerException(message, errorCode);
    } else {
      return new TwelveDataException(message, errorCode);
    }
  }
}
