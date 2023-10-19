package com.deliveryapirest.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderingConsumer {
  @KafkaListener(topics = "ordering", groupId = "orderingGroup")
  public void checkOrder(String content) {
    System.out.println("Order received");
    System.out.println(content);
  }
}
