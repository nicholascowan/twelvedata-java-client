package com.github.nicholascowan.twelvedata.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nicholascowan.twelvedata.exceptions.BadRequestException;
import com.github.nicholascowan.twelvedata.exceptions.ForbiddenException;
import com.github.nicholascowan.twelvedata.exceptions.NotFoundException;
import com.github.nicholascowan.twelvedata.exceptions.ParameterTooLongException;
import com.github.nicholascowan.twelvedata.exceptions.RateLimitException;
import com.github.nicholascowan.twelvedata.exceptions.ServerErrorException;
import com.github.nicholascowan.twelvedata.exceptions.TwelveDataException;
import com.github.nicholascowan.twelvedata.exceptions.UnauthorizedException;
import java.util.List;

/** Utility class for converting JSON responses to model objects. */
public class ModelUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /** Check if the JSON response contains an error and throw appropriate exception. */
  private static void checkForError(JsonNode jsonNode) {
    if (jsonNode.has("status")) {
      String status = jsonNode.get("status").asText();
      if ("error".equals(status)) {
        Integer code = null;
        String message = "Unknown error";

        if (jsonNode.has("code")) {
          code = jsonNode.get("code").asInt();
        }

        if (jsonNode.has("message")) {
          message = jsonNode.get("message").asText();
        }

        // Throw specific exceptions based on error code
        if (code != null) {
          switch (code) {
            case 400:
              throw new BadRequestException(message);
            case 401:
              throw new UnauthorizedException(message);
            case 403:
              throw new ForbiddenException(message);
            case 404:
              throw new NotFoundException(message);
            case 414:
              throw new ParameterTooLongException(message);
            case 429:
              throw new RateLimitException(message);
            case 500:
              throw new ServerErrorException(message, code);
            default:
              if (code >= 500) {
                throw new ServerErrorException(message, code);
              }
              throw new TwelveDataException(message, code);
          }
        }

        throw new TwelveDataException(message, 0);
      }
    }
  }

  /** Convert JsonNode to TimeSeriesResponse. */
  public static TimeSeriesResponse toTimeSeriesResponse(JsonNode jsonNode) {
    if (jsonNode == null) {
      return null;
    }

    // Check for error response first
    checkForError(jsonNode);

    TimeSeriesResponse response = new TimeSeriesResponse();

    if (jsonNode.has("status")) {
      response.setStatus(jsonNode.get("status").asText());
    }

    if (jsonNode.has("meta")) {
      JsonNode metaNode = jsonNode.get("meta");
      TimeSeriesMeta meta = new TimeSeriesMeta();

      if (metaNode.has("symbol")) {
        meta.setSymbol(metaNode.get("symbol").asText());
      }
      if (metaNode.has("interval")) {
        meta.setInterval(metaNode.get("interval").asText());
      }
      if (metaNode.has("currency")) {
        meta.setCurrency(metaNode.get("currency").asText());
      }
      if (metaNode.has("exchange_timezone")) {
        meta.setExchangeTimezone(metaNode.get("exchange_timezone").asText());
      }
      if (metaNode.has("exchange")) {
        meta.setExchange(metaNode.get("exchange").asText());
      }
      if (metaNode.has("mic_code")) {
        meta.setMicCode(metaNode.get("mic_code").asText());
      }
      if (metaNode.has("type")) {
        meta.setType(metaNode.get("type").asText());
      }

      response.setMeta(meta);
    }

    if (jsonNode.has("values") && jsonNode.get("values").isArray()) {
      try {
        List<TimeSeriesValue> values =
            objectMapper.convertValue(
                jsonNode.get("values"), new TypeReference<List<TimeSeriesValue>>() {});
        response.setValues(values);
      } catch (Exception e) {
        // Fallback to manual parsing if automatic conversion fails
        response.setValues(parseTimeSeriesValues(jsonNode.get("values")));
      }
    }

    return response;
  }

  /** Convert JsonNode to QuoteResponse. */
  public static QuoteResponse toQuoteResponse(JsonNode jsonNode) {
    if (jsonNode == null) {
      return null;
    }

    // Check for error response first
    checkForError(jsonNode);

    QuoteResponse response = new QuoteResponse();

    // Basic fields
    if (jsonNode.has("symbol")) {
      response.setSymbol(jsonNode.get("symbol").asText());
    }
    if (jsonNode.has("name")) {
      response.setName(jsonNode.get("name").asText());
    }
    if (jsonNode.has("exchange")) {
      response.setExchange(jsonNode.get("exchange").asText());
    }
    if (jsonNode.has("mic_code")) {
      response.setMicCode(jsonNode.get("mic_code").asText());
    }
    if (jsonNode.has("currency")) {
      response.setCurrency(jsonNode.get("currency").asText());
    }
    if (jsonNode.has("datetime")) {
      response.setDatetime(jsonNode.get("datetime").asText());
    }
    if (jsonNode.has("timestamp")) {
      response.setTimestamp(jsonNode.get("timestamp").asText());
    }
    if (jsonNode.has("last_quote_at")) {
      response.setLastQuoteAt(jsonNode.get("last_quote_at").asText());
    }

    // Price fields
    if (jsonNode.has("open")) {
      response.setOpen(jsonNode.get("open").asText());
    }
    if (jsonNode.has("high")) {
      response.setHigh(jsonNode.get("high").asText());
    }
    if (jsonNode.has("low")) {
      response.setLow(jsonNode.get("low").asText());
    }
    if (jsonNode.has("close")) {
      response.setClose(jsonNode.get("close").asText());
    }
    if (jsonNode.has("volume")) {
      response.setVolume(jsonNode.get("volume").asText());
    }
    if (jsonNode.has("previous_close")) {
      response.setPreviousClose(jsonNode.get("previous_close").asText());
    }
    if (jsonNode.has("change")) {
      response.setChange(jsonNode.get("change").asText());
    }
    if (jsonNode.has("percent_change")) {
      response.setPercentChange(jsonNode.get("percent_change").asText());
    }
    if (jsonNode.has("average_volume")) {
      response.setAverageVolume(jsonNode.get("average_volume").asText());
    }

    // Boolean field
    if (jsonNode.has("is_market_open")) {
      response.setIsMarketOpen(jsonNode.get("is_market_open").asBoolean());
    }

    // 52-week data
    if (jsonNode.has("fifty_two_week")) {
      JsonNode fiftyTwoWeekNode = jsonNode.get("fifty_two_week");
      FiftyTwoWeek fiftyTwoWeek = new FiftyTwoWeek();

      if (fiftyTwoWeekNode.has("low")) {
        fiftyTwoWeek.setLow(fiftyTwoWeekNode.get("low").asText());
      }
      if (fiftyTwoWeekNode.has("high")) {
        fiftyTwoWeek.setHigh(fiftyTwoWeekNode.get("high").asText());
      }
      if (fiftyTwoWeekNode.has("low_change")) {
        fiftyTwoWeek.setLowChange(fiftyTwoWeekNode.get("low_change").asText());
      }
      if (fiftyTwoWeekNode.has("high_change")) {
        fiftyTwoWeek.setHighChange(fiftyTwoWeekNode.get("high_change").asText());
      }
      if (fiftyTwoWeekNode.has("low_change_percent")) {
        fiftyTwoWeek.setLowChangePercent(fiftyTwoWeekNode.get("low_change_percent").asText());
      }
      if (fiftyTwoWeekNode.has("high_change_percent")) {
        fiftyTwoWeek.setHighChangePercent(fiftyTwoWeekNode.get("high_change_percent").asText());
      }
      if (fiftyTwoWeekNode.has("range")) {
        fiftyTwoWeek.setRange(fiftyTwoWeekNode.get("range").asText());
      }

      response.setFiftyTwoWeek(fiftyTwoWeek);
    }

    return response;
  }

  /** Convert JsonNode to PriceResponse. */
  public static PriceResponse toPriceResponse(JsonNode jsonNode) {
    if (jsonNode == null) {
      return null;
    }

    // Check for error response first
    checkForError(jsonNode);

    PriceResponse response = new PriceResponse();

    if (jsonNode.has("price")) {
      response.setPrice(jsonNode.get("price").asText());
    }

    return response;
  }

  /** Convert JsonNode to ErrorResponse. */
  public static ErrorResponse toErrorResponse(JsonNode jsonNode) {
    if (jsonNode == null) {
      return null;
    }

    ErrorResponse response = new ErrorResponse();

    if (jsonNode.has("status")) {
      response.setStatus(jsonNode.get("status").asText());
    }

    if (jsonNode.has("code")) {
      response.setCode(jsonNode.get("code").asInt());
    }

    if (jsonNode.has("message")) {
      response.setMessage(jsonNode.get("message").asText());
    }

    return response;
  }

  /** Manual parsing of time series values as fallback. */
  private static List<TimeSeriesValue> parseTimeSeriesValues(JsonNode valuesNode) {
    List<TimeSeriesValue> values = new java.util.ArrayList<>();

    for (JsonNode valueNode : valuesNode) {
      TimeSeriesValue value = new TimeSeriesValue();

      if (valueNode.has("datetime")) {
        value.setDatetime(valueNode.get("datetime").asText());
      }
      if (valueNode.has("open")) {
        value.setOpen(valueNode.get("open").asText());
      }
      if (valueNode.has("high")) {
        value.setHigh(valueNode.get("high").asText());
      }
      if (valueNode.has("low")) {
        value.setLow(valueNode.get("low").asText());
      }
      if (valueNode.has("close")) {
        value.setClose(valueNode.get("close").asText());
      }
      if (valueNode.has("volume")) {
        value.setVolume(valueNode.get("volume").asText());
      }

      values.add(value);
    }

    return values;
  }
}
