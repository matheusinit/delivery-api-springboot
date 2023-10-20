package com.deliveryapirest.consumer;

import com.deliveryapirest.typeAdapters.GsonOptionalAdapter;
import com.deliveryapirest.typeAdapters.GsonZonedDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

record OrderToConsume(
    String id,
    String productId,
    String status,
    ZonedDateTime createdAt,
    Optional<ZonedDateTime> updatedAt,
    Optional<ZonedDateTime> canceledAt) {}

@Component
public class OrderingConsumer {
  @KafkaListener(topics = "ordering", groupId = "orderingGroup")
  public void checkOrder(String content) {
    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeAdapter())
            .registerTypeAdapterFactory(GsonOptionalAdapter.FACTORY)
            .create();

    OrderToConsume order = gson.fromJson(content, OrderToConsume.class);

    System.out.println(content);
  }
}
