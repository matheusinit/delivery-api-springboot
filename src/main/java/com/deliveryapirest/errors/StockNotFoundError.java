package com.deliveryapirest.errors;

public class StockNotFoundError {
  public String message;

  private StockNotFoundError(String message) {
    this.message = message;
  }

  public static StockNotFoundError returnError(String message) {
    return new StockNotFoundError(message);
  }
}
