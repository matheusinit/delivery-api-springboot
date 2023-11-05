package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SetOrderOutForDeliveryControllerIntegrationTest {
  @LocalServerPort private Integer port;

  @BeforeAll
  void setupClient() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void ensureWhenOrderIdIsNotProvided_thenShouldGetBadRequest() {
    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .post("/order/null/delivery")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is("Order id is required"));
  }
}
