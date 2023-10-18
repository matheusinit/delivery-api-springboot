package com.deliveryapirest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class GetStockByProductController {

  @GetMapping("/stock/{productId}")
  ResponseEntity<?> getStockByProduct(@PathVariable String productId) {
    return ResponseEntity.ok().body(null);
  }
}
