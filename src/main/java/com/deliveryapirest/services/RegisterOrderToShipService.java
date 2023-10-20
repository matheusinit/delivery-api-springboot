package com.deliveryapirest.services;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.repositories.inMemory.InMemoryOrderToShipRepository;

public class RegisterOrderToShipService {
  private final InMemoryOrderToShipRepository repository;

  public RegisterOrderToShipService(InMemoryOrderToShipRepository repository) {
    this.repository = repository;
  }

  public void register(Order order) {}
}
