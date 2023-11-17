package com.deliveryapirest.services;

import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductService {
  ProductRepository repository;

  public DeleteProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public void delete(UUID id) throws Exception {
    var productValue = this.repository.findById(id);
    var productIsEmpty = productValue.isEmpty();
    var productWasSoftDeleted = !productIsEmpty && productValue.get().getDeletedAt() != null;

    var productWasNotFound = productIsEmpty || productWasSoftDeleted;

    if (productWasNotFound) {
      throw new Exception("Product not found");
    }

    var product = productValue.get();

    product.delete();

    repository.save(product);
  }
}
