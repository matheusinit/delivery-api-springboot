package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RegisterProductController {

  @PostMapping("/product")
  ResponseEntity<?> registerProduct() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(BadRequestError.make("Product name is required"));
  }
}
