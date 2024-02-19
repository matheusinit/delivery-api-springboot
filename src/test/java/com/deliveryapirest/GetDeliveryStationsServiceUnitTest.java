package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.repositories.inMemory.InMemoryDeliveryStationRepository;
import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import com.deliveryapirest.services.GetDeliveryStationsService;

class GetDeliveryStationsServiceUnitTest {

  void ensureWhenThereIsntAnyDeliveryStationsThenShouldReturnEmptyList() {
    var sut = new GetDeliveryStationsService();

    var deliveryStations = sut.getDeliveryStations();

    assertEquals(0, deliveryStations.size());
  }

  void ensureWhenThereIsAnyDeliveryStationThenShouldReturnListWithItems()
      throws Exception {
    var repository = new InMemoryDeliveryStationRepository();
    var deliveryStation =
        new DeliveryStation("Rio Grande do Norte\'s Station Delivery",
                            "59064-625", -5.826694, -35.2144);
    repository.save(deliveryStation);
    var sut = new GetDeliveryStationsService(repository);

    var deliveryStations = sut.getDeliveryStations();

    assertEquals(1, deliveryStations.size());
  }
}
