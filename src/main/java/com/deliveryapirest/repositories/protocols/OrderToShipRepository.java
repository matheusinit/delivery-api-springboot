package com.deliveryapirest.repositories.protocols;

import com.deliveryapirest.entities.OrderToShip;
import java.util.List;

public interface OrderToShipRepository {
  public List<OrderToShip> findAll();

  public OrderToShip save(OrderToShip orderToShip);

  public void deleteAll();
}
