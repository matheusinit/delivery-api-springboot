package com.deliveryapirest.entities;

import com.deliveryapirest.errors.InvalidOperationError;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "stock")
public class Stock {

  @Id private UUID id;
  private UUID productId;
  private int quantity;
  @CreationTimestamp private Instant createdAt;

  @Column(insertable = false)
  @UpdateTimestamp
  private Instant updatedAt;

  private Instant deletedAt;

  protected Stock() {}

  public Stock(UUID productId) {
    this.productId = productId;
    this.quantity = 0;
    this.id = UUID.randomUUID();
    this.createdAt = Instant.now();
  }

  public Stock(UUID productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
    this.id = UUID.randomUUID();
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

  public void setQuantity(int quantity) throws InvalidOperationError {
    if (quantity < 0) {
      throw new InvalidOperationError("Quantity must be 0 or positive");
    }

    this.quantity = quantity;
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
