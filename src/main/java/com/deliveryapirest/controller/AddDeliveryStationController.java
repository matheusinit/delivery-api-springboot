package com.deliveryapirest.controller;

import com.deliveryapirest.data.DeliveryStationInput;
import com.deliveryapirest.entities.DeliveryStation;
import com.deliveryapirest.errors.InternalServerError;
import com.deliveryapirest.errors.MissingFieldError;
import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AddDeliveryStationController {

  DeliveryStationRepository repository;

  public AddDeliveryStationController(DeliveryStationRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/station")
  public ResponseEntity<?> addDeliveryStation(
      @RequestBody DeliveryStationInput deliveryStationInput) {

    try {
      var name = deliveryStationInput.getName();
      var zipCode = deliveryStationInput.getZipCode();
      var latitude = deliveryStationInput.getLatitude();
      var longitude = deliveryStationInput.getLongitude();

      var deliveryStation = new DeliveryStation(name, zipCode, latitude, longitude);

      repository.save(deliveryStation);

      return ResponseEntity.status(HttpStatus.CREATED).body(deliveryStation);
    } catch (Exception exception) {
      if (exception instanceof MissingFieldError) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(MissingFieldError.make(exception.getMessage()));
      }

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              new InternalServerError("An internal server error occured. Please try again later."));
    }
  }
}
