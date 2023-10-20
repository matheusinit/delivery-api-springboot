package com.deliveryapirest.repositories.inMemory;

import com.deliveryapirest.entities.OrderToShip;
import java.util.ArrayList;
import java.util.Collection;

public class InMemoryOrderToShipRepository {
  public Collection<OrderToShip> findAll() {
    return new ArrayList<OrderToShip>();
  }
}
