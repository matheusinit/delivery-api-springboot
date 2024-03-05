package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.deliveryapirest.controller.SetStockByProductController;
import com.deliveryapirest.data.SetStockByProductInput;
import com.deliveryapirest.errors.InvalidOperationError;
import com.deliveryapirest.services.SetStockByProductService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SetStockByProducerControllerUnitTest {
  @Test
  void givenAErrorThrown_whenSetStockByProduct_thenReturnInternalServerError()
      throws InvalidOperationError {
    var service = mock(SetStockByProductService.class);
    var sut = new SetStockByProductController(service);
    when(service.setStockByProduct(any(), any())).thenThrow(new RuntimeException());
    var productId = UUID.randomUUID();
    var quantity = 1;
    var input = new SetStockByProductInput(quantity);

    var response = sut.setStockByProduct(productId, input);

    assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
