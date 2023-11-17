package com.deliveryapirest.services;

import com.deliveryapirest.data.UpdateProductInput;
import com.deliveryapirest.entities.Product;
import com.deliveryapirest.errors.EmptyDescriptionError;
import com.deliveryapirest.errors.InvalidFieldError;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductService {
  ProductRepository repository;

  public UpdateProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public Product updateProduct(UUID id, UpdateProductInput input)
      throws InvalidFieldError, EmptyDescriptionError {
    var productValue = repository.findById(id);

    var product = productValue.get();

    if (input.description != null) {
      product.setDescription(input.description);
    }

    if (input.name != null) {
      product.setName(input.name);
    }

    return product;
  }
}
