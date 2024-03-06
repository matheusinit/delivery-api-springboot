package com.deliveryapirest.deliveryStation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import io.restassured.RestAssured;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListDeliveryStationIntegrationTest {

  @LocalServerPort private Integer port;

  @Autowired DeliveryStationRepository repository;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @BeforeEach
  void cleanUpDatabase() {
    this.repository.deleteAll();
  }

  @Test
  void
  givenDeliveryStationsStoredInDatabase_whenListDeliveryStations_thenListIsNotEmpty()
      throws Exception {
    var faker = new Faker();
    var name = faker.address().cityName();
    var zipCode = faker.address().zipCode();
    var latitude = Double.parseDouble(faker.address().latitude());
    var longitude = Double.parseDouble(faker.address().longitude());
    var deliveryStation =
        new DeliveryStation(name, zipCode, latitude, longitude);

    this.repository.save(deliveryStation);

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

  @Test
  void
  givenDeliveryStationsAreNotStoredInDatabase_whenListDeliveryStations_thenListIsEmpty() {
    var response = RestAssured.given()
                       .accept("application/json")
                       .when()
                       .get("/station")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    var list = responseBody.getList("$");
    assertThat(list, empty());
  }
}
