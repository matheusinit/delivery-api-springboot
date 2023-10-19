package com.deliveryapirest.controller;

import com.deliveryapirest.StockRepository;
import com.deliveryapirest.errors.StockNotFoundError;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class GetStockByProductController {

  @Autowired StockRepository repository;

  @GetMapping("/stock/{productId}")
  ResponseEntity<?> getStockByProduct(@PathVariable UUID productId) {

    var stock = this.repository.findByProductId(productId);

    if (stock == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(StockNotFoundError.returnError("Stock with given ID not found"));
    }

    return ResponseEntity.status(HttpStatus.OK).body(stock);
  }
}
