package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deliveryapirest.entities.Product;
import org.junit.jupiter.api.Test;

class ProductUnitTest {

  @Test
  void givenProductNameIsNotProvided_whenIsRequestedToRegisterProduct_thenIsReturnedAnError() {

    var error = assertThrows(Exception.class, () -> new Product());

    assertThat(error.getMessage(), is("Product name is required"));
  }
}
