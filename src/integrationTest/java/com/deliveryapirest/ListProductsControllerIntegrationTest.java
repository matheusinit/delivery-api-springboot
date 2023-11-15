package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.entities.Product;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import io.restassured.RestAssured;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListProductsControllerIntegrationTest {
  @LocalServerPort private Integer port;

  @Autowired ProductRepository repository;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @BeforeEach
  void cleanUp() {
    this.repository.deleteAll();
  }

  @Test
  void givenProductsStoredInDatabase_whenListProducts_thenProductListAreReturnedWithSameSize()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().sentence(10);
    this.repository.save(new Product(name, description));
    var secondName = faker.commerce().productName();
    var secondDescription = faker.lorem().sentence(10);
    this.repository.save(new Product(secondName, secondDescription));

    var response =
        RestAssured.given()
            .accept("application/json")
            .when()
            .get("/product")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(responseBody.getList("$"), hasSize(2));
  }

  @Test
  void givenProductsStoredInDatabase_whenListProducts_thenProductListReturnedWithSameData()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().sentence(10);
    this.repository.save(new Product(name, description));
    var secondName = faker.commerce().productName();
    var secondDescription = faker.lorem().sentence(10);
    this.repository.save(new Product(secondName, secondDescription));

    var response =
        RestAssured.given()
            .accept("application/json")
            .when()
            .get("/product")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    var firstProduct = responseBody.getObject("[0]", Product.class);
    var secondProduct = responseBody.getObject("[1]", Product.class);
    assertThat(firstProduct.getName(), equalTo(name));
    assertThat(firstProduct.getDescription(), equalTo(description));
    assertThat(secondProduct.getName(), equalTo(secondName));
    assertThat(secondProduct.getDescription(), equalTo(secondDescription));
  }
}
