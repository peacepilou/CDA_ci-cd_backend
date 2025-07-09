package com.backend_project_template.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("e2e")
public class OrderE2ETest {

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
                .contentType(ContentType.JSON)
                .body(registerBody)
                .when()
                .post("http://localhost:8080/auth/register")
                .then()
                .statusCode(201);

        // 2. Fetch user ID
        Long userId = given()
                .queryParam("email", email)
                .when()
                .get("http://localhost:8080/auth/users/by-email")
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
                .contentType(ContentType.JSON)
                .body(orderBody)
                .when()
                .post("http://localhost:8080/orders")
                .then()
                .statusCode(201);

        // 4. Fetch orders by email
        given()
                .queryParam("email", email)
                .when()
                .get("http://localhost:8080/orders/by-email")
                .then()
                .statusCode(200)
                .body("$.size()", is(1))
                .body("[0].items.size()", is(2))
                .body("[0].items[0].productName", equalTo("apple"))
                .body("[0].total", equalTo(13.0f));
    }
}
