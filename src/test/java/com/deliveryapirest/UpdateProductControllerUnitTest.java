package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.deliveryapirest.controller.UpdateProductController;
import com.deliveryapirest.data.UpdateProductInput;
import com.deliveryapirest.errors.EmptyDescriptionError;
import com.deliveryapirest.errors.InternalServerError;
import com.deliveryapirest.errors.InvalidFieldError;
import com.deliveryapirest.services.UpdateProductService;
import java.util.UUID;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class UpdateProductControllerUnitTest {
  @Test
  void givenValidData_whenErrorIsThrown_thenGetInternalServerError()
      throws InvalidFieldError, EmptyDescriptionError {
    var faker = new Faker();
    var serviceMock = mock(UpdateProductService.class);
    var sut = new UpdateProductController(serviceMock);
    when(serviceMock.updateProduct(any(), any())).thenThrow(new RuntimeException());
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var response = sut.updateProduct(UUID.randomUUID(), new UpdateProductInput(name, description));

    var error = (InternalServerError) response.getBody();
    assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    assertThat(error.message, is("An internal server error occured. Please try again later."));
  }
}
