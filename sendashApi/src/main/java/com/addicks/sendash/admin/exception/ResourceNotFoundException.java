package com.addicks.sendash.admin.exception;

/**
 * For HTTP 404 errros
 */
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 7531337978420041644L;

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }

}
