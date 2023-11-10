package com.deliveryapirest.entities;

public class Product {

  public Product() throws Exception {
    throw new Exception("Product name is required");
  }

  public Product(String name, String description) throws Exception {
    throw new Exception("Description cannot be empty, must have at least 10 characters");
  }
}
