package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SetStockByProductController {
  ProductRepository productRepository;

  public SetStockByProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @PostMapping("/product/{id}/stock")
  ResponseEntity<?> setStockByProduct(@PathVariable UUID id) {
    var productValue = productRepository.findById(id);

    if (productValue.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(BadRequestError.make("Product not found"));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(BadRequestError.make("Quantity must be provided"));
  }
}
