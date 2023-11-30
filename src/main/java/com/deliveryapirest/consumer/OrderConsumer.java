package com.deliveryapirest.consumer;

public interface OrderConsumer {
  public void consumeAndRegisterOrder(String content);
}
