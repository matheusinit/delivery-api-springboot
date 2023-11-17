package com.deliveryapirest.repositories.protocols;

import com.deliveryapirest.entities.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
  public Product save(Product product);

  public Optional<Product> findById(UUID id);

  public List<Product> findAll();

  public void deleteAll();

  public void softDelete(UUID id);
}
