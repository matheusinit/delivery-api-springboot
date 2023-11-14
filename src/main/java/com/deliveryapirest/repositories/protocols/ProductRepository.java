package com.deliveryapirest.repositories.protocols;

import com.deliveryapirest.entities.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
  public Product save(Product product);

  public Optional<Product> findById(UUID id);
}
