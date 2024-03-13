package com.deliveryapirest.order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.data.StatusInput;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.protocols.OrderToShipRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    var status = new StatusInput(null);

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/null/status")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    var badRequestCode = 400;
    assertThat(response.getStatusCode(), is(badRequestCode));
    assertThat(responseBody.get("error"), is("Bad Request"));
  }

  @Test
  void ensureGivenOrderId_whenOrderIsNotFound_thenShouldGetNotFound() {
    var randomUuid = UUID.randomUUID();
    var status = new StatusInput(null);

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/" + randomUuid + "/status")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    assertThat(response.getStatusCode(), is(404));
    assertThat(responseBody.get("message"),
               is("Order not found with given Id"));
  }

  @Test
  void
  ensureGivenOrderId_whenOrderHasStatusCancelled_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.CANCELLED, 1);
    repository.save(order);
    var status = new StatusInput("OUT_FOR_DELIVERY");

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/" + order.getId() + "/status")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    var expectedMessageError =
        "Order is cancelled. Cannot set it out for delivery!";
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is(expectedMessageError));
  }

  @Test
  void
  ensureGivenOrderId_whenOrderHasStatusOutForDelivery_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.OUT_FOR_DELIVERY, 1);
    repository.save(order);
    var status = new StatusInput("OUT_FOR_DELIVERY");

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/" + order.getId() + "/status")
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
  void
  ensureGivenOrderId_whenOrderHasStatusInDelivery_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.IN_DELIVERY, 1);
    repository.save(order);
    var status = new StatusInput("OUT_FOR_DELIVERY");

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/" + order.getId() + "/status")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    var expectedMessageError =
        "Order is in delivery. Cannot set it out for delivery!";
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is(expectedMessageError));
  }

  @Test
  void
  ensureGivenOrderId_whenOrderHasStatusDelivered_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.DELIVERED, 1);
    repository.save(order);
    var status = new StatusInput("OUT_FOR_DELIVERY");

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/" + order.getId() + "/status")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    var expectedMessageError =
        "Order is delivered. Cannot set it out for delivery!";
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is(expectedMessageError));
  }

  @Test
  void ensureGivenOrderId_whenOrderStatusIsNotGiven_thenShouldGetBadRequest() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.NOT_SENT, 1);
    repository.save(order);
    var status = new StatusInput(null);

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/" + order.getId() + "/status")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    var expectedMessageError =
        "Order status is not given. Cannot set set a new status!";
    assertThat(response.getStatusCode(), is(400));
    assertThat(responseBody.get("message"), is(expectedMessageError));
  }

  @Test
  void ensureGivenOrderId_whenOrderHasStatusNotSent_thenShouldGetOk() {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.NOT_SENT, 1);
    repository.save(order);
    var status = new StatusInput("OUT_FOR_DELIVERY");

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/" + order.getId() + "/status")
                       .then()
                       .extract()
                       .response();

    assertThat(response.getStatusCode(), is(200));
  }

  @Test
  void ensureGivenOrderId_whenOrderHasStatusNotSent_thenShouldGetOrderData()
      throws JsonProcessingException {
    var productId = UUID.randomUUID();
    var order = new OrderToShip(productId, OrderStatus.NOT_SENT, 1);
    repository.save(order);
    var status = new StatusInput("OUT_FOR_DELIVERY");

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .body(status)
                       .when()
                       .post("/order/" + order.getId() + "/status")
                       .then()
                       .extract()
                       .response();

    var responseBody = response.getBody().jsonPath();
    var createdAtTruncated =
        order.getCreatedAt().plusNanos(500).truncatedTo(ChronoUnit.MICROS);
    assertThat(responseBody.get("id"), is(order.getId().toString()));
    assertThat(responseBody.get("productId"),
               is(order.getProductId().toString()));
    assertThat(responseBody.get("quantity"), is(order.getQuantity()));
    assertThat(responseBody.get("status"),
               is(OrderStatus.OUT_FOR_DELIVERY.toString()));
    assertThat(responseBody.get("createdAt"),
               is(createdAtTruncated.toString()));
    assertThat(responseBody.get("createdAt"), is(notNullValue(String.class)));
    assertThat(responseBody.get("canceledAt"), is(nullValue(Instant.class)));
  }
}
