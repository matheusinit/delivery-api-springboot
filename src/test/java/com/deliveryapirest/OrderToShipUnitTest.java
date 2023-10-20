package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.entities.OrderToShip;
import org.junit.jupiter.api.Test;

class OrderToShipUnitTest {
  @Test
  void ensureWhenOrderToShipIsCreatedThenShouldHaveId() {
    var orderToShip = new OrderToShip();

    assertThat(orderToShip.getId(), is(notNullValue()));
  }
}
