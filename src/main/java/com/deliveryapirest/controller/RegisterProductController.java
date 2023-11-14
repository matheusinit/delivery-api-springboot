package com.deliveryapirest.controller;

import com.deliveryapirest.data.RegisterProductInput;
import com.deliveryapirest.errors.BadRequestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterProductController {

  @PostMapping("/product")
  ResponseEntity<?> registerProduct(@RequestBody RegisterProductInput input) {

    if (input.getName() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BadRequestError.make("Product name is required"));
    }

    if (input.getDescription() != "" && input.getDescription().length() < 10) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BadRequestError.make("Description cannot have less than 10 characters"));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            BadRequestError.make("Description cannot be empty, must have at least 10 characters"));
  }
}
