package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.entities.Product;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import io.restassured.RestAssured;
import java.util.UUID;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

class UpdateProductInput {
  public String name;
  public String description;
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateProductControllerIntegrationTest {
  @LocalServerPort private int port;

  @Autowired private ProductRepository repository;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenNameAndDescriptionAsNull_whenUpdateProduct_thenReturnBadRequest() {
    var uuid = UUID.randomUUID();
    var requestBody = new UpdateProductInput();

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .body(requestBody)
            .patch("/product/" + uuid)
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), equalTo(400));
    assertThat(
        responseBody.get("message"),
        equalTo("Name or description cannot be null, at least one of them must be provided"));
  }

  @Test
  void givenNameAsEmpty_whenUpdateProduct_thenReturnBadRequest() {
    var uuid = UUID.randomUUID();
    var requestBody = new UpdateProductInput();
    requestBody.name = "";

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .body(requestBody)
            .patch("/product/" + uuid)
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), equalTo(400));
    assertThat(
        responseBody.get("message"),
        equalTo(
            "Name cannot be null and description must be not null, at least one of them must be"
                + " provided"));
  }

  @Test
  void givenDescriptionAsEmpty_whenUpdateProduct_thenReturnUpdatedData() throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    repository.save(product);
    var requestBody = new UpdateProductInput();
    requestBody.description = "";

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .body(requestBody)
            .patch("/product/" + product.getId())
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), equalTo(400));
    assertThat(
        responseBody.get("message"),
        is("Description cannot be empty, must have at least 10 characters"));
  }

  @Test
  void givenDescription_whenUpdateProduct_thenReturnUpdatedData() throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    repository.save(product);
    var requestBody = new UpdateProductInput();
    var newDescription = faker.lorem().maxLengthSentence(10);
    requestBody.description = newDescription;

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .body(requestBody)
            .patch("/product/" + product.getId())
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), equalTo(200));
    assertThat(responseBody.get("description"), is(newDescription));
  }

  @Test
  void givenName_whenUpdateProduct_thenReturnUpdatedData() throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    repository.save(product);
    var requestBody = new UpdateProductInput();
    var newName = faker.commerce().productName();
    requestBody.name = newName;

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .body(requestBody)
            .patch("/product/" + product.getId())
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), equalTo(200));
    assertThat(responseBody.get("name"), is(newName));
  }

  @Test
  void givenName_whenUpdateProduct_thenShouldHaveDataUpdatedInDatabase() throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    repository.save(product);
    var requestBody = new UpdateProductInput();
    var newName = faker.commerce().productName();
    requestBody.name = newName;
    RestAssured.given()
        .accept("application/json")
        .contentType("application/json")
        .when()
        .body(requestBody)
        .patch("/product/" + product.getId())
        .then()
        .extract()
        .response();

    var productFromDb = repository.findById(product.getId()).get();

    assertThat(productFromDb.getName(), is(newName));
  }

  @Test
  void givenDescription_whenUpdateProduct_thenShouldHaveDataUpdatedInDatabase() throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    repository.save(product);
    var requestBody = new UpdateProductInput();
    var newDescription = faker.lorem().maxLengthSentence(10);
    requestBody.description = newDescription;
    RestAssured.given()
        .accept("application/json")
        .contentType("application/json")
        .when()
        .body(requestBody)
        .patch("/product/" + product.getId())
        .then()
        .extract()
        .response();

    var productFromDb = repository.findById(product.getId()).get();

    assertThat(productFromDb.getDescription(), is(newDescription));
  }

  @Test
  void givenValidData_whenUpdateProduct_thenShouldHaveUpdatedAtUpdatedInDatabase()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    repository.save(product);
    var requestBody = new UpdateProductInput();
    var newName = faker.commerce().productName();
    var newDescription = faker.lorem().maxLengthSentence(10);
    requestBody.name = newName;
    requestBody.description = newDescription;
    RestAssured.given()
        .accept("application/json")
        .contentType("application/json")
        .when()
        .body(requestBody)
        .patch("/product/" + product.getId())
        .then()
        .extract()
        .response();

    var productFromDb = repository.findById(product.getId()).get();

    assertThat(productFromDb.getUpdatedAt(), is(notNullValue()));
  }
}
