package com.deliveryapirest.services;

import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import java.util.List;
import java.util.Vector;

public class GetDeliveryStationsService {
  DeliveryStationRepository repository;

  public GetDeliveryStationsService() {}

  public GetDeliveryStationsService(DeliveryStationRepository repository) {
    this.repository = repository;
  }

  public List<DeliveryStation> getDeliveryStations() {
    return new Vector<DeliveryStation>();
  }
}
