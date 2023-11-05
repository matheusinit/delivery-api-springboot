package com.deliveryapirest.data;

public enum OrderStatus {
  NOT_SENT,
  CANCELLED,
  OUT_FOR_DELIVERY,
  IN_DELIVERY,
  DELIVERED;

  public static OrderStatus fromInt(int integer) {
    return values()[integer];
  }
}
