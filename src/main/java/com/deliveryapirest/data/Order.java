package com.deliveryapirest.data;

enum Status {
  NOT_SENT,
  CANCELLED
}

public class Order {
  public final Status status = Status.CANCELLED;
}
