package com.deliveryapirest.repositories.hibernate;

import com.deliveryapirest.entities.Product;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepositoryImpl extends ProductRepository, CrudRepository<Product, UUID> {
  @Transactional
  @Modifying
  @Query("UPDATE Product p SET deletedAt = INSTANT WHERE p.id = ?1 AND p.deletedAt IS NULL")
  public void softDelete(UUID id);
}
