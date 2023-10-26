package com.deliveryapirest.data;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

public class Order {
  private UUID id;
  private UUID productId;
  private int quantity;
  private OrderStatus status;
  private ZonedDateTime createdAt;
  private Optional<ZonedDateTime> updatedAt;
  private Optional<ZonedDateTime> canceledAt;

  public Order(OrderStatus status, int quantity) {
    this.status = status;
    this.quantity = quantity;
    this.createdAt = ZonedDateTime.now();
    this.updatedAt = Optional.empty();
    this.canceledAt = Optional.empty();
  }

  public Order(Optional<ZonedDateTime> canceledAt) {
    this.canceledAt = canceledAt;
  }

  public Order(int quantity) {
    this.quantity = quantity;
  }

  public Order(UUID productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public Order(
      UUID id,
      UUID productId,
      int quantity,
      OrderStatus status,
      ZonedDateTime createdAt,
      Optional<ZonedDateTime> updatedAt,
      Optional<ZonedDateTime> canceledAt) {
    this.id = id;
    this.productId = productId;
    this.quantity = quantity;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.canceledAt = canceledAt;
  }

  public UUID getId() {
    return id;
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

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public Optional<ZonedDateTime> getUpdatedAt() {
    return updatedAt;
  }

  public Optional<ZonedDateTime> getCanceledAt() {
    return canceledAt;
  }
}
