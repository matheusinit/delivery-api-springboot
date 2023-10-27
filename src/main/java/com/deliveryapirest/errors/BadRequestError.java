package com.deliveryapirest.errors;

public class BadRequestError {
  public String message;

  private BadRequestError(String message) {
    this.message = message;
  }

  public static BadRequestError make(String message) {
    return new BadRequestError(message);
  }
}
