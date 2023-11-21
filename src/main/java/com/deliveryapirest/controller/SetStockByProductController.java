package com.deliveryapirest.controller;

import com.deliveryapirest.entities.Stock;
import com.deliveryapirest.errors.BadRequestError;
import com.deliveryapirest.repositories.hibernate.StockRepository;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

class SetStockByProductInput {
  public Integer quantity;
}

@RestController
class SetStockByProductController {
  ProductRepository productRepository;
  StockRepository stockRepository;

  public SetStockByProductController(
      ProductRepository productRepository, StockRepository stockRepository) {
    this.productRepository = productRepository;
    this.stockRepository = stockRepository;
  }

  @PostMapping("/product/{id}/stock")
  ResponseEntity<?> setStockByProduct(
      @PathVariable UUID id, @RequestBody SetStockByProductInput input) {
    try {
      var productValue = productRepository.findById(id);

      if (productValue.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(BadRequestError.make("Product not found"));
      }

      if (input.quantity == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BadRequestError.make("Quantity must be provided"));
      }

      var product = productValue.get();
      var stock = stockRepository.findByProductId(product.getId());

      if (stock != null) {
        stock.setQuantity(input.quantity);
        stockRepository.save(stock);

        return ResponseEntity.status(HttpStatus.OK).body(stock);
      }

      stock = new Stock(product.getId(), input.quantity);

      return ResponseEntity.status(HttpStatus.OK).body(stock);
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BadRequestError.make("Quantity must be 0 or positive"));
    }
  }
}
