package com.github.nicholascowan.twelvedata;

import com.github.nicholascowan.twelvedata.config.TwelveDataConfig;
import com.github.nicholascowan.twelvedata.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Context class that holds the configuration and state for the TwelveData client.
 * 
 * <p>This class manages:</p>
 * <ul>
 *   <li>API authentication (API key)</li>
 *   <li>Base URL configuration</li>
 *   <li>HTTP client instance</li>
 *   <li>Default parameters for API requests</li>
 *   <li>Custom default parameters that can be overridden</li>
 * </ul>
 * 
 * <p>The context is used by all endpoint classes to make API requests with the
 * correct configuration and parameters.</p>
 */
public class TwelveDataContext {
    
    private final String apiKey;
    private final String baseUrl;
    private final HttpClient httpClient;
    private final Map<String, String> defaults;
    private final Map<String, String> customDefaults;
    
    /**
     * Creates a new context with the specified configuration.
     * 
     * @param apiKey the API key for authentication
     * @param baseUrl the base URL for the API
     * @param httpClient the HTTP client to use for requests
     * @param defaults the default parameters to use for API requests
     * @throws IllegalArgumentException if any parameter is null
     */
    public TwelveDataContext(String apiKey, String baseUrl, HttpClient httpClient, Map<String, String> defaults) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
        this.defaults = new HashMap<>(defaults);
        this.customDefaults = new HashMap<>();
    }
    
    /**
     * Creates a new context using Spring configuration.
     * 
     * @param apiKey the API key for authentication
     * @param config the Spring configuration object
     * @param httpClient the HTTP client to use for requests
     * @throws IllegalArgumentException if any parameter is null
     */
    public TwelveDataContext(String apiKey, TwelveDataConfig config, HttpClient httpClient) {
        this.apiKey = apiKey;
        this.baseUrl = config.getApi().getBaseUrl();
        this.httpClient = httpClient;
        this.defaults = config.getDefaultParams();
        this.customDefaults = new HashMap<>();
    }
    
    /**
     * Creates a new context by copying an existing one.
     * 
     * @param context the context to copy from
     * @return a new TwelveDataContext with the same configuration
     * @throws IllegalArgumentException if context is null
     */
    public static TwelveDataContext fromContext(TwelveDataContext context) {
        TwelveDataContext newContext = new TwelveDataContext(
                context.apiKey,
                context.baseUrl,
                context.httpClient,
                context.defaults
        );
        newContext.customDefaults.putAll(context.customDefaults);
        return newContext;
    }
    
    /**
     * Gets the API key used for authentication.
     * 
     * @return the API key
     */
    public String getApiKey() {
        return apiKey;
    }
    
    /**
     * Gets the base URL for the API.
     * 
     * @return the base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }
    
    /**
     * Gets the HTTP client used for making requests.
     * 
     * @return the HTTP client instance
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }
    
    /**
     * Gets the default parameters used for API requests.
     * 
     * @return a map of default parameter names to values
     */
    public Map<String, String> getDefaults() {
        return defaults;
    }
    
    /**
     * Gets the custom default parameters that override the standard defaults.
     * 
     * @return a map of custom parameter names to values
     */
    public Map<String, String> getCustomDefaults() {
        return customDefaults;
    }
    
    /**
     * Gets all parameters including defaults and custom defaults.
     * 
     * <p>This method combines the default parameters, custom defaults, and the API key
     * into a single map that can be used for API requests.</p>
     * 
     * @return a map containing all parameters including the API key
     */
    public Map<String, String> getAllParams() {
        Map<String, String> allParams = new HashMap<>(defaults);
        allParams.putAll(customDefaults);
        allParams.put("apikey", apiKey);
        return allParams;
    }
    
    /**
     * Updates custom defaults with the provided parameters.
     * 
     * <p>These custom defaults will override the standard defaults for all subsequent
     * API requests made through this context.</p>
     * 
     * @param newDefaults a map of parameter names to values to set as custom defaults
     * @throws IllegalArgumentException if newDefaults is null
     */
    public void updateDefaults(Map<String, String> newDefaults) {
        this.customDefaults.putAll(newDefaults);
    }
} 