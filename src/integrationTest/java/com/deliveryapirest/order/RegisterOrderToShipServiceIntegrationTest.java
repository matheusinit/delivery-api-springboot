package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.protocols.OrderToShipRepository;
import com.deliveryapirest.services.RegisterOrderToShipService;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterOrderToShipServiceIntegrationTest {

  @Autowired OrderToShipRepository repository;

  @BeforeEach
  void clearOrderToShipRecords() {
    repository.deleteAll();
  }

  @Test
  void
  givenOrderWithStatusDifferentThanNotSent_whenRegister_thenShouldNotRegister() {
    var sut = new RegisterOrderToShipService(repository);
    var order = new Order(OrderStatus.CANCELLED, 1);

    sut.register(order);

    var list = repository.findAll();
    var expected = not(hasItem(Matchers.<OrderToShip>hasProperty(
        "status", is(OrderStatus.CANCELLED))));
    var listSize = ((Collection<?>)list).size();
    assertThat(listSize, is(0));
    assertThat(list, expected);
  }

  @Test
  void givenQuantityEqualZero_whenRegister_thenShouldNotRegister() {
    var sut = new RegisterOrderToShipService(repository);
    var quantity = 0;
    var order = new Order(OrderStatus.NOT_SENT, quantity);

    sut.register(order);

    var list = repository.findAll();
    var expected = not(
        hasItem(Matchers.<OrderToShip>hasProperty("quantity", is(quantity))));
    var listSize = ((Collection<?>)list).size();
    assertThat(listSize, is(0));
    assertThat(list, expected);
  }

  @Test
  void givenCanceledAtNotNull_whenRegister_thenShouldNotRegister() {
    var sut = new RegisterOrderToShipService(repository);
    var canceledAt = Optional.of(ZonedDateTime.now());
    var order = new Order(canceledAt);

    sut.register(order);

    var list = repository.findAll();
    var expected = not(hasItem(
        Matchers.<OrderToShip>hasProperty("canceledAt", is(canceledAt))));
    var listSize = ((Collection<?>)list).size();
    assertThat(listSize, is(0));
    assertThat(list, expected);
  }
}
