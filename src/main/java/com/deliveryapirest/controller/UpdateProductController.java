package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

class UpdateProductInput {
  public String name;
  public String description;
}

@RestController
public class UpdateProductController {

  @PatchMapping("/product/{id}")
  ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody UpdateProductInput input) {

    if (input.name == "") {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(
              BadRequestError.make(
                  "Name cannot be null and description must be not null, at least one of them must"
                      + " be provided"));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            BadRequestError.make(
                "Name or description cannot be null, at least one of them must be provided"));
  }
}
