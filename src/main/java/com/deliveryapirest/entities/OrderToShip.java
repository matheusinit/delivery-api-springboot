package com.deliveryapirest.entities;

import java.util.UUID;

public class OrderToShip {
  private UUID id;

  public OrderToShip() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return id;
  }
}
