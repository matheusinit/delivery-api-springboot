package com.deliveryapirest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "product")
public class Product {
  @Id private UUID id;
  private String name;
  private String description;
  private Instant createdAt;

  @Column(insertable = false)
  @UpdateTimestamp
  private Instant updatedAt;

  private Instant deletedAt;

  public Product() {}

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
    this.id = UUID.randomUUID();
    this.name = name;
    this.description = description;
    this.createdAt = Instant.now();
  }

  public UUID getId() {
    return id;
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
