package com.deliveryapirest.controller;

import com.deliveryapirest.StockRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class GetStockByProductController {

  @Autowired StockRepository repository;

  @GetMapping("/stock/{productId}")
  ResponseEntity<?> getStockByProduct(@PathVariable UUID productId) {
    class ProductStock {
      public final String id = "1";
      public final String productId = "1";
      public final int quantity = 1;
      public final String createdAt = "2021-01-01T00:00:00";
      public final String updatedAt = null;
      public final String deletedAt = null;
    }

    var stock = this.repository.findByProductId(productId);

    if (stock == null) {
      return ResponseEntity.status(404).build();
    }

    var productStock = new ProductStock();

    return ResponseEntity.ok().body(productStock);
  }
}
