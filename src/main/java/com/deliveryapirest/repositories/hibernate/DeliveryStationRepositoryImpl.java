package com.deliveryapirest.repositories.hibernate;

import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryStationRepositoryImpl
    extends DeliveryStationRepository, CrudRepository<DeliveryStation, UUID> {}
