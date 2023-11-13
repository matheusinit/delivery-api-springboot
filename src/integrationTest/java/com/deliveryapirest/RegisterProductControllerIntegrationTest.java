package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterProductControllerIntegrationTest {
  @LocalServerPort private int port;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenNameIsNotProvided_whenRegisterProduct_thenGetBadRequest() {
    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .post("/product")
            .then()
            .extract()
            .response();

    var responseBody = response.body().jsonPath();
    assertThat(response.statusCode(), equalTo(400));
    assertThat(responseBody.get("message"), equalTo("Product name is required"));
  }
}
