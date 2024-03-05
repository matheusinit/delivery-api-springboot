package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deliveryapirest.entities.DeliveryStation;
import org.junit.jupiter.api.Test;

class DeliveryStationUnitTest {

  @Test
  void givenNoneFields_whenDeliveryStationIsCreated_thenShouldThrowError() {
    Exception error = assertThrows(
        Exception.class, () -> new DeliveryStation(null, null, null, null));

    assertEquals("Name, Zip code, Latitude and Longitude is required",
                 error.getMessage());
  }

  @Test
  void givenName_whenAnyOtherFieldIsNotProvided_thenShouldThrowError() {
    var name = "Rio Grande do Norte\'s Station Delivery";

    Exception error = assertThrows(
        Exception.class, () -> new DeliveryStation(name, null, null, null));

    assertEquals("Zip code, Latitude and Longitude is required",
                 error.getMessage());
  }

  @Test
  void
  givenNameAndZipCode_whenAnyOtherFieldIsNotProvided_thenShouldThrowError() {
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";

    Exception error = assertThrows(
        Exception.class, () -> new DeliveryStation(name, zipCode, null, null));

    assertEquals("Latitude and Longitude is required", error.getMessage());
  }

  @Test
  void
  givenNameAndZipCodeAndLatitude_whenLongitudeIsNotProvided_thenShouldThrowError() {
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";
    var latitude = -5.826694;

    Exception error =
        assertThrows(Exception.class,
                     () -> new DeliveryStation(name, zipCode, latitude, null));

    assertEquals("Longitude is required", error.getMessage());
  }

  @Test
  void
  givenNameAndZipCodeAndLatitudeAndLongitude_whenDeliveryStationIsCreated_thenShouldReturnObjectWithData()
      throws Exception {
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";
    var latitude = -5.826694;
    var longitude = -35.2144;

    var deliveryStation =
        new DeliveryStation(name, zipCode, latitude, longitude);

    assertEquals(name, deliveryStation.getName());
    assertEquals(zipCode, deliveryStation.getZipCode());
    assertEquals(latitude, deliveryStation.getLatitude());
    assertEquals(longitude, deliveryStation.getLongitude());
  }
}
