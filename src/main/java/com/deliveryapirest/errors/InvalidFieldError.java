package com.deliveryapirest.errors;

public class InvalidFieldError extends Exception {
  public String message;

  public InvalidFieldError(String message) {
    super(message);
    this.message = message;
  }

  public static InvalidFieldError make(String message) {
    return new InvalidFieldError(message);
  }
}
