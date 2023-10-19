package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.deliveryapirest.entities.Stock;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StockUnitTest {

  @Test
  void ensureWhenStockIsCreatedThenShouldHaveId() {
    var productId = UUID.randomUUID();

    var stock = new Stock(productId, 1);

    assertNotNull(stock.getId());
  }

  @Test
  void ensureWhenStockIsCreatedThenShouldHaveProductId() {
    var productId = UUID.randomUUID();

    var stock = new Stock(productId, 1);

    assertEquals(productId, stock.getProductId());
  }

  @Test
  void ensureWhenQuantityIsProvidedShouldHaveQuantityGiven() {
    var productId = UUID.randomUUID();
    var quantity = 1;

    var stock = new Stock(productId, quantity);

    assertEquals(quantity, stock.getQuantity());
  }

  @Test
  void ensureWhenQuantityIsNotProvidedShouldHaveQuantityEqualToZero() {
    var productId = UUID.randomUUID();

    var stock = new Stock(productId);

    var expectedQuantity = 0;
    assertEquals(expectedQuantity, stock.getQuantity());
  }

  @Test
  void ensureWhenStockIsCreatedThenShouldHaveCreationDateTimeDefined() {
    var productId = UUID.randomUUID();

    var stock = new Stock(productId);

    assertThat(stock.getCreatedAt(), is(instanceOf(LocalDateTime.class)));
  }

  @Test
  void ensureWhenStockIsCreatedThenShouldHaveUpdateDateTimeNotDefined() {
    var productId = UUID.randomUUID();

    var stock = new Stock(productId);

    assertThat(stock.getUpdatedAt(), is(nullValue()));
  }

  @Test
  void ensureWhenStockIsCreatedThenShouldHaveDeleteDateTimeNotDefined() {
    var productId = UUID.randomUUID();

    var stock = new Stock(productId);

    assertThat(stock.getDeletedAt(), is(nullValue()));
  }
}
