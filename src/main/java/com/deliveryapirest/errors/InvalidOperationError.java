package com.deliveryapirest.errors;

public class InvalidOperationError extends Error {
  public String message;

  private InvalidOperationError(String message) {
    super(message);
    this.message = message;
  }

  public static InvalidOperationError make(String message) {
    return new InvalidOperationError(message);
  }
}
