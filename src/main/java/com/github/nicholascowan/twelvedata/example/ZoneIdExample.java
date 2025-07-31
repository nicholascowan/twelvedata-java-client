package com.github.nicholascowan.twelvedata.example;

import com.github.nicholascowan.twelvedata.TwelveDataClient;
import com.github.nicholascowan.twelvedata.config.TwelveDataConfig;
import java.time.ZoneId;

/**
 * Example demonstrating the new ZoneId timezone functionality.
 * 
 * <p>This example shows how to use ZoneId objects instead of string timezones
 * for better type safety and IDE support.</p>
 */
public class ZoneIdExample {

  public static void main(String[] args) {
    // Create client with demo API key
    TwelveDataConfig config = new TwelveDataConfig();
    config.getApi().setKey("demo"); // Free tier demo key
    TwelveDataClient client = new TwelveDataClient(config);

    System.out.println("=== ZoneId Timezone Example ===\n");

    // Example 1: Using ZoneId.of() for specific timezones
    System.out.println("1. Using specific ZoneId timezones:");
    
    ZoneId[] timezones = {
      ZoneId.of("America/New_York"),
      ZoneId.of("Europe/London"),
      ZoneId.of("Asia/Tokyo"),
      ZoneId.of("Australia/Sydney"),
      ZoneId.of("UTC")
    };

    for (ZoneId zoneId : timezones) {
      System.out.println("   • " + zoneId.getId() + " (ZoneId: " + zoneId + ")");
      
      try {
        // Use ZoneId in time series request
        var response = client.timeSeries("AAPL", "1day")
            .timezone(zoneId)
            .outputsize(1)
            .asJson();
        
        System.out.println("     ✅ Success - Timezone applied: " + zoneId.getId());
      } catch (Exception e) {
        System.out.println("     ❌ Error: " + e.getMessage());
      }
    }

    // Example 2: Using system default timezone
    System.out.println("\n2. Using system default timezone:");
    ZoneId systemDefault = ZoneId.systemDefault();
    System.out.println("   System default: " + systemDefault.getId());
    
    try {
      var response = client.timeSeries("AAPL", "1day")
          .timezone(systemDefault)
          .outputsize(1)
          .asJson();
      System.out.println("   ✅ Success with system default timezone");
    } catch (Exception e) {
      System.out.println("   ❌ Error: " + e.getMessage());
    }

    // Example 3: Comparing string vs ZoneId methods
    System.out.println("\n3. Comparing string vs ZoneId methods:");
    String timezoneString = "America/New_York";
    ZoneId timezoneZoneId = ZoneId.of(timezoneString);
    
    System.out.println("   String method: client.timeSeries(\"AAPL\", \"1day\").timezone(\"" + timezoneString + "\")");
    System.out.println("   ZoneId method: client.timeSeries(\"AAPL\", \"1day\").timezone(" + timezoneZoneId + ")");
    System.out.println("   Both methods produce the same result!");

    // Example 4: Using ZoneId with Quote endpoint
    System.out.println("\n4. Using ZoneId with Quote endpoint:");
    try {
      var quoteResponse = client.quote("AAPL")
          .timezone(ZoneId.of("Europe/London"))
          .asJson();
      System.out.println("   ✅ Quote request with London timezone successful");
    } catch (Exception e) {
      System.out.println("   ❌ Quote error: " + e.getMessage());
    }

    // Example 5: Benefits of using ZoneId
    System.out.println("\n5. Benefits of using ZoneId:");
    System.out.println("   • Type safety - compile-time validation");
    System.out.println("   • IDE support - autocomplete and refactoring");
    System.out.println("   • Standard Java time API integration");
    System.out.println("   • Consistent with modern Java practices");
    System.out.println("   • Better error handling for invalid timezones");

    System.out.println("\n=== Example Complete ===");
  }
} 