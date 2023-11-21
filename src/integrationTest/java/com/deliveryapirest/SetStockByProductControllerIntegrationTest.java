package com.deliveryapirest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import io.restassured.RestAssured;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SetStockByProductControllerIntegrationTest {
  @LocalServerPort Integer port;

  @BeforeAll
  void setupRestAssured() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenNonExistentProductId_whenSetStockByProduct_thenReturnNotFound() {
    var id = UUID.randomUUID();

    var response =
        RestAssured.given()
            .accept("application/json")
            .accept("application/json")
            .post("/product/" + id + "/stock")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.statusCode(), is(404));
    assertThat(responseBody.get("message"), is("Product not found"));
  }
}
