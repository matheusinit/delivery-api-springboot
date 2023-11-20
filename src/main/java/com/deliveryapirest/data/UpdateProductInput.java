package com.deliveryapirest.data;

public class UpdateProductInput {
  public String name;
  public String description;

  public UpdateProductInput(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public UpdateProductInput() {}
}
