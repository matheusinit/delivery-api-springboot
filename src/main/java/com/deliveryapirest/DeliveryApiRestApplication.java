package com.deliveryapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DeliveryApiRestApplication {
  public static void main(String[] args) {
    SpringApplication.run(DeliveryApiRestApplication.class, args);
  }
}
