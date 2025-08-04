# Timing Metrics with Micrometer

The TwelveData Java client now includes built-in timing metrics using Micrometer. This allows you to monitor the performance of all API calls across different endpoints and response formats.

## Overview

The client automatically tracks timing for all HTTP client method calls:
- `asJson()` - JSON responses
- `asCsv()` - CSV responses  
- `asObject()` - Typed object responses

Each endpoint type has its own timing metrics, allowing you to distinguish between:
- TimeSeries API calls
- Daily API calls
- Quote API calls
- Price API calls
- EndOfDay API calls

All three methods (`asJson`, `asCsv`, `asObject`) are timed with the same metrics, focusing on the endpoint type rather than the response format.

## Metrics Structure

### Base Metrics
All API calls are tracked with the base metric:
```
twelvedata.api.duration
```

**Tags:**
- `endpoint`: The specific endpoint name (e.g., "time_series", "quote", "price")

### Endpoint-Specific Metrics
Each endpoint also has its own dedicated metric:

```
twelvedata.timeseries.duration
twelvedata.daily.duration
twelvedata.quote.duration
twelvedata.price.duration
twelvedata.endofday.duration
```

**Tags:**
- `endpoint`: The endpoint type

## Usage Examples

### Basic Setup

```java
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

// Create a meter registry (you can use any Micrometer registry)
MeterRegistry registry = new SimpleMeterRegistry();

// Your TwelveData client will automatically use the timing annotations
TwelveDataClient client = new TwelveDataClient("your-api-key");
```

### Monitoring Different Endpoints

```java
// These calls will be tracked separately
TimeSeriesResponse timeSeries = client.timeSeries("AAPL", "1day").asObject();
DailyResponse daily = client.daily("AAPL").asObject();
QuoteResponse quote = client.quote("AAPL").asObject();
PriceResponse price = client.price("AAPL").asObject();
EndOfDayResponse eod = client.endOfDay("AAPL").asObject();
```

### Monitoring Different Methods

```java
// These will all be tracked with the same endpoint timing
JsonNode json = client.timeSeries("AAPL", "1day").asJson();
String csv = client.timeSeries("AAPL", "1day").asCsv();
TimeSeriesResponse object = client.timeSeries("AAPL", "1day").asObject();
```

## Integration with Monitoring Systems

### Prometheus

```java
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
```

### InfluxDB

```java
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;

InfluxConfig config = new InfluxConfig() {
    @Override
    public String uri() {
        return "http://localhost:8086";
    }
    
    @Override
    public String get(String key) {
        return null;
    }
};

InfluxMeterRegistry influxRegistry = new InfluxMeterRegistry(config, Clock.SYSTEM);
```

### Spring Boot Actuator

If you're using Spring Boot, you can expose metrics via Actuator:

```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
```

## Querying Metrics

### Prometheus Queries

```promql
# Average response time for all API calls
avg(twelvedata_api_duration_seconds)

# Average response time by endpoint
avg by (endpoint) (twelvedata_api_duration_seconds)

# 95th percentile for TimeSeries calls
histogram_quantile(0.95, sum(rate(twelvedata_timeseries_duration_seconds_bucket[5m])) by (le))

# Error rate (if you add error tracking)
rate(twelvedata_api_duration_seconds_count{status="error"}[5m])
```

### Grafana Dashboards

You can create dashboards showing:
- Response time trends by endpoint
- Request volume by endpoint
- Error rates
- Performance percentiles (P50, P95, P99)

## Alerting

### Example Prometheus Alert Rules

```yaml
groups:
  - name: twelvedata-api
    rules:
      - alert: HighResponseTime
        expr: histogram_quantile(0.95, twelvedata_api_duration_seconds) > 5
        for: 5m
        labels:
          severity: warning
        annotations:
          summary: "High response time for TwelveData API"
          description: "95th percentile response time is {{ $value }}s"

      - alert: HighErrorRate
        expr: rate(twelvedata_api_duration_seconds_count{status="error"}[5m]) > 0.1
        for: 2m
        labels:
          severity: critical
        annotations:
          summary: "High error rate for TwelveData API"
          description: "Error rate is {{ $value }} errors per second"
```

## Performance Considerations

- **Minimal Overhead**: Micrometer timing adds negligible overhead to API calls
- **Memory Efficient**: Metrics are stored efficiently and don't accumulate indefinitely
- **Configurable**: You can configure retention, aggregation, and export settings

## Custom Metrics

You can extend the timing system by adding your own metrics:

```java
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

// Track successful vs failed calls
Counter successCounter = Counter.builder("twelvedata.api.calls")
    .tag("status", "success")
    .register(registry);

Counter errorCounter = Counter.builder("twelvedata.api.calls")
    .tag("status", "error")
    .register(registry);
```

## Troubleshooting

### No Metrics Appearing

1. Ensure Micrometer is properly configured
2. Check that your meter registry is being used
3. Verify that API calls are actually being made

### High Memory Usage

1. Configure appropriate retention policies
2. Use step-based aggregation for long-running applications
3. Consider using a dedicated metrics backend

### Integration Issues

1. Check Micrometer version compatibility
2. Ensure your monitoring system supports the metric format
3. Verify network connectivity to your metrics backend 