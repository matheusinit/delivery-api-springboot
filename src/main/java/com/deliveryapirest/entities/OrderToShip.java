package com.deliveryapirest.entities;

import java.util.UUID;

public class OrderToShip {
  private UUID id;
  private UUID productId;

  public OrderToShip() {
    this.id = UUID.randomUUID();
  }

  public OrderToShip(UUID productId) {
    this.productId = productId;
  }

  public UUID getId() {
    return id;
  }

  public UUID getProductId() {
    return productId;
  }
}
