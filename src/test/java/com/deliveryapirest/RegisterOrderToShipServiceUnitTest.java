// 1. Producer that will send an order through Kafka
// 2. Consumer will get order and map it to a Java object
// 3. Consumer will pass order object to a Service class to register order to
// ship

package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.inMemory.InMemoryOrderToShipRepository;
import com.deliveryapirest.services.RegisterOrderToShipService;
import java.time.ZonedDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class RegisterOrderToShipServiceUnitTest {

  private InMemoryOrderToShipRepository makeRepository() {
    var repository = new InMemoryOrderToShipRepository();

    return repository;
  }

  private RegisterOrderToShipService makeSut(InMemoryOrderToShipRepository repository) {
    return new RegisterOrderToShipService(repository);
  }

  @Test
  void ensureWhenStatusProvidedIsNotNotSentThenShouldNotRegister() {
    var quantity = 1;
    var order = new Order(OrderStatus.CANCELLED, quantity);
    var repository = makeRepository();
    var sut = makeSut(repository);

    sut.register(order);

    var list = repository.findAll();
    var expected =
        not(hasItem(Matchers.<OrderToShip>hasProperty("status", is(OrderStatus.CANCELLED))));
    assertThat(list.size(), is(0));
    assertThat(list, expected);
  }

  @Test
  void ensureWhenDeletedAtProvidedIsNotNullThenShouldNotRegister() {
    var deletedAt = ZonedDateTime.now();
    var order = new Order(deletedAt);
    var repository = makeRepository();
    var sut = makeSut(repository);

    sut.register(order);

    var list = repository.findAll();
    var expected = not(hasItem(Matchers.<OrderToShip>hasProperty("deletedAt", is(deletedAt))));
    assertThat(list.size(), is(0));
    assertThat(list, expected);
  }

  @Test
  void ensureWhenQuantityProvidedIsEqual0ThenShouldNotRegister() {
    var quantity = 0;
    var order = new Order(quantity);
    var repository = makeRepository();
    var sut = makeSut(repository);

    sut.register(order);

    var list = repository.findAll();
    var expected = not(hasItem(Matchers.<OrderToShip>hasProperty("quantity", is(quantity))));
    assertThat(list.size(), is(0));
    assertThat(list, expected);
  }

  @Test
  void
      ensureWhenThereIsItemsRegistered_and_orderProvidedHasDeletedAtNotNull_thenShouldNotRegister() {
    var deletedAt = ZonedDateTime.now();
    var order = new Order(deletedAt);
    var repository = makeRepository();
    repository.save(new OrderToShip());
    var sut = makeSut(repository);

    sut.register(order);

    var list = repository.findAll();
    var expected = not(hasItem(Matchers.<OrderToShip>hasProperty("deletedAt", is(deletedAt))));
    assertThat(list, expected);
  }

  @Test
  void ensureWhenOrderWithStatusNotSentIsProvided_thenShouldRegister() {
    var quantity = 1;
    var order = new Order(OrderStatus.NOT_SENT, quantity);
    var repository = makeRepository();
    var sut = makeSut(repository);

    sut.register(order);

    var list = repository.findAll();
    var expected = hasItem(Matchers.<OrderToShip>hasProperty("status", is(OrderStatus.NOT_SENT)));
    assertThat(list.size(), is(1));
    assertThat(list, expected);
  }
}
