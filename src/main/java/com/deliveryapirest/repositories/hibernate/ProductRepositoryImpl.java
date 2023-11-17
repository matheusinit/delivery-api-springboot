package com.deliveryapirest.repositories.hibernate;

import com.deliveryapirest.entities.Product;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryImpl extends ProductRepository, CrudRepository<Product, UUID> {}
