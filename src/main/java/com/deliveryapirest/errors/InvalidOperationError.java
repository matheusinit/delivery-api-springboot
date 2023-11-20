package com.deliveryapirest.errors;

public class InvalidOperationError extends Exception {
  public String message;

  public InvalidOperationError(String message) {
    super(message);
    this.message = message;
  }

  public static InvalidOperationError make(String message) {
    return new InvalidOperationError(message);
  }
}
