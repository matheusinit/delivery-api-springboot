package com.deliveryapirest.controller.deliveryStation;

import com.deliveryapirest.repositories.protocols.DeliveryStationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListDeliveryStationController {
  DeliveryStationRepository repository;

  public ListDeliveryStationController(DeliveryStationRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/station")
  public ResponseEntity<?> getDeliveryStation() {
    var list = this.repository.findAll();
    return ResponseEntity.ok(list);
  }
}
