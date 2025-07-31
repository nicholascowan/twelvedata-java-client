package com.github.nicholascowan.twelvedata.exceptions;

/** Exception thrown when the API key is invalid (401 error). */
public class InvalidApiKeyException extends TwelveDataException {

  public InvalidApiKeyException(String message) {
    super(message, 401);
  }

  public InvalidApiKeyException(String message, Throwable cause) {
    super(message, 401, cause);
  }
}
