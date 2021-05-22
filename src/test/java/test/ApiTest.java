package test;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ApiTest {

    @BeforeAll
    static  void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void successfulSingleUserTest() {
        given()
                .when()
                .get("api/users/10")
                .then()
                .statusCode(200)
                .body("data.id", is(10))
                .body("data.email", is("byron.fields@reqres.in"))
                .body("data.first_name", is("Byron"))
                .body("data.avatar", is("https://reqres.in/img/faces/10-image.jpg"))
                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("support.text", is("To keep ReqRes free, contributions towards server " +
                        "costs are appreciated!"));
    }

    @Test
    void unsuccessfulSingleUserTest() {
        given()
                .when()
                .get("api/users/50")
                .then()
                .statusCode(404);
    }

    @Test
    void successfulListUserTest() {
        given()
                .when()
                .get("api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.id.first()", is(7))
                .body("data.email.first()", is("michael.lawson@reqres.in"))
                .body("data.first_name.first()", is("Michael"))
                .body("data.last_name.first()", is("Lawson"))
                .body("data.avatar.first()", is("https://reqres.in/img/faces/7-image.jpg"))
                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("support.text", is("To keep ReqRes free, contributions towards server " +
                        "costs are appreciated!"));
    }

    @Test
    void successfulListResourcesTest() {
        given()
                .when()
                .get("api/unknown")
                .then()
                .statusCode(200)
                .body("data.id.first()", is(1))
                .body("data.name.first()", is("cerulean"))
                .body("data.year.first()", is(2000))
                .body("data.color.first()", is("#98B2D1"))
                .body("data.pantone_value.first()", is("15-4020"))
                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("support.text", is("To keep ReqRes free, contributions towards server " +
                        "costs are appreciated!"));
    }

    @Test
    void unsuccessfulRegisterTest() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"sydney@fife\" }")
                .when()
                .post("/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
