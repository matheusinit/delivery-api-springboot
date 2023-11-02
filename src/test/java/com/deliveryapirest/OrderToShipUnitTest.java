package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;
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
  void ensureGivenOrderWithCancelledStatusWhenOrderIsSetOutForDeliveryThenShouldThrowError() {
    var id = UUID.randomUUID();
    var productId = UUID.randomUUID();
    var quantity = 2;
    var currentStatus = OrderStatus.CANCELLED;
    var createdAt = ZonedDateTime.now();

    var orderToShip =
        new OrderToShip(id, productId, quantity, currentStatus, createdAt, null, null);

    Exception error = assertThrows(Exception.class, () -> orderToShip.setOutForDelivery());

    assertThat(orderToShip.getStatus(), is(OrderStatus.CANCELLED));
    assertThat(error.getMessage(), is("Order is cancelled. Cannot set it out for delivery!"));
  }
}
