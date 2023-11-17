package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteProductController {

  @DeleteMapping("/product/{id}")
  ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(BadRequestError.make("Product not found"));
  }
}
