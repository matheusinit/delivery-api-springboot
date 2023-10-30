package com.deliveryapirest.data;

public class DeliveryStationInput {
  private String name;
  private String zipCode;
  private Double latitude;
  private Double longitude;

  public DeliveryStationInput(String name, String zipCode, Double latitude, Double longitude) {
    this.name = name;
    this.zipCode = zipCode;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getName() {
    return name;
  }

  public String getZipCode() {
    return this.zipCode;
  }

  public Double getLatitude() {
    return this.latitude;
  }

  public Double getLongitude() {
    return this.longitude;
  }
}
