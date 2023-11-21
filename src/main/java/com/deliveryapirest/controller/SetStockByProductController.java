package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SetStockByProductController {

  @PostMapping("/product/{id}/stock")
  ResponseEntity<?> setStockByProduct() {
    return ResponseEntity.status(404).body(BadRequestError.make("Product not found"));
  }
}
