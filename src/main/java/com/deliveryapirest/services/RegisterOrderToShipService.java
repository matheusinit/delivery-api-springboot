package com.deliveryapirest.services;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.protocols.OrderToShipRepository;

public class RegisterOrderToShipService {
  private final OrderToShipRepository repository;

  public RegisterOrderToShipService(OrderToShipRepository repository) {
    this.repository = repository;
  }

  public void register(Order order) {
    var quantity = order.getQuantity();
    if (quantity == 0) {
      return;
    }

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
