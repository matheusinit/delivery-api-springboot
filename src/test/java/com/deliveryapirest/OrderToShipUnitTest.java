package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.entities.OrderToShip;
import java.time.Instant;
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
}
