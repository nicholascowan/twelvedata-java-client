package com.github.nicholascowan.twelvedata.http;

import java.util.Map;

/**
 * HTTP client interface for making API requests to the TwelveData API.
 * 
 * <p>This interface defines the contract for HTTP clients that can make requests
 * to the TwelveData API. It supports both JSON and CSV response formats.</p>
 * 
 * <p>Implementations should handle:</p>
 * <ul>
 *   <li>HTTP GET requests with query parameters</li>
 *   <li>Response parsing and error handling</li>
 *   <li>Timeout and connection management</li>
 *   <li>Authentication via API key</li>
 * </ul>
 * 
 * @see DefaultHttpClient
 */
public interface HttpClient {
    
    /**
     * Makes a GET request to the specified URL with query parameters.
     * 
     * @param relativeUrl the relative URL path
     * @param params the query parameters
     * @return the response as a string
     * @throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException if the request fails
     */
    String get(String relativeUrl, Map<String, String> params) throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
    
    /**
     * Makes a GET request to the specified URL with query parameters and returns CSV response.
     * 
     * @param relativeUrl the relative URL path
     * @param params the query parameters
     * @return the response as a string (CSV format)
     * @throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException if the request fails
     */
    String getCsv(String relativeUrl, Map<String, String> params) throws com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
} 