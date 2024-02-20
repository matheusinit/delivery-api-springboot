package com.deliveryapirest.services;

import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import java.util.List;

public class GetDeliveryStationsService {
  DeliveryStationRepository repository;

  public GetDeliveryStationsService(DeliveryStationRepository repository) {
    this.repository = repository;
  }

  public List<DeliveryStation> getDeliveryStations() {
    return repository.findAll();
  }
}
