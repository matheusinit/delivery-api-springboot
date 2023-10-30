package com.deliveryapirest.repositories.protocols;

import com.deliveryapirest.entities.DeliveryStation;
import java.util.List;

public interface DeliveryStationRepository {
  public DeliveryStation save(DeliveryStation entity);

  public List<DeliveryStation> findAll();

  public void deleteAll();
}
