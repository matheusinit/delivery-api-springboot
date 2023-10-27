package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deliveryapirest.entities.StationDelivery;
import org.junit.jupiter.api.Test;

class StationDeliveryUnitTest {

  @Test
  void givenNoneFields_whenStationDeliveryIsCreated_thenShouldThrowError() {
    Exception error =
        assertThrows(Exception.class, () -> new StationDelivery(null, null, null, null));

    assertEquals("Name, Zip code, Latitude and Longitude is required", error.getMessage());
  }

  @Test
  void givenName_whenAnyOtherFieldIsProvided_thenShouldThrowError() {
    var name = "Rio Grande do Norte\'s Station Delivery";

    Exception error =
        assertThrows(Exception.class, () -> new StationDelivery(name, null, null, null));

    assertEquals("Zip code, Latitude and Longitude is required", error.getMessage());
  }
}
