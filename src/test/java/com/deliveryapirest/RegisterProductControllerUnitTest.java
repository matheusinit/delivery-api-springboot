package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.deliveryapirest.controller.RegisterProductController;
import com.deliveryapirest.data.RegisterProductInput;
import com.deliveryapirest.repositories.inMemory.InMemoryProductRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RegisterProductControllerUnitTest {
  @Test
  void givenValidData_whenErrorIsThrown_thenGetInternalServerError() {
    var mock = mock(InMemoryProductRepository.class);
    var sut = new RegisterProductController(mock);
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var requestBody = new RegisterProductInput(productName, description);
    when(mock.save(any())).thenThrow(new RuntimeException());

    var responseBody = sut.registerProduct(requestBody);

    assertThat(responseBody.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
