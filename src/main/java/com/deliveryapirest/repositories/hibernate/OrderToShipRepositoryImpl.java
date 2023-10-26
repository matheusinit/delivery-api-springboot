package com.deliveryapirest.repositories.hibernate;

import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.protocols.OrderToShipRepository;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderToShipRepositoryImpl
    extends OrderToShipRepository, CrudRepository<OrderToShip, UUID> {}
