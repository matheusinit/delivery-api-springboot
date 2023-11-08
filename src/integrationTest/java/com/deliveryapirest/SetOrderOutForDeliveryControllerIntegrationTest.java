package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.protocols.OrderToShipRepository;
import io.restassured.RestAssured;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SetOrderOutForDeliveryControllerIntegrationTest {
  @LocalServerPort private Integer port;

  @Autowired private OrderToShipRepository repository;

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

    response.getBody().prettyPrint();
    var responseBody = response.getBody().jsonPath();
    var badRequestCode = 400;
    assertThat(response.getStatusCode(), is(badRequestCode));
    assertThat(responseBody.get("error"), is("Bad Request"));
  }

  @Test
  void ensureGivenOrderId_whenOrderIsNotFound_thenShouldGetNotFound() {
    var randomUuid = UUID.randomUUID();

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .post("/order/" + randomUuid + "/delivery")
            .then()
            .extract()
            .response();

    response.getBody().prettyPrint();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.getStatusCode(), is(404));
    assertThat(responseBody.get("message"), is("Order not found with given id"));
  }

  @Test
  void ensureGivenOrderId_whenOrderHasStatusCancelled_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.CANCELLED, 1);
    repository.save(order);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .post("/order/" + order.getId() + "/delivery")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    var expectedMessageError = "Order is cancelled. Cannot set it out for delivery!";
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is(expectedMessageError));
  }

  @Test
  void ensureGivenOrderId_whenOrderHasStatusOutForDelivery_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.OUT_FOR_DELIVERY, 1);
    repository.save(order);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .post("/order/" + order.getId() + "/delivery")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    var expectedMessageError =
        "Order is already out for delivery. Cannot set it out for delivery again!";
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is(expectedMessageError));
  }

  @Test
  void ensureGivenOrderId_whenOrderHasStatusInDelivery_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.IN_DELIVERY, 1);
    repository.save(order);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .post("/order/" + order.getId() + "/delivery")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    var expectedMessageError = "Order is in delivery. Cannot set it out for delivery!";
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is(expectedMessageError));
  }

  @Test
  void ensureGivenOrderId_whenOrderHasStatusDelivered_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.DELIVERED, 1);
    repository.save(order);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .post("/order/" + order.getId() + "/delivery")
            .then()
            .extract()
            .response();

    var responseBody = response.getBody().jsonPath();
    var expectedMessageError = "Order is delivered. Cannot set it out for delivery!";
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is(expectedMessageError));
  }

  @Test
  void ensureGivenOrderId_whenOrderHasStatusNotSent_thenShouldGetOk() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.NOT_SENT, 1);
    repository.save(order);

    var response =
        RestAssured.given()
            .accept("application/json")
            .contentType("application/json")
            .when()
            .post("/order/" + order.getId() + "/delivery")
            .then()
            .extract()
            .response();

    assertThat(response.getStatusCode(), is(200));
  }
}
