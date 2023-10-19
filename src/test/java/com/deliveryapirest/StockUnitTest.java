package com.deliveryapirest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.deliveryapirest.entities.Stock;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StockUnitTest {

  @Test
  void ensureWhenStockIsCreatedThenShouldHaveId() {

    var stock = new Stock(UUID.randomUUID(), 1);

    assertNotNull(stock.getId());
  }
}
