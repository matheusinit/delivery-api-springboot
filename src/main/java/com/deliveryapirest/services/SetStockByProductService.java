package com.deliveryapirest.services;

import com.deliveryapirest.entities.Stock;
import com.deliveryapirest.errors.InvalidOperationError;
import com.deliveryapirest.producer.StockProducer;
import com.deliveryapirest.repositories.hibernate.StockRepository;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetStockByProductService {
  ProductRepository productRepository;
  StockRepository stockRepository;
  StockProducer stockProducer;

  public SetStockByProductService(
      ProductRepository productRepository,
      StockRepository stockRepository,
      StockProducer stockProducer) {
    this.productRepository = productRepository;
    this.stockRepository = stockRepository;
    this.stockProducer = stockProducer;
  }

  @Transactional
  public Stock setStockByProduct(UUID id, Integer quantity) throws InvalidOperationError {
    var productValue = productRepository.findById(id);

    if (productValue.isEmpty()) {
      throw new InvalidOperationError("Product not found");
    }

    if (quantity == null) {
      throw new InvalidOperationError("Quantity must be provided");
    }

    var product = productValue.get();
    var stock = stockRepository.findByProductId(product.getId());

    if (stock != null) {
      stock.setQuantity(quantity);
      stockRepository.save(stock);
      stockProducer.sendStockToQueue(stock);

      return stock;
    }

    stock = new Stock(product.getId(), quantity);
    stockRepository.save(stock);

    stockProducer.sendStockToQueue(stock);

    return stock;
  }
}
