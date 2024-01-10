package com.deliveryapirest.consumer;

import com.deliveryapirest.data.Order;
import com.deliveryapirest.data.OrderStatus;
import com.deliveryapirest.services.RegisterOrderToShipService;
import com.deliveryapirest.typeAdapters.GsonOptionalAdapter;
import com.deliveryapirest.typeAdapters.GsonZonedDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

record OrderToConsume(
    UUID id,
    UUID productId,
    int status,
    ZonedDateTime createdAt,
    Optional<ZonedDateTime> updatedAt,
    Optional<ZonedDateTime> canceledAt) {}

@Component
public class OrderConsumerKafka implements OrderConsumer {

  RegisterOrderToShipService registerOrderToShipService;

  public OrderConsumerKafka(RegisterOrderToShipService registerOrderToShipService) {
    this.registerOrderToShipService = registerOrderToShipService;
  }

  @KafkaListener(
      topics = "ordering",
      groupId = "orderingGroup",
      autoStartup = "${kafka.autostart:false}")
  public void consumeAndRegisterOrder(String content) {
    OrderToConsume orderToConsume = receiveAndSerializeContent(content);

    var order = convertOrderToConsumeToOrderObject(orderToConsume);

    this.registerOrderToShipService.register(order);
  }

  private OrderToConsume receiveAndSerializeContent(String content) {
    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeAdapter())
            .registerTypeAdapterFactory(GsonOptionalAdapter.FACTORY)
            .create();

    var contentInJson = gson.fromJson(content, OrderToConsume.class);

    return contentInJson;
  }

  private Order convertOrderToConsumeToOrderObject(OrderToConsume orderToConsume) {
    OrderStatus orderStatusInEnum = OrderStatus.fromInt(orderToConsume.status());

    var order =
        new Order(
            orderToConsume.id(),
            orderToConsume.productId(),
            1,
            orderStatusInEnum,
            orderToConsume.createdAt(),
            orderToConsume.updatedAt(),
            orderToConsume.canceledAt());

    return order;
  }
}
