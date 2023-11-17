package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

class UpdateProductInput {
  public String name;
  public String description;
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateProductControllerIntegrationTest {
  @LocalServerPort private int port;

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
}
