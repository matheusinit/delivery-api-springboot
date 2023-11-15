package com.deliveryapirest.controller;

import com.deliveryapirest.entities.Product;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListProductsController {

  @GetMapping("/product")
  ResponseEntity<?> listProducts() {
    var productsArray = new ArrayList<Product>();

    var product = new Product();
    var product2 = new Product();

    productsArray.add(product);
    productsArray.add(product2);

    return ResponseEntity.ok(productsArray);
  }
}
