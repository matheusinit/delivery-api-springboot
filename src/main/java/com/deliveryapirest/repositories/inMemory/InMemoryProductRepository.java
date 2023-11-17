package com.deliveryapirest.repositories.inMemory;

import com.deliveryapirest.entities.Product;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {

  List<Product> products;

  @Override
  public Product save(Product product) {
    this.products.add(product);

    return products.get(products.size() - 1);
  }

  @Override
  public Optional<Product> findById(UUID id) {
    return products.stream().filter(p -> p.getId().equals(id)).findFirst();
  }

  @Override
  public List<Product> findAll() {
    return products;
  }

  @Override
  public void deleteAll() {
    products.clear();
  }

  @Override
  public void softDelete(UUID id) {
    var productValue = products.stream().filter(p -> p.getId().equals(id)).findFirst();
    var productFound = productValue.get();
    var index = products.indexOf(productFound);

    productFound.delete();

    products.set(index, productFound);
  }
}
