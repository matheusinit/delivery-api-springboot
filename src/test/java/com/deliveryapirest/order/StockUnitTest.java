package com.deliveryapirest.order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.deliveryapirest.entities.Stock;
import com.deliveryapirest.errors.InvalidOperationError;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StockUnitTest {

  @Test
  void ensureWhenStockIsCreatedThenShouldHaveId() throws InvalidOperationError {
    var productId = UUID.randomUUID();

    var stock = new Stock(productId, 1);

    assertNotNull(stock.getId());
  }

  @Test
  void ensureWhenStockIsCreatedThenShouldHaveProductId()
      throws InvalidOperationError {
    var productId = UUID.randomUUID();

    var stock = new Stock(productId, 1);

    assertEquals(productId, stock.getProductId());
  }

  @Test
  void ensureWhenQuantityIsProvidedShouldHaveQuantityGiven()
      throws InvalidOperationError {
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

    assertThat(stock.getCreatedAt(), is(instanceOf(Instant.class)));
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

  @Test
  void ensureGivenNegativeQuantityWhenUpdateStockThenShouldThrowException()
      throws InvalidOperationError {
    var productId = UUID.randomUUID();
    var stock = new Stock(productId, 1);

    var error =
        assertThrows(InvalidOperationError.class, () -> stock.setQuantity(-1));

    assertThat(error.getMessage(), is("Quantity must be 0 or positive"));
  }

  @Test
  void ensureGivenZeroQuantityWhenUpdateStockThenShouldUpdate()
      throws InvalidOperationError {
    var productId = UUID.randomUUID();
    var stock = new Stock(productId, 1);

    stock.setQuantity(0);

    assertThat(stock.getQuantity(), is(0));
  }

  @Test
  void ensureGivenPositiveQuantityWhenUpdateStockThenShouldUpdate()
      throws InvalidOperationError {
    var productId = UUID.randomUUID();
    var stock = new Stock(productId, 1);

    stock.setQuantity(2);

    assertThat(stock.getQuantity(), is(2));
  }

  @Test
  void ensureGivenDeletedStockWhenUpdateStockThenShouldThrowException()
      throws InvalidOperationError {
    var productId = UUID.randomUUID();
    var stock = new Stock(productId, 1);
    stock.delete();

    var error =
        assertThrows(InvalidOperationError.class, () -> stock.setQuantity(2));

    assertThat(error.getMessage(),
               is("Cannot update stock when it is deleted"));
  }
}
