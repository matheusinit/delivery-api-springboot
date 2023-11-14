package com.deliveryapirest.errors;

public class EmptyDescriptionError extends Exception {
  public String message;

  public EmptyDescriptionError(String message) {
    super(message);
    this.message = message;
  }

  public static EmptyDescriptionError make(String message) {
    return new EmptyDescriptionError(message);
  }
}
