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
  private Instant canceledAt;

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
      Optional<ZonedDateTime> canceledAt) {
    this.id = id;
    this.productId = productId;
    this.quantity = quantity;
    this.status = status;
    this.createdAt = createdAt.toInstant();
    var updatedAtWithNull = updatedAt != null ? updatedAt.orElse(null) : null;
    this.updatedAt = updatedAtWithNull == null ? null : updatedAtWithNull.toInstant();
    var canceledAtWithNull = canceledAt != null ? canceledAt.orElse(null) : null;
    this.canceledAt = canceledAtWithNull == null ? null : canceledAt.get().toInstant();
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

  public Instant getCanceledAt() {
    return canceledAt;
  }

  public void setOutForDelivery() throws Exception {
    if (status == OrderStatus.CANCELLED) {
      throw new Exception("Order is cancelled. Cannot set it out for delivery!");
    }

    if (status == OrderStatus.OUT_FOR_DELIVERY) {
      throw new Exception(
          "Order is already out for delivery. Cannot set it out for delivery again!");
    }

    if (status == OrderStatus.IN_DELIVERY) {
      throw new Exception("Order is in delivery. Cannot set it out for delivery!");
    }

    status = OrderStatus.OUT_FOR_DELIVERY;
  }
}
