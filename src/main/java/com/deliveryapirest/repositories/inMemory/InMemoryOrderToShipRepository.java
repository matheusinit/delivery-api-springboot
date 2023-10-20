package com.deliveryapirest.repositories.inMemory;

import com.deliveryapirest.entities.OrderToShip;
import java.util.ArrayList;
import java.util.Collection;

public class InMemoryOrderToShipRepository {
  private Collection<OrderToShip> orders;

  public InMemoryOrderToShipRepository() {
    this.orders = new ArrayList<OrderToShip>();
  }

  public Collection<OrderToShip> findAll() {
    return orders;
  }

  public void save(OrderToShip orderToShip) {
    this.orders.add(orderToShip);
  }
}
