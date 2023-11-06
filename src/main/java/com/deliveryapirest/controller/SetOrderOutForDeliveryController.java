package com.deliveryapirest.controller;

import com.deliveryapirest.repositories.protocols.OrderToShipRepository;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetOrderOutForDeliveryController {

  private final OrderToShipRepository repository;

  public SetOrderOutForDeliveryController(OrderToShipRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/order/{id}/delivery")
  public ResponseEntity<?> setOrderOutForDelivery(@PathVariable UUID id) {
    if (id == null) {
      return ResponseEntity.badRequest().body(new Error("Order id is required"));
    }

    var order = this.repository.findById(id);

    if (order.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new Error("Order not found with given id"));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new Error("Order is cancelled. Cannot set it out for delivery!"));
  }
}
