package com.deliveryapirest.entities;

public class StationDelivery {

  public StationDelivery(String name, String zipCode, Double latitude, Double longitude)
      throws Exception {
    throw new Exception("Zip code, latitude and longitude is required");
  }
}
