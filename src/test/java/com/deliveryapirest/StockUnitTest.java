package com.deliveryapirest;

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
}
