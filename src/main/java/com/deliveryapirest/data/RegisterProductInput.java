package com.deliveryapirest.data;

public class RegisterProductInput {
  private String name;
  private String description;

  public RegisterProductInput() {}

  public RegisterProductInput(String name) {
    this.name = name;
  }

  public RegisterProductInput(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}
