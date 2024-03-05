package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.repositories.inMemory.InMemoryDeliveryStationRepository;
import com.deliveryapirest.services.GetDeliveryStationsService;
import org.junit.jupiter.api.Test;

class GetDeliveryStationsServiceUnitTest {
  @Test
  void ensureWhenThereIsntAnyDeliveryStationsThenShouldReturnEmptyList() {
    var repository = new InMemoryDeliveryStationRepository();

    var sut = new GetDeliveryStationsService(repository);

    var deliveryStations = sut.getDeliveryStations();

    assertThat(deliveryStations.size(), equalTo(0));
  }

  @Test
  void ensureWhenThereIsAnyDeliveryStationThenShouldReturnListWithItems()
      throws Exception {
    var repository = new InMemoryDeliveryStationRepository();
    var deliveryStation =
        new DeliveryStation("Rio Grande do Norte\'s Station Delivery",
                            "59064-625", -5.826694, -35.2144);
    repository.save(deliveryStation);
    var sut = new GetDeliveryStationsService(repository);

    var deliveryStations = sut.getDeliveryStations();

    assertThat(deliveryStations.size(), greaterThan(0));
  }

  @Test
  void
  ensureWhenThereIsADeliveryStationThenShouldReturnTheDeliveryStationStored()
      throws Exception {
    var repository = new InMemoryDeliveryStationRepository();
    var deliveryStation =
        new DeliveryStation("Rio Grande do Norte\'s Station Delivery",
                            "59064-625", -5.826694, -35.2144);
    repository.save(deliveryStation);
    var sut = new GetDeliveryStationsService(repository);

    var deliveryStations = sut.getDeliveryStations();

    assertThat(deliveryStations.get(0), equalTo(deliveryStation));
  }
}
