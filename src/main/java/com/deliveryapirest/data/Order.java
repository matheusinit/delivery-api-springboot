package com.deliveryapirest.data;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Order {
  private UUID productId;
  private int quantity;
  private OrderStatus status;
  private ZonedDateTime deletedAt;

  public Order(OrderStatus status) {
    this.status = status;
  }

  public Order(ZonedDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public Order(UUID productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public UUID getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public ZonedDateTime getDeletedAt() {
    return deletedAt;
  }
}
