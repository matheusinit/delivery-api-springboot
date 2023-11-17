package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteProductController {
  ProductRepository repository;

  public DeleteProductController(ProductRepository repository) {
    this.repository = repository;
  }

  @DeleteMapping("/product/{id}")
  ResponseEntity<?> deleteProduct(@PathVariable UUID id) {

    var productValue = this.repository.findById(id);
    var productIsEmpty = productValue.isEmpty();
    var productWasSoftDeleted = !productIsEmpty && productValue.get().getDeletedAt() != null;

    var productWasNotFound = productIsEmpty || productWasSoftDeleted;

    if (productWasNotFound) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(BadRequestError.make("Product not found"));
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(BadRequestError.make("Product cannot be deleted, because was not found"));
  }
}
