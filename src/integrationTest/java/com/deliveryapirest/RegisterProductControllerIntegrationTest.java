package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.data.RegisterProductInput;
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
    var input = new RegisterProductInput(null);

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
    var input = new RegisterProductInput("name", "");

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
}
