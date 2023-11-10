package com.deliveryapirest.entities;

public class Product {

  public Product() throws Exception {
    throw new Exception("Product name is required");
  }

  public Product(String name) throws Exception {
    throw new Exception("Price is required");
  }
}
