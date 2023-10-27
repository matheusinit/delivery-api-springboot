package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deliveryapirest.entities.StationDelivery;
import org.junit.jupiter.api.Test;

class StationDeliveryUnitTest {

  @Test
  void givenName_whenAnyOtherFieldIsProvided_thenShouldThrowError() {
    var name = "Rio Grande do Norte\'s Station Delivery";

    Exception error =
        assertThrows(Exception.class, () -> new StationDelivery(name, null, null, null));

    assertEquals("Zip code, latitude and longitude is required", error.getMessage());
  }
}
