package com.github.nicholascowan.twelvedata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration properties for the TwelveData client.
 */
@Component
@ConfigurationProperties(prefix = "twelvedata")
public class TwelveDataConfig {
    
    private Api api = new Api();
    private Defaults defaults = new Defaults();
    private Indicators indicators = new Indicators();
    private WebSocket websocket = new WebSocket();
    
    public static class Api {
        private String baseUrl = "https://api.twelvedata.com";
        private int timeout = 30000;
        private String source = "java";
        
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
        
        public int getTimeout() { return timeout; }
        public void setTimeout(int timeout) { this.timeout = timeout; }
        
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
    }
    
    public static class Defaults {
        private int outputsize = 30;
        private String timezone = "Exchange";
        private String order = "desc";
        private String prepost = "false";
        private int dp = 5;
        
        public int getOutputsize() { return outputsize; }
        public void setOutputsize(int outputsize) { this.outputsize = outputsize; }
        
        public String getTimezone() { return timezone; }
        public void setTimezone(String timezone) { this.timezone = timezone; }
        
        public String getOrder() { return order; }
        public void setOrder(String order) { this.order = order; }
        
        public String getPrepost() { return prepost; }
        public void setPrepost(String prepost) { this.prepost = prepost; }
        
        public int getDp() { return dp; }
        public void setDp(int dp) { this.dp = dp; }
    }
    
    public static class Indicators {
        private int timePeriod = 14;
        private int fastPeriod = 12;
        private int slowPeriod = 26;
        private int signalPeriod = 9;
        private String maType = "SMA";
        private String seriesType = "close";
        
        public int getTimePeriod() { return timePeriod; }
        public void setTimePeriod(int timePeriod) { this.timePeriod = timePeriod; }
        
        public int getFastPeriod() { return fastPeriod; }
        public void setFastPeriod(int fastPeriod) { this.fastPeriod = fastPeriod; }
        
        public int getSlowPeriod() { return slowPeriod; }
        public void setSlowPeriod(int slowPeriod) { this.slowPeriod = slowPeriod; }
        
        public int getSignalPeriod() { return signalPeriod; }
        public void setSignalPeriod(int signalPeriod) { this.signalPeriod = signalPeriod; }
        
        public String getMaType() { return maType; }
        public void setMaType(String maType) { this.maType = maType; }
        
        public String getSeriesType() { return seriesType; }
        public void setSeriesType(String seriesType) { this.seriesType = seriesType; }
    }
    
    public static class WebSocket {
        private int maxQueueSize = 12000;
        private int heartbeatInterval = 10000;
        
        public int getMaxQueueSize() { return maxQueueSize; }
        public void setMaxQueueSize(int maxQueueSize) { this.maxQueueSize = maxQueueSize; }
        
        public int getHeartbeatInterval() { return heartbeatInterval; }
        public void setHeartbeatInterval(int heartbeatInterval) { this.heartbeatInterval = heartbeatInterval; }
    }
    
    public Api getApi() { return api; }
    public void setApi(Api api) { this.api = api; }
    
    public Defaults getDefaults() { return defaults; }
    public void setDefaults(Defaults defaults) { this.defaults = defaults; }
    
    public Indicators getIndicators() { return indicators; }
    public void setIndicators(Indicators indicators) { this.indicators = indicators; }
    
    public WebSocket getWebsocket() { return websocket; }
    public void setWebsocket(WebSocket websocket) { this.websocket = websocket; }
    
    /**
     * Gets all default parameters as a map.
     */
    public Map<String, String> getDefaultParams() {
        Map<String, String> params = new HashMap<>();
        params.put("outputsize", String.valueOf(defaults.getOutputsize()));
        params.put("timezone", defaults.getTimezone());
        params.put("order", defaults.getOrder());
        params.put("prepost", defaults.getPrepost());
        params.put("dp", String.valueOf(defaults.getDp()));
        return params;
    }
} 