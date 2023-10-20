package com.deliveryapirest.services;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.inMemory.InMemoryOrderToShipRepository;

public class RegisterOrderToShipService {
  private final InMemoryOrderToShipRepository repository;

  public RegisterOrderToShipService(InMemoryOrderToShipRepository repository) {
    this.repository = repository;
  }

  public void register(Order order) {
    var status = order.getStatus();

    if (status == OrderStatus.CANCELLED) {
      return;
    }

    if (order.getDeletedAt() != null) {
      return;
    }

    repository.save(new OrderToShip(order.getProductId(), order.getStatus(), order.getQuantity()));
  }
}
