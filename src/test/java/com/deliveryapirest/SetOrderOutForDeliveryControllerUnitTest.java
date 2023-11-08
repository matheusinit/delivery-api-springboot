package com.deliveryapirest;

// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;
//
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.deliveryapirest.controller.SetOrderOutForDeliveryController;
import com.deliveryapirest.repositories.inMemory.InMemoryOrderToShipRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SetOrderOutForDeliveryControllerUnitTest {

  @Test
  void ensureGivenOrderId_whenExceptionIsThrown_thenShouldGetInternalServerError() {
    var orderId = UUID.randomUUID();
    var mock = mock(InMemoryOrderToShipRepository.class);
    when(mock.findById(orderId)).thenThrow(new RuntimeException());
    var sut = new SetOrderOutForDeliveryController(mock);

    var response = sut.setOrderOutForDelivery(orderId);

    assertEquals(HttpStatus.valueOf(500), response.getStatusCode());
  }
}
