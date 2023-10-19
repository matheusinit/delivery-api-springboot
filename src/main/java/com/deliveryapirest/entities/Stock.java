package com.deliveryapirest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "stock")
public class Stock {

  @Id private UUID id;
  private UUID productId;
  private int quantity;
  @CreationTimestamp private String createdAt;

  @Column(insertable = false)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private String deletedAt;

  protected Stock() {}

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

  public String getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public String getDeletedAt() {
    return deletedAt;
  }
}
