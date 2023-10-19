package com.deliveryapirest;

import com.deliveryapirest.entities.Stock;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import org.springframework.data.repository.CrudRepository;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {
  Stock findByProductId(UUID productId);
}
