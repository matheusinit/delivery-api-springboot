package com.deliveryapirest.stock;

import com.deliveryapirest.entities.Stock;
import com.deliveryapirest.errors.InvalidOperationError;
import com.deliveryapirest.repositories.hibernate.StockRepository;
import io.restassured.RestAssured;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetStockByProductIntegrationTest {
  @LocalServerPort private Integer port;

  @Autowired StockRepository stockRepository;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenProductId_whenGetStockByProduct_thenReturnOk()
      throws InvalidOperationError {
    var productId = UUID.randomUUID();
    stockRepository.save(new Stock(productId, 1));

    var response = RestAssured.given()
                       .accept("application/json")
                       .when()
                       .get("/product/" + productId + "/stock")
                       .then()
                       .extract()
                       .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }

  @Test
  void givenProductId_whenGetStockByProduct_thenReturnProductStock()
      throws InvalidOperationError {
    var productId = UUID.randomUUID();
    stockRepository.save(new Stock(productId, 1));

    var response = RestAssured.given()
                       .accept("application/json")
                       .when()
                       .get("/product/" + productId + "/stock")
                       .then()
                       .extract()
                       .response();

    Assertions.assertNotNull(response.getBody().jsonPath().get("id"));
    Assertions.assertNotNull(response.getBody().jsonPath().get("productId"));
    Assertions.assertNotNull(response.getBody().jsonPath().get("quantity"));
    Assertions.assertNotNull(response.getBody().jsonPath().get("createdAt"));
    Assertions.assertNull(response.getBody().jsonPath().get("updatedAt"));
    Assertions.assertNull(response.getBody().jsonPath().get("deletedAt"));
  }

  @Test
  void givenInvalidProductId_whenGetStockByProduct_thenReturnNotFound() {
    var invalidId = UUID.randomUUID();

    var response = RestAssured.given()
                       .accept("application/json")
                       .when()
                       .get(String.format("/product/%s/stock", invalidId))
                       .then()
                       .extract()
                       .response();

    Assertions.assertEquals(404, response.getStatusCode());
  }

  @Test
  void
  givenInvalidProductId_whenGetStockByProduct_thenReturnStockNotFoundError() {
    var invalidId = UUID.randomUUID();

    var response = RestAssured.given()
                       .accept("application/json")
                       .when()
                       .get(String.format("/product/%s/stock", invalidId))
                       .then()
                       .extract()
                       .response();

    Assertions.assertEquals("Stock with given ID not found",
                            response.getBody().jsonPath().get("message"));
  }
}
