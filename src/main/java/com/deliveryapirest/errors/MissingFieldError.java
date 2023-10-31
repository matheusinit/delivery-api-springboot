package com.deliveryapirest.errors;

public class MissingFieldError extends Exception {
  public String message;

  public MissingFieldError(String message) {
    super(message);
    this.message = message;
  }

  public static MissingFieldError make(String message) {
    return new MissingFieldError(message);
  }
}
