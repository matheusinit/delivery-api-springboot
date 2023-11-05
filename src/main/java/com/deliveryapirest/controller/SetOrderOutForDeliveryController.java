package com.deliveryapirest.controller;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetOrderOutForDeliveryController {

  @PostMapping("/order/{id}/delivery")
  public ResponseEntity<?> setOrderOutForDelivery(@PathVariable UUID id) {
    if (id == null) {
      return ResponseEntity.badRequest().body(new Error("Order id is required"));
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new Error("Order not found with given id"));
  }
}
