package com.deliveryapirest.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

class Gson_ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {
  @Override
  public ZonedDateTime read(JsonReader in) throws IOException {
    return ZonedDateTime.parse(in.nextString());
  }

  @Override
  public void write(JsonWriter out, ZonedDateTime value) throws IOException {
    out.value(value.toString());
  }
}

class Gson_OptionalAdapter<E> extends TypeAdapter<Optional<E>> {
  public static final TypeAdapterFactory FACTORY =
      new TypeAdapterFactory() {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
          Class<T> rawType = (Class<T>) type.getRawType();
          if (rawType != Optional.class) {
            return null;
          }
          final ParameterizedType parameterizedType = (ParameterizedType) type.getType();
          final Type actualType = parameterizedType.getActualTypeArguments()[0];
          final TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(actualType));
          return new Gson_OptionalAdapter(adapter);
        }
      };
  private final TypeAdapter<E> adapter;

  public Gson_OptionalAdapter(TypeAdapter<E> adapter) {
    this.adapter = adapter;
  }

  @Override
  public Optional<E> read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return Optional.empty();
    }
    return Optional.ofNullable(adapter.read(in));
  }

  @Override
  public void write(JsonWriter out, Optional<E> value) throws IOException {
    if (value.isEmpty()) {
      out.nullValue();
    } else {
      out.value(value.get().toString());
    }
  }
}

@Component
public class OrderingConsumer {
  @KafkaListener(topics = "ordering", groupId = "orderingGroup")
  public void checkOrder(String content) {
    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new Gson_ZonedDateTimeAdapter())
            .registerTypeAdapterFactory(Gson_OptionalAdapter.FACTORY)
            .create();

    OrderToConsume order = gson.fromJson(content, OrderToConsume.class);

    System.out.println(content);
  }
}
