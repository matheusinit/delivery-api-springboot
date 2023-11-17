package com.deliveryapirest.entities;

import com.deliveryapirest.errors.EmptyDescriptionError;
import com.deliveryapirest.errors.InvalidFieldError;
import com.deliveryapirest.errors.MissingFieldError;
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
      throw new MissingFieldError("Product name is required");
    }

    validateDescription(description);

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

  public void setName(String newValue) throws InvalidFieldError {
    if (newValue == "") {
      throw new InvalidFieldError("Name cannot be empty");
    }

    if (newValue == null) {
      throw new InvalidFieldError("Name cannot be null");
    }

    name = newValue;
    updatedAt = Instant.now();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String newValue) throws InvalidFieldError, EmptyDescriptionError {
    validateDescription(newValue);

    description = newValue;
    updatedAt = Instant.now();
  }

  private void validateDescription(String description)
      throws InvalidFieldError, EmptyDescriptionError {
    if (description != null && description == "") {
      throw new EmptyDescriptionError(
          "Description cannot be empty, must have at least 10 characters");
    }

    if (description != null && !description.isEmpty() && description.length() < 10) {
      throw new InvalidFieldError("Description cannot have less than 10 characters");
    }
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
