package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import com.deliveryapirest.errors.InvalidOperationError;
import com.deliveryapirest.services.SetStockByProductService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

class SetStockByProductInput {
  public Integer quantity;
}

@RestController
class SetStockByProductController {
  SetStockByProductService service;

  public SetStockByProductController(SetStockByProductService service) {
    this.service = service;
  }

  @PostMapping("/product/{id}/stock")
  ResponseEntity<?> setStockByProduct(
      @PathVariable UUID id, @RequestBody SetStockByProductInput input) {
    try {
      var stock = service.setStockByProduct(id, input.quantity);

      return ResponseEntity.status(HttpStatus.OK).body(stock);
    } catch (Exception exception) {
      if (exception.getMessage() == "Product not found") {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(InvalidOperationError.make(exception.getMessage()));
      }

      if (exception instanceof InvalidOperationError) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(InvalidOperationError.make(exception.getMessage()));
      }

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BadRequestError.make("Quantity must be 0 or positive"));
    }
  }
}
