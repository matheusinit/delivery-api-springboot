package com.deliveryapirest.services;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.protocols.OrderToShipRepository;
import org.springframework.stereotype.Service;

@Service
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

    if (order.getDeletedAt().orElse(null) != null) {
      return;
    }

    var orderToShip =
        new OrderToShip(
            order.getId(),
            order.getProductId(),
            order.getQuantity(),
            order.getStatus(),
            order.getCreatedAt(),
            order.getUpdatedAt(),
            order.getDeletedAt());

    repository.save(orderToShip);
  }
}
