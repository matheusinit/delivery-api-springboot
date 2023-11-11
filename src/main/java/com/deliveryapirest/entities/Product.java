package com.deliveryapirest.entities;

import java.time.Instant;

public class Product {
  private String name;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant deletedAt;

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

    this.name = name;

    this.createdAt = Instant.now();
  }

  public String getName() {
    return name;
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
