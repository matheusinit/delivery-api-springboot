package com.deliveryapirest.producer;

import com.deliveryapirest.entities.Stock;

public interface StockProducer {
  public void sendStockToQueue(Stock stock);
}
