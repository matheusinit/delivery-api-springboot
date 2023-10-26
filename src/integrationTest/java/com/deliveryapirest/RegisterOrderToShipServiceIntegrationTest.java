package com.deliveryapirest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.entities.OrderToShip;
import com.deliveryapirest.repositories.protocols.OrderToShipRepository;
import com.deliveryapirest.services.RegisterOrderToShipService;
import java.util.Collection;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterOrderToShipServiceIntegrationTest {

  @Autowired OrderToShipRepository repository;

  @Test
  void givenOrderWithStatusDifferentThanNotSent_whenRegister_thenShouldNotRegister() {
    var sut = new RegisterOrderToShipService(repository);
    var order = new Order(OrderStatus.CANCELLED, 1);

    sut.register(order);

    var list = repository.findAll();
    var expected =
        not(hasItem(Matchers.<OrderToShip>hasProperty("status", is(OrderStatus.CANCELLED))));
    var listSize = ((Collection<?>) list).size();
    assertThat(listSize, is(0));
    assertThat(list, expected);
  }
}
