package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

class DeliveryStationLocation {
  private String name;
  private String zipCode;
  private Double latitude;

  public DeliveryStationLocation(String name, String zipCode, Double latitude) {
    this.name = name;
    this.zipCode = zipCode;
    this.latitude = latitude;
  }

  public String getName() {
    return name;
  }

  public String getZipCode() {
    return this.zipCode;
  }

  public Double getLatitude() {
    return this.latitude;
  }
}

@RestController()
public class AddDeliveryStationController {
  @PostMapping("/station")
  ResponseEntity<?> addDeliveryStation(
      @RequestBody DeliveryStationLocation deliveryStationLocation) {

    if (deliveryStationLocation.getName() != null && deliveryStationLocation.getZipCode() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BadRequestError.make("Zip code, Latitude and Longitude is required"));
    }

    if (deliveryStationLocation.getName() != null
        && deliveryStationLocation.getZipCode() != null
        && deliveryStationLocation.getLatitude() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BadRequestError.make("Latitude and Longitude is required"));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(BadRequestError.make("Longitude is required"));
  }
}
