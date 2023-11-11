package com.deliveryapirest.entities;

import java.time.Instant;

public class Product {
  private Instant createdAt;
  private Instant updatedAt;

  public Product() throws Exception {
    throw new Exception("Product name is required");
  }

  public Product(String name, String description) throws Exception {

    if (description == "") {
      throw new Exception("Description cannot be empty, must have at least 10 characters");
    }

    if (description.length() < 10) {
      throw new Exception("Description cannot have less than 10 characters");
    }

    this.createdAt = Instant.now();
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }
}
