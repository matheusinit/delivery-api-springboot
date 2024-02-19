package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deliveryapirest.services.GetDeliveryStationsService;

class GetDeliveryStationsServiceUnitTest {

  void ensureWhenThereIsntAnyDeliveryStationsThenShouldReturnEmptyList() {
    var sut = new GetDeliveryStationsService();

    var deliveryStations = sut.getDeliveryStations();

    assertEquals(0, deliveryStations.size());
  }
}
