package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.deliveryapirest.entities.Stock;
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
}
