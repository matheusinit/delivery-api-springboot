package com.deliveryapirest.controller;

import com.deliveryapirest.data.UpdateProductInput;
import com.deliveryapirest.errors.BadRequestError;
import com.deliveryapirest.services.UpdateProductService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateProductController {

  private final UpdateProductService updateProductService;

  public UpdateProductController(UpdateProductService updateProductService) {
    this.updateProductService = updateProductService;
  }

  @PatchMapping("/product/{id}")
  ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody UpdateProductInput input) {
    try {
      var product = updateProductService.updateProduct(id, input);

      return ResponseEntity.status(HttpStatus.OK).body(product);

    } catch (Exception exception) {
      if (input.name == "") {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                BadRequestError.make(
                    "Name cannot be null and description must be not null, at least one of them"
                        + " must be provided"));
      }

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(
              BadRequestError.make(
                  "Name or description cannot be null, at least one of them must be provided"));
    }
  }
}
