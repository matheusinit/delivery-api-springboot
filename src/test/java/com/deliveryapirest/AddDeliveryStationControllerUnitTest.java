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
  void ensureWhenInternalErrorIsThrownShouldGetInternalServerError() throws Exception {
    var mock = mock(InMemoryDeliveryStationRepository.class);
    when(mock.save(any())).thenThrow(new RuntimeException());
    var sut = new AddDeliveryStationController(mock);
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";
    Double latitude = -5.826694;
    Double longitude = -35.2144;
    var requestBody = new DeliveryStationInput(name, zipCode, latitude, longitude);

    var response = sut.addDeliveryStation(requestBody);

    assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
  }
}
