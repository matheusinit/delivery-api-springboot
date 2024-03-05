package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.deliveryapirest.controller.ListProductsController;
import com.deliveryapirest.repositories.inMemory.InMemoryProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ListProductsControllerUnitTest {
  @Test
  void givenErrorHappened_whenListProducts_thenReturnInternalServerError() {
    var mock = mock(InMemoryProductRepository.class);
    when(mock.findAll()).thenThrow(new RuntimeException());
    var sut = new ListProductsController(mock);

    var response = sut.listProducts();

    assertThat(response.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
