package com.deliveryapirest.controller;

import com.deliveryapirest.errors.InternalServerError;
import com.deliveryapirest.errors.InvalidOperationError;
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
    try {
      if (id == null) {
        return ResponseEntity.badRequest().body(new Error("Order id is required"));
      }

      var orderValue = this.repository.findById(id);

      if (orderValue.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new Error("Order not found with given id"));
      }

      var order = orderValue.get();

      order.setOutForDelivery();

      return ResponseEntity.ok().body(order);
    } catch (Exception exception) {
      if (exception instanceof InvalidOperationError) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(InvalidOperationError.make(exception.getMessage()));
      }

      var error =
          InternalServerError.make("An internal server error occured. Please try again later.");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
  }
}
