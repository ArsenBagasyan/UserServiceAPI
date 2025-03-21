package clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public static Response post(RequestSpecification requestSpecification, String endpoint) {
        return given()
                .spec(requestSpecification)
                .post(endpoint);
    }

    public static Response get(RequestSpecification requestSpecification, String endpoint) {
        return given()
                .spec(requestSpecification)
                .get(endpoint);
    }

    public static Response delete(RequestSpecification requestSpecification, String endpoint) {
        return given()
                .spec(requestSpecification)
                .delete(endpoint);
    }

    public static Response patch(RequestSpecification requestSpecification, String endpoint) {
        return given()
                .spec(requestSpecification)
                .patch(endpoint);
    }
}