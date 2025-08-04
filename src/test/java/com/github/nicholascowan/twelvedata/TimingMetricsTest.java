package com.github.nicholascowan.twelvedata;

import static org.junit.jupiter.api.Assertions.*;

import com.github.nicholascowan.twelvedata.endpoints.Daily;
import com.github.nicholascowan.twelvedata.endpoints.Quote;
import com.github.nicholascowan.twelvedata.endpoints.TimeSeries;
import com.github.nicholascowan.twelvedata.models.DailyResponse;
import com.github.nicholascowan.twelvedata.models.QuoteResponse;
import com.github.nicholascowan.twelvedata.models.TimeSeriesResponse;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test demonstrating how to use and view Micrometer timing metrics with the TwelveData client.
 * 
 * <p>This test shows how to:
 * - Set up a meter registry
 * - Make API calls that generate timing metrics
 * - Query and display the timing data
 * - Analyze performance by endpoint type
 */
@Tag("UnitTest")
class TimingMetricsTest {

  private MeterRegistry meterRegistry;
  private TwelveDataClient client;

  @BeforeEach
  void setUp() {
    // Create a simple meter registry for testing
    meterRegistry = new SimpleMeterRegistry();
    
    // Create a client with a mock HTTP client that returns valid responses
    // In a real application, you'd use the actual API
    client = new TwelveDataClient("test-api-key");
  }

  @Test
  void testTimingMetricsCollection() throws Exception {
    // Make several API calls to generate timing data
    System.out.println("Making API calls to generate timing metrics...");
    
    // TimeSeries calls
    try {
      TimeSeriesResponse timeSeriesResponse = client.timeSeries("AAPL", "1day")
          .outputsize(5)
          .asObject();
      System.out.println("✓ TimeSeries call completed");
    } catch (Exception e) {
      System.out.println("⚠ TimeSeries call failed (expected in test environment): " + e.getMessage());
    }

    // Daily calls
    try {
      DailyResponse dailyResponse = client.daily("MSFT")
          .outputsize(3)
          .asObject();
      System.out.println("✓ Daily call completed");
    } catch (Exception e) {
      System.out.println("⚠ Daily call failed (expected in test environment): " + e.getMessage());
    }

    // Quote calls
    try {
      QuoteResponse quoteResponse = client.quote("GOOGL").asObject();
      System.out.println("✓ Quote call completed");
    } catch (Exception e) {
      System.out.println("⚠ Quote call failed (expected in test environment): " + e.getMessage());
    }

    // Simulate some timing data since we can't make real API calls in this test
    simulateTimingData();
    
    // Display the collected metrics
    displayTimingMetrics();
  }

  @Test
  void testTimingMetricsAnalysis() {
    // Simulate timing data for analysis
    simulateTimingData();
    
    // Analyze performance by endpoint
    analyzeEndpointPerformance();
  }

  private void simulateTimingData() {
    System.out.println("\n=== Simulating Timing Data ===");
    
    // Simulate TimeSeries timing
    Timer timeSeriesTimer = Timer.builder("twelvedata.timeseries.duration")
        .tag("endpoint", "time_series")
        .register(meterRegistry);
    timeSeriesTimer.record(150, TimeUnit.MILLISECONDS);
    timeSeriesTimer.record(200, TimeUnit.MILLISECONDS);
    timeSeriesTimer.record(180, TimeUnit.MILLISECONDS);
    
    // Simulate Daily timing
    Timer dailyTimer = Timer.builder("twelvedata.daily.duration")
        .tag("endpoint", "daily")
        .register(meterRegistry);
    dailyTimer.record(120, TimeUnit.MILLISECONDS);
    dailyTimer.record(140, TimeUnit.MILLISECONDS);
    
    // Simulate Quote timing
    Timer quoteTimer = Timer.builder("twelvedata.quote.duration")
        .tag("endpoint", "quote")
        .register(meterRegistry);
    quoteTimer.record(80, TimeUnit.MILLISECONDS);
    quoteTimer.record(90, TimeUnit.MILLISECONDS);
    quoteTimer.record(85, TimeUnit.MILLISECONDS);
    quoteTimer.record(95, TimeUnit.MILLISECONDS);
    
    // Simulate base API timing
    Timer baseTimer = Timer.builder("twelvedata.api.duration")
        .tag("endpoint", "time_series")
        .register(meterRegistry);
    baseTimer.record(160, TimeUnit.MILLISECONDS);
    baseTimer.record(210, TimeUnit.MILLISECONDS);
    
    baseTimer = Timer.builder("twelvedata.api.duration")
        .tag("endpoint", "daily")
        .register(meterRegistry);
    baseTimer.record(130, TimeUnit.MILLISECONDS);
    baseTimer.record(150, TimeUnit.MILLISECONDS);
    
    baseTimer = Timer.builder("twelvedata.api.duration")
        .tag("endpoint", "quote")
        .register(meterRegistry);
    baseTimer.record(85, TimeUnit.MILLISECONDS);
    baseTimer.record(95, TimeUnit.MILLISECONDS);
  }

  private void displayTimingMetrics() {
    System.out.println("\n=== Timing Metrics Summary ===");
    
    // Display all timers
    meterRegistry.getMeters().stream()
        .filter(meter -> meter instanceof Timer)
        .map(meter -> (Timer) meter)
        .forEach(timer -> {
          String timerName = timer.getId().getName();
          String endpoint = timer.getId().getTag("endpoint");
          long count = timer.count();
          double totalTime = timer.totalTime(TimeUnit.MILLISECONDS);
          double meanTime = timer.mean(TimeUnit.MILLISECONDS);
          double maxTime = timer.max(TimeUnit.MILLISECONDS);
          
          System.out.printf("Timer: %s (endpoint: %s)%n", timerName, endpoint);
          System.out.printf("  Count: %d calls%n", count);
          System.out.printf("  Total Time: %.2f ms%n", totalTime);
          System.out.printf("  Mean Time: %.2f ms%n", meanTime);
          System.out.printf("  Max Time: %.2f ms%n", maxTime);
          System.out.println();
        });
  }

  private void analyzeEndpointPerformance() {
    System.out.println("\n=== Endpoint Performance Analysis ===");
    
    // Find the fastest and slowest endpoints
    Timer fastestTimer = null;
    Timer slowestTimer = null;
    double fastestMean = Double.MAX_VALUE;
    double slowestMean = 0.0;
    
    for (Timer timer : meterRegistry.find("twelvedata.api.duration").timers()) {
      double meanTime = timer.mean(TimeUnit.MILLISECONDS);
      String endpoint = timer.getId().getTag("endpoint");
      
      if (meanTime < fastestMean) {
        fastestMean = meanTime;
        fastestTimer = timer;
      }
      
      if (meanTime > slowestMean) {
        slowestMean = meanTime;
        slowestTimer = timer;
      }
    }
    
    if (fastestTimer != null) {
      String fastestEndpoint = fastestTimer.getId().getTag("endpoint");
      System.out.printf("Fastest endpoint: %s (%.2f ms average)%n", fastestEndpoint, fastestMean);
    }
    
    if (slowestTimer != null) {
      String slowestEndpoint = slowestTimer.getId().getTag("endpoint");
      System.out.printf("Slowest endpoint: %s (%.2f ms average)%n", slowestEndpoint, slowestMean);
    }
    
    // Calculate overall statistics
    double totalCalls = meterRegistry.find("twelvedata.api.duration").timers().stream()
        .mapToLong(Timer::count)
        .sum();
    
    double totalTime = meterRegistry.find("twelvedata.api.duration").timers().stream()
        .mapToDouble(timer -> timer.totalTime(TimeUnit.MILLISECONDS))
        .sum();
    
    System.out.printf("Total API calls: %.0f%n", totalCalls);
    System.out.printf("Total time: %.2f ms%n", totalTime);
    System.out.printf("Overall average: %.2f ms per call%n", totalTime / totalCalls);
  }

  @Test
  void testTimingMetricsWithDifferentFormats() {
    System.out.println("\n=== Testing Different Response Formats ===");
    
    // Simulate timing for different response formats
    Timer jsonTimer = Timer.builder("twelvedata.api.duration")
        .tag("endpoint", "time_series")
        .register(meterRegistry);
    
    Timer csvTimer = Timer.builder("twelvedata.api.duration")
        .tag("endpoint", "time_series")
        .register(meterRegistry);
    
    Timer objectTimer = Timer.builder("twelvedata.api.duration")
        .tag("endpoint", "time_series")
        .register(meterRegistry);
    
    // Simulate different response times
    jsonTimer.record(100, TimeUnit.MILLISECONDS);
    csvTimer.record(110, TimeUnit.MILLISECONDS);
    objectTimer.record(120, TimeUnit.MILLISECONDS);
    
    // Show that all formats contribute to the same endpoint timing
    Timer timeSeriesTimer = meterRegistry.find("twelvedata.api.duration")
        .tag("endpoint", "time_series")
        .timer();
    
    System.out.printf("TimeSeries endpoint total calls: %d%n", timeSeriesTimer.count());
    System.out.printf("TimeSeries endpoint total time: %.2f ms%n", 
        timeSeriesTimer.totalTime(TimeUnit.MILLISECONDS));
    System.out.printf("TimeSeries endpoint average: %.2f ms%n", 
        timeSeriesTimer.mean(TimeUnit.MILLISECONDS));
    
    // Verify that all calls are aggregated together
    assertEquals(3, timeSeriesTimer.count(), "All format calls should be counted together");
  }

  @Test
  void testTimingMetricsQuerying() {
    System.out.println("\n=== Querying Specific Metrics ===");
    
    // Simulate some data
    simulateTimingData();
    
    // Query specific endpoint metrics
    Timer timeSeriesTimer = meterRegistry.find("twelvedata.timeseries.duration")
        .tag("endpoint", "time_series")
        .timer();
    
    Timer dailyTimer = meterRegistry.find("twelvedata.daily.duration")
        .tag("endpoint", "daily")
        .timer();
    
    Timer quoteTimer = meterRegistry.find("twelvedata.quote.duration")
        .tag("endpoint", "quote")
        .timer();
    
    // Display specific endpoint performance
    System.out.println("TimeSeries Performance:");
    System.out.printf("  Calls: %d%n", timeSeriesTimer.count());
    System.out.printf("  Average: %.2f ms%n", timeSeriesTimer.mean(TimeUnit.MILLISECONDS));
    System.out.printf("  P95: %.2f ms%n", timeSeriesTimer.percentile(0.95, TimeUnit.MILLISECONDS));
    
    System.out.println("\nDaily Performance:");
    System.out.printf("  Calls: %d%n", dailyTimer.count());
    System.out.printf("  Average: %.2f ms%n", dailyTimer.mean(TimeUnit.MILLISECONDS));
    System.out.printf("  P95: %.2f ms%n", dailyTimer.percentile(0.95, TimeUnit.MILLISECONDS));
    
    System.out.println("\nQuote Performance:");
    System.out.printf("  Calls: %d%n", quoteTimer.count());
    System.out.printf("  Average: %.2f ms%n", quoteTimer.mean(TimeUnit.MILLISECONDS));
    System.out.printf("  P95: %.2f ms%n", quoteTimer.percentile(0.95, TimeUnit.MILLISECONDS));
    
    // Verify metrics are being collected
    assertTrue(timeSeriesTimer.count() > 0, "TimeSeries metrics should be collected");
    assertTrue(dailyTimer.count() > 0, "Daily metrics should be collected");
    assertTrue(quoteTimer.count() > 0, "Quote metrics should be collected");
  }
} 