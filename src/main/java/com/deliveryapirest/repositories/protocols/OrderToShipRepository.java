package com.deliveryapirest.repositories.protocols;

import com.deliveryapirest.entities.OrderToShip;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderToShipRepository extends CrudRepository<OrderToShip, UUID> {}
