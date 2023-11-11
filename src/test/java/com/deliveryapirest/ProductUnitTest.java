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
  void givenDescriptionIsProvidedAsEmpty_whenIsRequestedToRegisterProduct_thenIsReturnedAnError() {
    var faker = new Faker();
    var productName = faker.commerce().productName();

    var error = assertThrows(Exception.class, () -> new Product(productName, ""));

    var expectedErrorMessage = "Description cannot be empty, must have at least 10 characters";
    assertThat(error.getMessage(), is(expectedErrorMessage));
  }

  @Test
  void
      givenDescriptionWithLessThan10Characters_whenIsRequestedToRegisterProduct_thenIsReturnedAnError() {
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var description = faker.lorem().characters(9);

    var error = assertThrows(Exception.class, () -> new Product(productName, description));

    var expectedErrorMessage = "Description cannot have less than 10 characters";
    assertThat(error.getMessage(), is(expectedErrorMessage));
  }

  @Test
  void givenValidData_whenIsRequestedToRegisterProduct_thenShouldHaveCreatedAtValue()
      throws Exception {
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);

    var product = new Product(productName, description);

    assertThat(product.getCreatedAt(), is(notNullValue()));
  }

  @Test
  void givenValidData_whenIsRequestedToRegisterProduct_thenShouldHaveDateTimeISOAsCreatedAtValue()
      throws Exception {
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    final String regexPattern = "\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d+Z";

    var product = new Product(productName, description);

    assertThat(product.getCreatedAt().toString(), matchesPattern(regexPattern));
  }

  @Test
  void givenValidName_whenIsRequestedToRegisterProduct_thenShouldHaveProductNameAsDefined()
      throws Exception {
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);

    var product = new Product(productName, description);

    assertThat(product.getName(), is(productName));
  }

  @Test
  void
      givenValidDescription_whenIsRequestedToRegisterProduct_thenShouldHaveProductDescriptionAsDefined()
          throws Exception {
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);

    var product = new Product(productName, description);

    assertThat(product.getDescription(), is(description));
  }

  @Test
  void givenValidData_whenIsRequestedToRegisterProduct_thenShouldHaveUpdatedAtAsNull()
      throws Exception {
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);

    var product = new Product(productName, description);

    assertThat(product.getUpdatedAt(), is(nullValue()));
  }

  @Test
  void givenValidData_whenIsRequestedToRegisterProduct_thenShouldHaveDeletedAtAsNull()
      throws Exception {
    var faker = new Faker();
    var productName = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);

    var product = new Product(productName, description);

    assertThat(product.getDeletedAt(), is(nullValue()));
  }
}
