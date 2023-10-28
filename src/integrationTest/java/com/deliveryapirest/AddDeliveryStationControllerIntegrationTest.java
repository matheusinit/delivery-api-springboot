package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

class AddDeliveryStationRequest {
  private String name;
  private String zipCode;
  private Double latitude;

  public AddDeliveryStationRequest(String name, String zipCode, Double latitude) {
    this.name = name;
    this.zipCode = zipCode;
    this.latitude = latitude;
  }

  public String getName() {
    return name;
  }

  public String getZipCode() {
    return this.zipCode;
  }

  public Double getLatitude() {
    return this.latitude;
  }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddDeliveryStationControllerIntegrationTest {
  @LocalServerPort private Integer port;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenOnlyNameIsProvided_whenAnyOtherFieldIsNotProvided_thenShouldGetBadRequest()
      throws JSONException {
    var name = "Rio Grande do Norte\'s Station Delivery";

    var requestBody = new AddDeliveryStationRequest(name, null, null);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/station")
            .then()
            .extract()
            .response();

    assertEquals(400, response.getStatusCode());
    assertEquals(
        "Zip code, Latitude and Longitude is required",
        response.getBody().jsonPath().get("message"));
  }

  @Test
  void givenOnlyNameAndZipCode_whenAnyOtherFieldIsNotProvided_thenShouldGetBadRequest() {
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";

    var requestBody = new AddDeliveryStationRequest(name, zipCode, null);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/station")
            .then()
            .extract()
            .response();

    assertEquals(400, response.getStatusCode());
    assertEquals(
        "Latitude and Longitude is required", response.getBody().jsonPath().get("message"));
  }

  @Test
  void givenNameAndZipCodeAndLatitude_whenLongitudeIsNotProvided_thenShouldGetBadRequest() {
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";
    var latitude = -5.826694;

    var requestBody = new AddDeliveryStationRequest(name, zipCode, latitude);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/station")
            .then()
            .extract()
            .response();

    assertEquals(400, response.getStatusCode());
    assertEquals("Longitude is required", response.getBody().jsonPath().get("message"));
  }
}
