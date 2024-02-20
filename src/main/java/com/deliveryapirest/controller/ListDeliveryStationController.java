package com.deliveryapirest.controller;

import com.deliveryapirest.entities.DeliveryStation;
import java.util.Vector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListDeliveryStationController {

  @GetMapping("/station")
  public ResponseEntity<?> getDeliveryStation() {
    var list = new Vector<DeliveryStation>();
    list.add(new DeliveryStation());
    return ResponseEntity.ok(list);
  }
}
