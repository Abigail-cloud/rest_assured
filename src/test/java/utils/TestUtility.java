package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import static io.restassured.RestAssured.given;

public class TestUtility {
    public static void verifyStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        System.out.println("Status Code: " + actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
    }

    public static void verifyResponseTime(Response response, long maxTime) {
        long responseTime = response.getTime();
        System.out.println("Response Time: " + responseTime);
        Assert.assertTrue(responseTime < maxTime, "Response time " + responseTime + "ms exceeds maxTime " + maxTime + "ms");
    }

    public static void printTheResponse(Response response) {
        System.out.println("Response: ");
        System.out.println(response.getBody().asPrettyString());
    }

    public static Response performGet(String endpoint, RequestSpecification spec) {
        return given()
                .spec(spec)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response performPost(String endpoint, Object body, RequestSpecification spec) {
        return given()
                .spec(spec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response performPatch(String endpoint, Object body, RequestSpecification spec) {
        return given()
                .spec(spec)
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response performPut(String endpoint, Object body, RequestSpecification spec) {
        return given()
                .spec(spec)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response performDelete(String endpoint, RequestSpecification spec) {
        return given()
                .spec(spec)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}