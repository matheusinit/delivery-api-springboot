package com.deliveryapirest.controller;

import com.deliveryapirest.errors.BadRequestError;
import com.deliveryapirest.services.DeleteProductService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteProductController {
  DeleteProductService deleteProductService;

  public DeleteProductController(DeleteProductService deleteProductService) {
    this.deleteProductService = deleteProductService;
  }

  @DeleteMapping("/product/{id}")
  ResponseEntity<?> deleteProduct(@PathVariable UUID id) {

    try {
      deleteProductService.delete(id);

      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(BadRequestError.make("Product not found"));
    }
  }
}
