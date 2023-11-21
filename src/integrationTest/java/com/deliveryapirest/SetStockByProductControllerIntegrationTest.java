package com.deliveryapirest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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

class SetStockByProductInput {
  public Integer quantity;
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SetStockByProductControllerIntegrationTest {
  @LocalServerPort Integer port;

  @Autowired ProductRepository productRepository;

  @BeforeAll
  void setupRestAssured() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenNonExistentProductId_whenSetStockByProduct_thenReturnNotFound() {
    var id = UUID.randomUUID();
    var requestBody = new SetStockByProductInput();

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .body(requestBody)
            .post("/product/" + id + "/stock")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), is(404));
    assertThat(responseBody.get("message"), is("Product not found"));
  }

  @Test
  void givenQuantityIsNotProvided_whenSetStockByProduct_thenReturnBadRequest() throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var requestBody = new SetStockByProductInput();

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .body(requestBody)
            .post("/product/" + id + "/stock")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), is(400));
    assertThat(responseBody.get("message"), is("Quantity must be provided"));
  }

  @Test
  void givenQuantityIsNegative_whenSetStockByProduct_thenReturnBadRequest() throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var requestBody = new SetStockByProductInput();
    requestBody.quantity = -1;

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .body(requestBody)
            .post("/product/" + id + "/stock")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), is(400));
    assertThat(responseBody.get("message"), is("Quantity must be 0 or positive"));
  }
}
