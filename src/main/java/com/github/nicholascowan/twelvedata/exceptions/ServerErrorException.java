package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a 500 Internal Server Error.
 *
 * <p>This indicates that a server-side issue occurred. Retry the request later or contact support
 * for assistance.
 */
public class ServerErrorException extends TwelveDataException {

  public ServerErrorException(String message, int errorCode) {
    super(message, errorCode);
  }

  public ServerErrorException(String message, int errorCode, Throwable cause) {
    super(message, errorCode, cause);
  }
}
