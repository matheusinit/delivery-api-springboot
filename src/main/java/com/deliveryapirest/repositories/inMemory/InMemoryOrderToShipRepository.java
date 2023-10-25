package com.deliveryapirest.repositories.inMemory;

import com.deliveryapirest.entities.OrderToShip;
import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderToShipRepository {
  private List<OrderToShip> orders;

  public InMemoryOrderToShipRepository() {
    this.orders = new ArrayList<OrderToShip>();
  }

  public List<OrderToShip> findAll() {
    return orders;
  }

  public void save(OrderToShip orderToShip) {
    this.orders.add(orderToShip);
  }
}
