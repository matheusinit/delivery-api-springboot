package com.deliveryapirest.entities;

public class StationDelivery {

  public StationDelivery(String name, String zipCode, Double latitude, Double longitude)
      throws Exception {
    if (name == null && zipCode == null && latitude == null && longitude == null) {
      throw new Exception("Name, Zip code, Latitude and Longitude is required");
    }

    throw new Exception("Zip code, latitude and longitude is required");
  }
}
