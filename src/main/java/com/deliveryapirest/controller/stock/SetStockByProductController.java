package com.deliveryapirest.controller.stock;

import com.deliveryapirest.data.SetStockByProductInput;
import com.deliveryapirest.errors.InternalServerError;
import com.deliveryapirest.errors.InvalidOperationError;
import com.deliveryapirest.services.SetStockByProductService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetStockByProductController {
  SetStockByProductService service;

  public SetStockByProductController(SetStockByProductService service) {
    this.service = service;
  }

  @PostMapping("/product/{id}/stock")
  public ResponseEntity<?>
  setStockByProduct(@PathVariable UUID id,
                    @RequestBody SetStockByProductInput input) {
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

      var error = InternalServerError.make(
          "An internal server error occured. Please try again later.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(error);
    }
  }
}
