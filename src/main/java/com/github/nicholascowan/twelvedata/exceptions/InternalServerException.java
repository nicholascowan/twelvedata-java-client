package com.github.nicholascowan.twelvedata.exceptions;

/** Exception thrown when the API returns a 5xx Internal Server Error. */
public class InternalServerException extends TwelveDataException {

  public InternalServerException(String message, int errorCode) {
    super(message, errorCode);
  }

  public InternalServerException(String message, int errorCode, Throwable cause) {
    super(message, errorCode, cause);
  }
}
