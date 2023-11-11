package com.deliveryapirest.entities;

import java.time.Instant;

public class Product {
  private String name;
  private String description;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant deletedAt;

  public Product(String name) throws Exception {
    this(name, null);
  }

  public Product(String name, String description) throws Exception {

    if (name == null) {
      throw new Exception("Product name is required");
    }

    if (description != null && description == "") {
      throw new Exception("Description cannot be empty, must have at least 10 characters");
    }

    if (description != null && description.length() < 10) {
      throw new Exception("Description cannot have less than 10 characters");
    }

    this.name = name;
    this.description = description;
    this.createdAt = Instant.now();
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
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
