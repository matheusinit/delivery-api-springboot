package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deliveryapirest.data.DeliveryStationInput;
import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddDeliveryStationControllerIntegrationTest {
  @LocalServerPort private Integer port;

  @Autowired DeliveryStationRepository repository;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @BeforeEach
  void cleanDatabaseRecords() {
    this.repository.deleteAll();
  }

  @Test
  void givenOnlyNameIsProvided_whenAnyOtherFieldIsNotProvided_thenShouldGetBadRequest()
      throws JSONException {
    var name = "Rio Grande do Norte\'s Station Delivery";

    var requestBody = new DeliveryStationInput(name, null, null, null);

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

    var requestBody = new DeliveryStationInput(name, zipCode, null, null);

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

    var requestBody = new DeliveryStationInput(name, zipCode, latitude, null);

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

  @Test
  void givenValidData_whenDeliveryStationIsAdded_thenShouldGetCreated() {
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";
    Double latitude = -5.826694;
    Double longitude = -35.2144;

    var requestBody = new DeliveryStationInput(name, zipCode, latitude, longitude);

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

    var responseBody = response.getBody().jsonPath();
    var latitudeInResponse = (Float) responseBody.get("latitude");
    var longitudeInResponse = (Float) responseBody.get("longitude");
    assertEquals(201, response.getStatusCode());
    assertEquals(name, responseBody.get("name"));
    assertEquals(zipCode, responseBody.get("zipCode"));
    assertEquals(latitude.floatValue(), latitudeInResponse);
    assertEquals(longitude.floatValue(), longitudeInResponse);
  }

  @Test
  void givenValidData_whenDeliveryStationIsAdded_thenShouldBeStoredInDatabase() {
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";
    Double latitude = -5.826694;
    Double longitude = -35.2144;

    var requestBody = new DeliveryStationInput(name, zipCode, latitude, longitude);

    RestAssured.given()
        .accept("application/json")
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("/station")
        .then();

    var list = repository.findAll();
    assertEquals(1, list.size());
    assertEquals(name, list.get(0).getName());
    assertEquals(zipCode, list.get(0).getZipCode());
    assertEquals(latitude, list.get(0).getLatitude());
    assertEquals(longitude, list.get(0).getLongitude());
  }

  @Test
  void givenValidData_whenDeliveryStationIsAdded_thenShouldHaveCreatedAtValue() {
    var name = "Rio Grande do Norte\'s Station Delivery";
    var zipCode = "59064-625";
    Double latitude = -5.826694;
    Double longitude = -35.2144;

    var requestBody = new DeliveryStationInput(name, zipCode, latitude, longitude);

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

    var responseBody = response.getBody().jsonPath();
    assertThat(responseBody.get("createdAt"), is(notNullValue()));
  }
}
