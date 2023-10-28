package com.deliveryapirest.entities;

public class DeliveryStation {

  private String name;
  private String zipCode;
  private Double latitude;
  private Double longitude;

  public DeliveryStation(String name, String zipCode, Double latitude, Double longitude)
      throws Exception {
    if (name == null && zipCode == null && latitude == null && longitude == null) {
      throw new Exception("Name, Zip code, Latitude and Longitude is required");
    }

    if (zipCode == null && latitude == null && longitude == null) {
      throw new Exception("Zip code, Latitude and Longitude is required");
    }

    if (latitude == null && longitude == null) {
      throw new Exception("Latitude and Longitude is required");
    }

    if (longitude == null) {
      throw new Exception("Longitude is required");
    }

    this.name = name;
    this.zipCode = zipCode;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getName() {
    return name;
  }

  public String getZipCode() {
    return zipCode;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }
}