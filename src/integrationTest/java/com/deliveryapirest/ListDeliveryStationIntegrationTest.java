package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListDeliveryStationIntegrationTest {

  @LocalServerPort private Integer port;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void
  givenDeliveryStationsStoredInDatabase_whenListDeliveryStations_thenListIsNotEmpty() {

    var response = RestAssured.given()
                       .accept("application/json")
                       .when()
                       .get("/station")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    var list = responseBody.getList("$");
    assertThat(list, not(empty()));
  }
}
