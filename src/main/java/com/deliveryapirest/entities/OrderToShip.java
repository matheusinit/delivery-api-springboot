package com.deliveryapirest.entities;

import java.time.Instant;
import java.util.UUID;

public class OrderToShip {
  private UUID id;
  private UUID productId;
  private int quantity;
  private Instant createdAt;
  private Instant updatedAt;

  public OrderToShip() {
    this.id = UUID.randomUUID();
  }

  public OrderToShip(UUID productId, int quantity) {
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
    return this.createdAt;
  }

  public Instant getUpdatedAt() {
    return this.updatedAt;
  }
}
