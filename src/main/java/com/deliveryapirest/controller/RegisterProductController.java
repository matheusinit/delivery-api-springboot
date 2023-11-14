package com.deliveryapirest.controller;

import com.deliveryapirest.data.RegisterProductInput;
import com.deliveryapirest.entities.Product;
import com.deliveryapirest.errors.BadRequestError;
import com.deliveryapirest.errors.InvalidFieldError;
import com.deliveryapirest.errors.MissingFieldError;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterProductController {
  private ProductRepository repository;

  public RegisterProductController(ProductRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/product")
  ResponseEntity<?> registerProduct(@RequestBody RegisterProductInput input) {
    try {
      var product = new Product(input.getName(), input.getDescription());

      repository.save(product);

      return ResponseEntity.status(HttpStatus.CREATED).body(product);

    } catch (Exception exception) {
      if (exception instanceof MissingFieldError) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BadRequestError.make(exception.getMessage()));
      }

      if (exception instanceof InvalidFieldError) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BadRequestError.make(exception.getMessage()));
      }

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BadRequestError.make(exception.getMessage()));
    }
  }
}
