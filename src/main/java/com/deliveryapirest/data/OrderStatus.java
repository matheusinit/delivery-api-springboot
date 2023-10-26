package com.deliveryapirest.data;

public enum OrderStatus {
  NOT_SENT,
  CANCELLED;

  public static OrderStatus fromInt(int integer) {
    return values()[integer];
  }
}
