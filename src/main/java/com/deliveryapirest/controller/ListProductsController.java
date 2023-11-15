package com.deliveryapirest.controller;

import com.deliveryapirest.repositories.protocols.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListProductsController {
  ProductRepository repository;

  public ListProductsController(ProductRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/product")
  ResponseEntity<?> listProducts() {
    var products = this.repository.findAll();

    return ResponseEntity.ok(products);
  }
}
