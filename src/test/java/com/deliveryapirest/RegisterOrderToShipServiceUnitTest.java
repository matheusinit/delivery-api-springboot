// 1. Producer that will send an order through Kafka
// 2. Consumer will get order and map it to a Java object
// 3. Consumer will pass order object to a Service class to register order to
// ship

// BUSINESS LOGIC OF REGISTER ORDER TO SHIP
// If deleted at of order is not null should not register
// if status order is not SENT should not register

package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.inMemory.InMemoryOrderToShipRepository;
import com.deliveryapirest.services.RegisterOrderToShipService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

enum Status {
  NOT_SENT,
  CANCELLED
}

class RegisterOrderToShipServiceUnitTest {
  @Test
  void ensureWhenStatusProvidedIsNotNotSentThenShouldNotRegister() {
    var order = new Order();
    var repository = new InMemoryOrderToShipRepository();
    var sut = new RegisterOrderToShipService(repository);

    sut.register(order);

    var list = repository.findAll();
    var expected = not(hasItem(Matchers.<OrderToShip>hasProperty("status", is(Status.CANCELLED))));
    assertThat(list.size(), is(0));
    assertThat(list, expected);
  }
}
