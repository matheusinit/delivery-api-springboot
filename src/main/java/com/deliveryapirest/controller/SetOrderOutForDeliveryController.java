package com.deliveryapirest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetOrderOutForDeliveryController {

  @PostMapping("/order/{id}/delivery")
  public ResponseEntity<?> setOrderOutForDelivery() {
    return ResponseEntity.badRequest().body(new Error("Order id is required"));
  }
}
