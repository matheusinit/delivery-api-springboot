package com.deliveryapirest.order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.errors.InvalidOperationError;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

class OrderToShipUnitTest {
  @Test
  void ensureWhenOrderToShipIsCreatedThenShouldHaveId() {
    var orderToShip = new OrderToShip();

    assertThat(orderToShip.getId(), is(notNullValue()));
  }

  @Test
  void ensureWhenProductIdIsProvidedThenShouldHaveProductId() {
    var productId = UUID.randomUUID();
    var quantity = 1;

    var orderToShip = new OrderToShip(productId, quantity);

    assertThat(orderToShip.getProductId(), is(productId));
  }

  @Test
  void ensureWhenQuantityIsProvidedThenShouldHaveQuantityEqualAsExpected() {
    var productId = UUID.randomUUID();
    var quantity = 2;

    var orderToShip = new OrderToShip(productId, quantity);

    assertThat(orderToShip.getQuantity(), is(quantity));
  }

  @Test
  void ensureWhenOrderToShipIsCreatedThenShouldHaveCreatedAtAsNotNull() {
    var productId = UUID.randomUUID();
    var quantity = 2;

    var orderToShip = new OrderToShip(productId, quantity);

    assertThat(orderToShip.getCreatedAt(), is(notNullValue()));
  }

  @Test
  void ensureWhenOrderToShipIsCreatedThenShouldHaveCreatedAtOfTypeInstant() {
    var productId = UUID.randomUUID();
    var quantity = 2;

    var orderToShip = new OrderToShip(productId, quantity);

    assertThat(orderToShip.getCreatedAt(), is(instanceOf(Instant.class)));
  }

  @Test
  void ensureWhenOrderToShipIsCreatedThenShouldHaveUpdatedAtAsNull() {
    var productId = UUID.randomUUID();
    var quantity = 2;

    var orderToShip = new OrderToShip(productId, quantity);

    assertThat(orderToShip.getUpdatedAt(), is(nullValue()));
  }

  @Test
  void ensureWhenOrderToShipIsCreatedThenShouldHaveCanceledAtAsNull() {
    var productId = UUID.randomUUID();
    var quantity = 2;

    var orderToShip = new OrderToShip(productId, quantity);

    assertThat(orderToShip.getCanceledAt(), is(nullValue()));
  }

  @Test
  void ensureWhenValidDataIsProvidedThenShouldCreateRandomUUID() {
    var productId = UUID.randomUUID();
    var quantity = 2;

    var orderToShip = new OrderToShip(productId, quantity);

    assertThat(orderToShip.getId(), is(instanceOf(UUID.class)));
  }

  @Test
  void
  ensureGivenOrderWithCancelledStatusWhenOrderIsSetOutForDeliveryThenShouldThrowError() {
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.CANCELLED;
    var createdAt = ZonedDateTime.now();

    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, null, null);

    Exception error =
        assertThrows(Exception.class, () -> orderToShip.setOutForDelivery());

    assertThat(orderToShip.getStatus(), is(OrderStatus.CANCELLED));
    assertThat(error.getMessage(),
               is("Order is cancelled. Cannot set it out for delivery!"));
  }

  @Test
  void
  ensureGivenOrderWithOutForDeliveryStatusWhenOrderIsSetOutForDeliveryThenShouldThrowError() {
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.OUT_FOR_DELIVERY;
    var createdAt = ZonedDateTime.now();

    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, null, null);

    Exception error =
        assertThrows(Exception.class, () -> orderToShip.setOutForDelivery());

    assertThat(orderToShip.getStatus(), is(OrderStatus.OUT_FOR_DELIVERY));
    var expectedErrorMessage =
        "Order is already out for delivery. Cannot set it out for delivery again!";
    assertThat(error.getMessage(), is(expectedErrorMessage));
  }

  @Test
  void
  ensureGivenOrderWithInDeliveryStatusWhenOrderIsSetOutForDeliveryThenShouldThrowError() {
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.IN_DELIVERY;
    var createdAt = ZonedDateTime.now();

    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, null, null);

    Exception error =
        assertThrows(Exception.class, () -> orderToShip.setOutForDelivery());

    assertThat(orderToShip.getStatus(), is(OrderStatus.IN_DELIVERY));
    var expectedErrorMessage =
        "Order is in delivery. Cannot set it out for delivery!";
    assertThat(error.getMessage(), is(expectedErrorMessage));
  }

  @Test
  void
  ensureGivenOrderWithDeliveredStatusWhenOrderIsSetOutForDeliveryThenShouldThrowError() {
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.DELIVERED;
    var createdAt = ZonedDateTime.now();

    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, null, null);

    Exception error =
        assertThrows(Exception.class, () -> orderToShip.setOutForDelivery());

    assertThat(orderToShip.getStatus(), is(OrderStatus.DELIVERED));
    var expectedErrorMessage =
        "Order is delivered. Cannot set it out for delivery!";
    assertThat(error.getMessage(), is(expectedErrorMessage));
  }

  @Test
  void
  ensureGivenOrderWithNotSentStatusWhenOrderIsSetOutForDeliveryThenShouldChangeStatusToOutForDelivery()
      throws Exception {
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.NOT_SENT;
    var createdAt = ZonedDateTime.now();

    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, null, null);

    orderToShip.setOutForDelivery();

    assertThat(orderToShip.getStatus(), is(OrderStatus.OUT_FOR_DELIVERY));
  }

  @Test
  void
  ensureGivenOrderWithNotSentStatusWhenOrderIsSetOutForDeliveryThenShouldSetUpdatedAtToCurrentDateTime()
      throws Exception {
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.NOT_SENT;
    var createdAt = ZonedDateTime.now();

    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, null, null);

    orderToShip.setOutForDelivery();

    assertThat(orderToShip.getUpdatedAt(), is(notNullValue(Instant.class)));
  }

  @Test
  void ensureWhenOrderWasMadeThenShouldSetOriginLocationOfProduct() {
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.NOT_SENT;
    var createdAt = ZonedDateTime.now();
    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, null, null);

    var faker = new Faker();
    var originLocation = faker.address().cityName();
    orderToShip.setLocation(originLocation);

    assertThat(orderToShip.getLocation(), is(originLocation));
  }

  @Test
  void
  ensureGivenOrderWithOutForDeliveryStatusWhenOrderIsSetForInDeliveryThenShouldOrderStatusBeInDelivery()
      throws InvalidOperationError {
    var faker = new Faker();
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.OUT_FOR_DELIVERY;
    var createdAt = ZonedDateTime.now();
    var updatedAt = faker.date().past(1, TimeUnit.DAYS).toInstant();
    var optionalUpdatedAt =
        Optional.of(ZonedDateTime.ofInstant(updatedAt, ZoneId.systemDefault()));
    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, optionalUpdatedAt, null);

    orderToShip.setInDelivery();

    assertThat(orderToShip.getStatus(), is(OrderStatus.IN_DELIVERY));
  }

  @Test
  void
  ensureGivenOrderWithOutForDeliveryStatusWhenOrderIsSetForInDeliveryThenShouldSetUpdatedAtToCurrentDateTime()
      throws InvalidOperationError {
    var faker = new Faker();
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.OUT_FOR_DELIVERY;
    var createdAt = ZonedDateTime.now();
    var updatedAt = faker.date().past(1, TimeUnit.DAYS).toInstant();
    var optionalUpdatedAt =
        Optional.of(ZonedDateTime.ofInstant(updatedAt, ZoneId.systemDefault()));
    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, optionalUpdatedAt, null);
    var currentUpdatedAt = orderToShip.getUpdatedAt();

    orderToShip.setInDelivery();

    assertThat(orderToShip.getUpdatedAt(), is(not(currentUpdatedAt)));
  }

  @Test
  void
  ensureGivenOrderWithNotSentStatusWhenOrderIsSetForInDeliveryThenShouldGetAnError() {

    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.NOT_SENT;
    var createdAt = ZonedDateTime.now();
    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, null, null);

    var error =
        assertThrows(Exception.class, () -> orderToShip.setInDelivery());

    assertThat(error.getMessage(),
               is("Order is not sent. Cannot set it in delivery!"));
  }

  @Test
  void
  ensureGivenOrderWithInDeliveryStatusWhenOrderIsSetAsDeliveredThenShouldOrderStatusBeDelivered()
      throws InvalidOperationError {
    var faker = new Faker();
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.IN_DELIVERY;
    var createdAt = ZonedDateTime.now();
    var updatedAt = faker.date().past(1, TimeUnit.DAYS).toInstant();
    var optionalUpdatedAt =
        Optional.of(ZonedDateTime.ofInstant(updatedAt, ZoneId.systemDefault()));
    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, optionalUpdatedAt, null);

    orderToShip.setDelivered();

    assertThat(orderToShip.getStatus(), is(OrderStatus.DELIVERED));
  }

  @Test
  void
  ensureGivenOrderWithInDeliveryStatusWhenOrderIsSetAsDeliveredThenShouldUpdatedAtToCurrentDateTime()
      throws InvalidOperationError {
    var faker = new Faker();
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.IN_DELIVERY;
    var createdAt = ZonedDateTime.now();
    var updatedAt = faker.date().past(1, TimeUnit.DAYS).toInstant();
    var optionalUpdatedAt =
        Optional.of(ZonedDateTime.ofInstant(updatedAt, ZoneId.systemDefault()));
    var orderToShip = new OrderToShip(id, productId, quantity, currentStatus,
                                      createdAt, optionalUpdatedAt, null);
    var currentUpdatedAt = orderToShip.getUpdatedAt();

    orderToShip.setDelivered();

    assertThat(orderToShip.getUpdatedAt(), is(not(currentUpdatedAt)));
  }
}
