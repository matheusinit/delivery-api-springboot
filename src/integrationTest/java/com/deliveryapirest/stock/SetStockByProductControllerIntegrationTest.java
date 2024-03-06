package com.deliveryapirest.stock;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.deliveryapirest.entities.Product;
import com.deliveryapirest.entities.Stock;
import com.deliveryapirest.producer.StockProducer;
import com.deliveryapirest.repositories.hibernate.StockRepository;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import io.restassured.RestAssured;
import java.util.UUID;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

class SetStockByProductInput {
  public Integer quantity;
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SetStockByProductControllerIntegrationTest {
  @LocalServerPort Integer port;

  @MockBean private StockProducer stockProducer;

  @Autowired ProductRepository productRepository;
  @Autowired StockRepository stockRepository;

  @BeforeAll
  void setupRestAssured() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenNonExistentProductId_whenSetStockByProduct_thenReturnNotFound() {
    var id = UUID.randomUUID();
    var requestBody = new SetStockByProductInput();

    var response = RestAssured.given()
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
  void givenQuantityIsNotProvided_whenSetStockByProduct_thenReturnBadRequest()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var requestBody = new SetStockByProductInput();

    var response = RestAssured.given()
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
  void givenQuantityIsNegative_whenSetStockByProduct_thenReturnBadRequest()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var requestBody = new SetStockByProductInput();
    requestBody.quantity = -1;

    var response = RestAssured.given()
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
    assertThat(responseBody.get("message"),
               is("Quantity must be 0 or positive"));
  }

  @Test
  void givenQuantityisZero_whenSetStockByProduct_thenReturnOk()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var requestBody = new SetStockByProductInput();
    requestBody.quantity = 0;

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .when()
                       .body(requestBody)
                       .post("/product/" + id + "/stock")
                       .then()
                       .extract()
                       .response();

    assertThat(response.statusCode(), is(200));
  }

  @Test
  void givenQuantityIsPositive_whenSetStockByProduct_thenReturnOk()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var requestBody = new SetStockByProductInput();
    requestBody.quantity = 1;

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .when()
                       .body(requestBody)
                       .post("/product/" + id + "/stock")
                       .then()
                       .extract()
                       .response();

    assertThat(response.statusCode(), is(200));
  }

  @Test
  void givenValidQuantity_whenSetStockByProduct_thenReturnStockData()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var requestBody = new SetStockByProductInput();
    requestBody.quantity = 1;

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .when()
                       .body(requestBody)
                       .post("/product/" + id + "/stock")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(responseBody.get("id"), is(notNullValue()));
    assertThat(responseBody.get("quantity"), is(1));
    assertThat(responseBody.get("productId"), is(id.toString()));
  }

  @Test
  void
  givenStockAlreadyExistsForProductId_whenSetStockByProduct_thenUpdateStock()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var stock = new Stock(id, 1);
    stockRepository.save(stock);
    var requestBody = new SetStockByProductInput();
    requestBody.quantity = 2;

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .when()
                       .body(requestBody)
                       .post("/product/" + id + "/stock")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(responseBody.get("id"), is(stock.getId().toString()));
    assertThat(responseBody.get("quantity"), is(2));
    assertThat(responseBody.get("productId"), is(id.toString()));
  }

  @Test
  void
  givenStockAlreadyExistsForProductId_whenSetStockByProduct_thenShouldUpdateInDatabase()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var stock = new Stock(id, 1);
    stockRepository.save(stock);
    var requestBody = new SetStockByProductInput();
    requestBody.quantity = 2;
    RestAssured.given()
        .accept("application/json")
        .contentType("application/json")
        .when()
        .body(requestBody)
        .post("/product/" + id + "/stock")
        .then()
        .extract()
        .response();

    var stockFromDatabase = stockRepository.findById(stock.getId()).get();

    assertThat(stockFromDatabase.getQuantity(), is(2));
  }

  @Test
  void givenValidData_whenSetStockByProduct_thenShouldUpdateInDatabase()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    productRepository.save(product);
    var id = product.getId();
    var requestBody = new SetStockByProductInput();
    requestBody.quantity = 2;
    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .when()
                       .body(requestBody)
                       .post("/product/" + id + "/stock")
                       .then()
                       .extract()
                       .response();
    var stockId = UUID.fromString(response.getBody().jsonPath().get("id"));

    var stockFromDatabase = stockRepository.findById(stockId).get();

    assertThat(stockFromDatabase.getQuantity(), is(2));
  }
}
