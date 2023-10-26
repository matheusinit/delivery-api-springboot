package com.deliveryapirest.repositories.inMemory;

import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.protocols.OrderToShipRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderToShipRepository implements OrderToShipRepository {
  private List<OrderToShip> orders;

  public InMemoryOrderToShipRepository() {
    this.orders = new ArrayList<OrderToShip>();
  }

  public List<OrderToShip> findAll() {
    return orders;
  }

  public OrderToShip save(OrderToShip orderToShip) {
    this.orders.add(orderToShip);

    return this.orders.get(this.orders.size() - 1);
  }

  public void deleteAll() {
    this.orders.clear();
  }
}
