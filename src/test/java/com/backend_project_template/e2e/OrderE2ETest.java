package com.backend_project_template.e2e;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("e2e")
public class OrderE2ETest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void configureRestAssured() {
        baseURI = "http://localhost";
        port = 8080;
    }

    @Test
    void shouldRegisterUserAndCreateOrderAndRetrieveIt() {
        String email = "piloutre@test.com";

        // 1. Register user
        String registerBody = """
            {
              "email": "%s",
              "password": "1234",
              "name": "Piloutre"
            }
        """.formatted(email);

        given()
                .contentType("application/json")
                .body(registerBody)
                .when()
                .post("/auth/register")
                .then()
                .statusCode(201);

        // 2. Fetch user ID
        Long userId = given()
                .queryParam("email", email)
                .when()
                .get("/auth/users/by-email")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getLong("id");

        // 3. Create order
        String orderBody = """
            {
              "userId": %d,
              "items": [
                { "productName": "apple", "quantity": 2, "unitPrice": 5 },
                { "productName": "banana", "quantity": 1, "unitPrice": 3 }
              ]
            }
        """.formatted(userId);

        given()
                .contentType("application/json")
                .body(orderBody)
                .when()
                .post("/orders")
                .then()
                .statusCode(201);

        // 4. Fetch orders by email
        given()
                .queryParam("email", email)
                .when()
                .get("/orders/by-email")
                .then()
                .statusCode(200)
                .body("$.size()", is(1))
                .body("[0].items.size()", is(2))
                .body("[0].items[0].productName", equalTo("apple"))
                .body("[0].total", equalTo(13.0f));
    }
}