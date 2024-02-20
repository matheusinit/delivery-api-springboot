package com.deliveryapirest.repositories.inMemory;

import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import java.util.List;

public class InMemoryDeliveryStationRepository
    implements DeliveryStationRepository {

  List<DeliveryStation> deliveryStations;

  public InMemoryDeliveryStationRepository() {
    this.deliveryStations = new java.util.ArrayList<>();
  }

  @Override
  public DeliveryStation save(DeliveryStation entity) {
    this.deliveryStations.add(entity);

    return deliveryStations.get(deliveryStations.size() - 1);
  }

  @Override
  public List<DeliveryStation> findAll() {
    return deliveryStations;
  }

  @Override
  public void deleteAll() {
    deliveryStations.clear();
  }
}
