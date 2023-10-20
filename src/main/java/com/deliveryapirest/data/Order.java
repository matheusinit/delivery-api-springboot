package com.deliveryapirest.data;

import java.time.ZonedDateTime;

public class Order {
  private OrderStatus status;
  private ZonedDateTime deletedAt;

  public Order(OrderStatus status) {
    this.status = status;
  }

  public Order(ZonedDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public ZonedDateTime getDeletedAt() {
    return deletedAt;
  }
}
