package com.deliveryapirest.controller;

import com.deliveryapirest.errors.InternalServerError;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<?> listProducts() {
    try {
      var products = this.repository.findAll();

      return ResponseEntity.ok(products);

    } catch (Exception exception) {
      var errorMessage =
          InternalServerError.make("An internal server error occured. Please try again later.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
  }
}
