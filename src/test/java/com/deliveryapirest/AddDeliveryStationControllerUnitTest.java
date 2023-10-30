package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.deliveryapirest.controller.AddDeliveryStationController;
import com.deliveryapirest.data.DeliveryStationInput;
import com.deliveryapirest.repositories.inMemory.InMemoryDeliveryStationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

class AddDeliveryStationControllerUnitTest {
  @Test
  void ensureWhenInternalErrorIsThrownShouldGetInternalServerError() {
    var repository = new InMemoryDeliveryStationRepository();
    var sut = new AddDeliveryStationController(repository);
    var mock = mock(AddDeliveryStationController.class);
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";
    Double latitude = -5.826694;
    Double longitude = -35.2144;
    var requestBody = new DeliveryStationInput(name, zipCode, latitude, longitude);
    when(mock.addDeliveryStation(requestBody)).thenThrow(new RuntimeException());

    var response = sut.addDeliveryStation(requestBody);

    assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
  }
}
