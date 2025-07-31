package com.github.nicholascowan.twelvedata.endpoints;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nicholascowan.twelvedata.TwelveDataContext;
import com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
import com.github.nicholascowan.twelvedata.models.ErrorResponse;
import com.github.nicholascowan.twelvedata.models.ModelUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for all API endpoints.
 *
 * <p>This abstract class provides common functionality for all TwelveData API endpoints, including
 * parameter management, HTTP request execution, and response handling.
 *
 * <p>Key features:
 *
 * <ul>
 *   <li>Parameter management with type-safe addParam methods
 *   <li>JSON and CSV response handling
 *   <li>URL generation for debugging
 *   <li>Endpoint type classification (price, indicator, overlay, batch)
 * </ul>
 *
 * <p>Subclasses should implement {@link #getEndpointName()} to specify the API endpoint path.
 */
public abstract class Endpoint {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  protected final TwelveDataContext context;
  protected final Map<String, String> params;

  // Flags for endpoint behavior
  protected boolean isPrice = false;
  protected boolean isIndicator = false;
  protected boolean isOverlay = false;
  protected boolean isBatch = false;

  /**
   * Creates a new endpoint with the specified context.
   *
   * @param context the TwelveData context containing configuration and defaults
   * @throws IllegalArgumentException if context is null
   */
  public Endpoint(TwelveDataContext context) {
    this.context = context;
    this.params = new HashMap<>(context.getAllParams());
  }

  /**
   * Gets the endpoint name for the API request.
   *
   * <p>This method should return the endpoint path (without leading slash) that will be appended to
   * the base URL to form the complete API endpoint.
   *
   * @return the endpoint name (e.g., "time_series", "quote", "price")
   */
  protected abstract String getEndpointName();

  /**
   * Executes the endpoint request and returns JSON response.
   *
   * <p>This method makes an HTTP GET request to the API endpoint with the current parameters and
   * returns the response as a Jackson JsonNode object.
   *
   * @return the API response as a JsonNode
   * @throws TwelveDataException if the API request fails or returns an error
   */
  public JsonNode asJson() throws TwelveDataException {
    try {
      String response = context.getHttpClient().get("/" + getEndpointName(), params);
      return objectMapper.readTree(response);
    } catch (TwelveDataException e) {
      // Re-throw TwelveDataException subclasses directly
      throw e;
    } catch (Exception e) {
      throw new TwelveDataException("Failed to execute " + getEndpointName() + " endpoint", e);
    }
  }

  /**
   * Executes the endpoint request and returns CSV response.
   *
   * <p>This method makes an HTTP GET request to the API endpoint with the current parameters and
   * returns the response as a CSV-formatted string.
   *
   * @return the API response as a CSV string
   * @throws TwelveDataException if the API request fails or returns an error
   */
  public String asCsv() throws TwelveDataException {
    try {
      return context.getHttpClient().getCsv("/" + getEndpointName(), params);
    } catch (TwelveDataException e) {
      // Re-throw TwelveDataException subclasses directly
      throw e;
    } catch (Exception e) {
      throw new TwelveDataException("Failed to execute " + getEndpointName() + " endpoint", e);
    }
  }

  /**
   * Executes the endpoint request and returns error response if applicable.
   *
   * <p>This method makes an HTTP GET request to the API endpoint and returns an ErrorResponse
   * object if the API returns an error status.
   *
   * @return the API error response, or null if no error
   * @throws TwelveDataException if the HTTP request fails
   */
  public ErrorResponse asErrorResponse() throws TwelveDataException {
    try {
      String response = context.getHttpClient().get("/" + getEndpointName(), params);
      JsonNode jsonNode = objectMapper.readTree(response);

      if (jsonNode.has("status")) {
        String status = jsonNode.get("status").asText();
        if ("error".equals(status)) {
          return ModelUtils.toErrorResponse(jsonNode);
        }
      }

      return null;
    } catch (TwelveDataException e) {
      // Re-throw TwelveDataException subclasses directly
      throw e;
    } catch (Exception e) {
      throw new TwelveDataException("Failed to execute " + getEndpointName() + " endpoint", e);
    }
  }

  /**
   * Returns the URL that would be used for this request.
   *
   * <p>This method constructs the complete URL that would be used for the API request, including
   * all parameters. This is useful for debugging and understanding what request will be made.
   *
   * @return the complete URL with all parameters
   */
  public String asUrl() {
    StringBuilder url = new StringBuilder(context.getBaseUrl() + "/" + getEndpointName() + "?");
    boolean first = true;
    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (!first) {
        url.append("&");
      }
      url.append(entry.getKey()).append("=").append(entry.getValue());
      first = false;
    }
    return url.toString();
  }

  /**
   * Adds a string parameter to the request.
   *
   * @param key the parameter name
   * @param value the parameter value (null values are ignored)
   */
  protected void addParam(String key, String value) {
    if (value != null) {
      params.put(key, value);
    }
  }

  /**
   * Adds an integer parameter to the request.
   *
   * @param key the parameter name
   * @param value the parameter value (null values are ignored)
   */
  protected void addParam(String key, Integer value) {
    if (value != null) {
      params.put(key, value.toString());
    }
  }

  /**
   * Adds a double parameter to the request.
   *
   * @param key the parameter name
   * @param value the parameter value (null values are ignored)
   */
  protected void addParam(String key, Double value) {
    if (value != null) {
      params.put(key, value.toString());
    }
  }

  /**
   * Adds a boolean parameter to the request.
   *
   * @param key the parameter name
   * @param value the parameter value (null values are ignored)
   */
  protected void addParam(String key, Boolean value) {
    if (value != null) {
      params.put(key, value.toString());
    }
  }

  /**
   * Checks if this endpoint is a price endpoint.
   *
   * @return true if this is a price endpoint, false otherwise
   */
  public boolean isPrice() {
    return isPrice;
  }

  /**
   * Checks if this endpoint is an indicator endpoint.
   *
   * @return true if this is an indicator endpoint, false otherwise
   */
  public boolean isIndicator() {
    return isIndicator;
  }

  /**
   * Checks if this endpoint is an overlay endpoint.
   *
   * @return true if this is an overlay endpoint, false otherwise
   */
  public boolean isOverlay() {
    return isOverlay;
  }

  /**
   * Checks if this endpoint is a batch endpoint.
   *
   * @return true if this is a batch endpoint, false otherwise
   */
  public boolean isBatch() {
    return isBatch;
  }
}
