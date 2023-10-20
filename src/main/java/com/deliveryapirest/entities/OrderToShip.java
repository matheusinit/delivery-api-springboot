package com.deliveryapirest.entities;

import java.time.Instant;
import java.util.UUID;

public class OrderToShip {
  private UUID id;
  private UUID productId;
  private int quantity;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant deletedAt;

  public OrderToShip() {
    this.id = UUID.randomUUID();
  }

  public OrderToShip(UUID productId, int quantity) {
    id = UUID.randomUUID();
    this.productId = productId;
    this.quantity = quantity;
    this.createdAt = Instant.now();
  }

  public UUID getId() {
    return id;
  }

  public UUID getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Instant getDeletedAt() {
    return deletedAt;
  }
}
