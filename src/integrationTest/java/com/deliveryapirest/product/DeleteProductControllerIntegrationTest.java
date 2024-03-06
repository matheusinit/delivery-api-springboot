package com.deliveryapirest.product;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.deliveryapirest.entities.Product;
import com.deliveryapirest.repositories.protocols.ProductRepository;
import io.restassured.RestAssured;
import java.util.UUID;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteProductControllerIntegrationTest {
  @LocalServerPort private Integer port;

  @Autowired ProductRepository repository;

  @BeforeAll
  void setup() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void givenNonExistentId_whenDeleteProduct_thenReturnNotFound() {
    var id = UUID.randomUUID();

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .when()
                       .delete("/product/" + id)
                       .then()
                       .extract()
                       .response();

    assertThat(response.getStatusCode(), is(404));
    assertThat(response.getBody().jsonPath().get("message"),
               is("Product not found"));
  }

  @Test
  void givenAProductAlreadyDeleted_whenDeleteProduct_thenReturnNotFound()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    repository.save(product);
    var id = product.getId();
    product.delete();
    repository.save(product);

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .when()
                       .delete("/product/" + id)
                       .then()
                       .extract()
                       .response();

    assertThat(response.getStatusCode(), is(404));
    assertThat(response.getBody().jsonPath().get("message"),
               is("Product not found"));
  }

  @Test
  void givenAProductNotDeleted_whenDeleteProduct_thenReturnNoContent()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    var productSaved = repository.save(product);
    var id = productSaved.getId();

    var response = RestAssured.given()
                       .accept("application/json")
                       .contentType("application/json")
                       .when()
                       .delete("/product/" + id)
                       .then()
                       .extract()
                       .response();

    assertThat(response.getStatusCode(), is(204));
  }

  @Test
  void
  givenAProductNotDeleted_whenDeleteProduct_thenShouldHaveDeletedAtDefinedAtDatabase()
      throws Exception {
    var faker = new Faker();
    var name = faker.commerce().productName();
    var description = faker.lorem().maxLengthSentence(10);
    var product = new Product(name, description);
    var productSaved = repository.save(product);
    var id = productSaved.getId();
    RestAssured.given()
        .accept("application/json")
        .contentType("application/json")
        .when()
        .delete("/product/" + id)
        .then()
        .extract()
        .response();

    var productDeleted = repository.findById(id).get();

    assertThat(productDeleted.getDeletedAt(), notNullValue());
  }
}
