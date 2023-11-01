package com.deliveryapirest.errors;

public class InternalServerError {
  public String message;

  public InternalServerError(String message) {
    this.message = message;
  }
}
