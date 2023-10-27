package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AddDeliveryStationController {
  @PostMapping("/station")
  ResponseEntity<?> addDeliveryStation() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(BadRequestError.make("Zip code, Latitude and Longitude is required"));
  }
}
