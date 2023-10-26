package com.deliveryapirest.entities;

import com.deliveryapirest.data.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "orders_to_ship")
public class OrderToShip {
  @Id private UUID id;
  private UUID productId;
  private int quantity;
  private OrderStatus status;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant deletedAt;

  public OrderToShip() {
    this.id = UUID.randomUUID();
  }

  public OrderToShip(
      UUID id,
      UUID productId,
      int quantity,
      OrderStatus status,
      ZonedDateTime createdAt,
      Optional<ZonedDateTime> updatedAt,
      Optional<ZonedDateTime> deletedAt) {
    this.id = id;
    this.productId = productId;
    this.quantity = quantity;
    this.status = status;
    this.createdAt = createdAt.toInstant();
    var updatedAtWithNull = updatedAt.orElse(null);
    this.updatedAt = updatedAtWithNull == null ? null : updatedAtWithNull.toInstant();
    this.deletedAt = deletedAt.orElse(null) == null ? null : deletedAt.get().toInstant();
  }

  public OrderToShip(UUID productId, OrderStatus status, int quantity) {
    id = UUID.randomUUID();
    this.productId = productId;
    this.status = status;
    this.quantity = quantity;
    this.createdAt = Instant.now();
  }

  public OrderToShip(UUID productId, int quantity) {
    id = UUID.randomUUID();
    this.productId = productId;
    this.quantity = quantity;
    this.createdAt = Instant.now();
  }

  public UUID getId() {
    return id;
  }

  public UUID getProductId() {
    return productId;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public int getQuantity() {
    return quantity;
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
