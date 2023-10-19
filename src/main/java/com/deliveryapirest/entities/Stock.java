package com.deliveryapirest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
  @UpdateTimestamp private String updatedAt;
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
}
