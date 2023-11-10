package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deliveryapirest.entities.Product;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

class ProductUnitTest {

  @Test
  void givenProductNameIsNotProvided_whenIsRequestedToRegisterProduct_thenIsReturnedAnError() {

    var error = assertThrows(Exception.class, () -> new Product());

    assertThat(error.getMessage(), is("Product name is required"));
  }

  @Test
  void givenPriceIsNotProvided_whenIsRequestedToRegisterProduct_thenIsReturnedAnError() {
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var error = assertThrows(Exception.class, () -> new Product(productName));

    assertThat(error.getMessage(), is("Price is required"));
  }
}
