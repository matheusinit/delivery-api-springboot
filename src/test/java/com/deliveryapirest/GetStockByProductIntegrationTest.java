package com.deliveryapirest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetStockByProductIntegrationTest {
  @LocalServerPort private Integer port;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenProductId_whenGetStockByProduct_thenReturnOk() {
    var response =
        RestAssured.given()
            .accept("application/json")
            .when()
            .get("/stock/1")
            .then()
            .extract()
            .response();

    Assertions.assertEquals(200, response.getStatusCode());
  }
}
