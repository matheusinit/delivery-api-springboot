package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import com.deliveryapirest.repositories.protocols.ProductRepository;
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

  private final ProductRepository repository;

  public UpdateProductController(ProductRepository repository) {
    this.repository = repository;
  }

  @PatchMapping("/product/{id}")
  ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody UpdateProductInput input) {
    try {
      var productValue = repository.findById(id);

      var product = productValue.get();

      if (input.description != null) {
        product.setDescription(input.description);
      }

      if (input.name != null) {
        product.setName(input.name);
      }

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
