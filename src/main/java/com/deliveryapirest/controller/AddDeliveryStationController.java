package com.deliveryapirest.controller;

import com.deliveryapirest.data.DeliveryStationInput;
import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.errors.BadRequestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AddDeliveryStationController {
  @PostMapping("/station")
  ResponseEntity<?> addDeliveryStation(@RequestBody DeliveryStationInput deliveryStationInput) {

    try {
      var name = deliveryStationInput.getName();
      var zipCode = deliveryStationInput.getZipCode();
      var latitude = deliveryStationInput.getLatitude();
      var longitude = deliveryStationInput.getLongitude();

      var deliveryStation = new DeliveryStation(name, zipCode, latitude, longitude);

      return ResponseEntity.status(HttpStatus.CREATED).body(deliveryStation);
    } catch (Exception exception) {
      if (deliveryStationInput.getName() != null && deliveryStationInput.getZipCode() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BadRequestError.make("Zip code, Latitude and Longitude is required"));
      }

      if (deliveryStationInput.getName() != null
          && deliveryStationInput.getZipCode() != null
          && deliveryStationInput.getLatitude() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BadRequestError.make("Latitude and Longitude is required"));
      }

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BadRequestError.make("Longitude is required"));
    }
  }
}
