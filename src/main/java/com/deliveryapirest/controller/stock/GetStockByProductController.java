package com.deliveryapirest.controller.stock;

import com.deliveryapirest.errors.StockNotFoundError;
import com.deliveryapirest.repositories.hibernate.StockRepository;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class GetStockByProductController {

  StockRepository repository;

  public GetStockByProductController(StockRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/product/{productId}/stock")
  ResponseEntity<?> getStockByProduct(@PathVariable UUID productId) {

    var stock = this.repository.findByProductId(productId);

    if (stock == null)
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(StockNotFoundError.make("Stock with given ID not found"));

    return ResponseEntity.status(HttpStatus.OK).body(stock);
  }
}
