package com.deliveryapirest.repositories;

import com.deliveryapirest.entities.Stock;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {
  Stock findByProductId(UUID productId);
}
