package com.deliveryapirest.producer;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

import com.deliveryapirest.entities.Stock;
import java.util.Map;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

class StockDto {
  public String productId;
  public int quantity;
}

@Component
@Configuration
public class StockProducer {
  KafkaTemplate<String, StockDto> kafkaTemplate;

  public StockProducer() {
    var config = getProducerConfig();
    this.kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(config));
  }

  Map<String, Object> getProducerConfig() {
    var kafkaServer = "localhost:9092";
    var waitForInMs = 10;

    Map<String, Object> config =
        Map.of(
            BOOTSTRAP_SERVERS_CONFIG,
            kafkaServer,
            LINGER_MS_CONFIG,
            waitForInMs,
            KEY_SERIALIZER_CLASS_CONFIG,
            IntegerSerializer.class,
            VALUE_SERIALIZER_CLASS_CONFIG,
            JsonSerializer.class);

    return config;
  }

  @Value("${kafka.topic.stock}")
  String routingKey;

  public void sendStock(Stock stock) {
    StockDto data = new StockDto();
    data.productId = stock.getProductId().toString();
    data.quantity = stock.getQuantity();

    kafkaTemplate.send(routingKey, data);
  }
}
