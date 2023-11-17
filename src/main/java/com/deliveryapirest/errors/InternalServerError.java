package com.deliveryapirest.errors;

public class InternalServerError {
  public String message;

  private InternalServerError(String message) {
    this.message = message;
  }

  public static InternalServerError make(String message) {
    return new InternalServerError(message);
  }

  public String toString() {
    return message;
  }
}
