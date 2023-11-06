package com.deliveryapirest.repositories.protocols;

import com.deliveryapirest.entities.OrderToShip;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderToShipRepository {
  public List<OrderToShip> findAll();

  public OrderToShip save(OrderToShip orderToShip);

  public void deleteAll();

  public Optional<OrderToShip> findById(UUID id);
}
