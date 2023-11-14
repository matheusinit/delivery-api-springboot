package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.data.RegisterProductInput;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import io.restassured.RestAssured;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterProductControllerIntegrationTest {
  @LocalServerPort private int port;

  @Autowired private ProductRepository repository;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenNameIsNotProvided_whenRegisterProduct_thenGetBadRequest() {
    String name = null;
    var input = new RegisterProductInput(name);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(input)
            .when()
            .post("/product")
            .then()
            .extract()
            .response();

    var responseBody = response.body().jsonPath();
    assertThat(response.statusCode(), equalTo(400));
    assertThat(responseBody.get("message"), equalTo("Product name is required"));
  }

  @Test
  void givenDescriptionAsEmpty_whenRegisterProduct_thenGetBadRequest() {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = "";
    var input = new RegisterProductInput(name, description);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(input)
            .when()
            .post("/product")
            .then()
            .extract()
            .response();

    var responseBody = response.body().jsonPath();
    assertThat(response.statusCode(), equalTo(400));
    assertThat(
        responseBody.get("message"),
        is("Description cannot be empty, must have at least 10 characters"));
  }

  @Test
  void givenDescriptionWithLessThan10Characters_whenRegisterProduct_thenGetBadRequest() {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(9);
    var input = new RegisterProductInput(name, description);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(input)
            .when()
            .post("/product")
            .then()
            .extract()
            .response();

    var responseBody = response.body().jsonPath();
    assertThat(response.statusCode(), equalTo(400));
    assertThat(responseBody.get("message"), is("Description cannot have less than 10 characters"));
  }

  @Test
  void givenOnlyName_whenRegisterProduct_thenGetCreated() {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var input = new RegisterProductInput(name);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(input)
            .when()
            .post("/product")
            .then()
            .extract()
            .response();

    var responseBody = response.body().jsonPath();
    assertThat(response.statusCode(), equalTo(201));
    assertThat(responseBody.get("id"), notNullValue());
    assertThat(responseBody.get("name"), equalTo(name));
    assertThat(responseBody.get("description"), nullValue());
    assertThat(responseBody.get("createdAt"), notNullValue(String.class));
    assertThat(responseBody.get("updatedAt"), nullValue(String.class));
    assertThat(responseBody.get("deletedAt"), nullValue(String.class));
  }

  @Test
  void givenNameAndDescription_whenRegisterProduct_thenGetCreated() {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var input = new RegisterProductInput(name, description);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(input)
            .when()
            .post("/product")
            .then()
            .extract()
            .response();

    var responseBody = response.body().jsonPath();
    assertThat(response.statusCode(), equalTo(201));
    assertThat(responseBody.get("id"), notNullValue());
    assertThat(responseBody.get("name"), equalTo(name));
    assertThat(responseBody.get("description"), equalTo(description));
    assertThat(responseBody.get("createdAt"), notNullValue(String.class));
    assertThat(responseBody.get("updatedAt"), nullValue(String.class));
    assertThat(responseBody.get("deletedAt"), nullValue(String.class));
  }

  @Test
  void givenValidInput_whenRegisterProduct_thenShouldBeStoredInDatabase() {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var input = new RegisterProductInput(name, description);
    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(input)
            .when()
            .post("/product")
            .then()
            .extract()
            .response();
    var responseBody = response.body().jsonPath();

    var productValue = repository.findById(UUID.fromString(responseBody.get("id")));

    var product = productValue.get();
    var createdAt =
        Instant.parse(responseBody.get("createdAt")).plusNanos(500).truncatedTo(ChronoUnit.MICROS);
    assertThat(product.getId(), equalTo(UUID.fromString(responseBody.get("id"))));
    assertThat(product.getName(), equalTo(responseBody.get("name")));
    assertThat(product.getDescription(), equalTo(responseBody.get("description")));
    assertThat(product.getCreatedAt().toString(), equalTo(createdAt.toString()));
    assertThat(product.getUpdatedAt(), equalTo(responseBody.get("updatedAt")));
    assertThat(product.getDeletedAt(), equalTo(responseBody.get("deletedAt")));
  }
}
