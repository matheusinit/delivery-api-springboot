package com.deliveryapirest.entities;

import com.deliveryapirest.errors.MissingFieldError;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "delivery_station")
public class DeliveryStation {

  @Id private UUID id;
  private String name;
  private String zipCode;
  private Double latitude;
  private Double longitude;
  @CreationTimestamp private Instant createdAt;

  @Column(insertable = false)
  @UpdateTimestamp
  private Instant updatedAt;

  private Instant deletedAt;

  public DeliveryStation(String name, String zipCode, Double latitude, Double longitude)
      throws Exception {
    if (name == null && zipCode == null && latitude == null && longitude == null) {
      throw new MissingFieldError("Name, Zip code, Latitude and Longitude is required");
    }

    if (zipCode == null && latitude == null && longitude == null) {
      throw new MissingFieldError("Zip code, Latitude and Longitude is required");
    }

    if (latitude == null && longitude == null) {
      throw new MissingFieldError("Latitude and Longitude is required");
    }

    if (longitude == null) {
      throw new MissingFieldError("Longitude is required");
    }

    this.id = UUID.randomUUID();
    this.name = name;
    this.zipCode = zipCode;
    this.latitude = latitude;
    this.longitude = longitude;
    this.createdAt = Instant.now();
    this.deletedAt = null;
  }

  public DeliveryStation() {}

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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Instant getDeletedAt() {
    return deletedAt;
  }
}
