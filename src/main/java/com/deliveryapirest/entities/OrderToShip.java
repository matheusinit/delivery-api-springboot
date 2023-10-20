package com.deliveryapirest.entities;

import java.util.UUID;

public class OrderToShip {
  private UUID id;
  private UUID productId;
  private int quantity;

  public OrderToShip() {
    this.id = UUID.randomUUID();
  }

  public OrderToShip(UUID productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
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
}
