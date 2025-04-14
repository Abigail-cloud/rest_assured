/** with html report*/

package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class TestUtility {
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Initialize test in ExtentReports
    public static void startTest(String testName, String description) {
        ExtentTest test = config.ExtentReportConfig.getInstance().createTest(testName, description);
        extentTest.set(test);
    }

    // End test and flush report
    public static void endTest() {
        config.ExtentReportConfig.getInstance().flush();
    }

    public static void verifyStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        System.out.println("Status Code: " + actualStatusCode);
        extentTest.get().log(Status.INFO, "Status Code: " + actualStatusCode);
        try {
            Assert.assertEquals(actualStatusCode, expectedStatusCode,
                    "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
            extentTest.get().log(Status.PASS, "Status code verification passed: " + actualStatusCode);
        } catch (AssertionError e) {
            extentTest.get().log(Status.FAIL, "Status code verification failed: " + e.getMessage());
            throw e;
        }
    }

    public static void verifyResponseTime(Response response, long maxTime) {
        long responseTime = response.getTime();
        System.out.println("Response Time: " + responseTime);
        extentTest.get().log(Status.INFO, "Response Time: " + responseTime + "ms");
        try {
            Assert.assertTrue(responseTime < maxTime, "Response time " + responseTime + "ms exceeds maxTime " + maxTime + "ms");
            extentTest.get().log(Status.PASS, "Response time verification passed: " + responseTime + "ms");
        } catch (AssertionError e) {
            extentTest.get().log(Status.FAIL, "Response time verification failed: " + e.getMessage());
            throw e;
        }
    }

    public static void printTheResponse(Response response) {
        String responseBody = response.getBody().asPrettyString();
        System.out.println("Response: ");
        System.out.println(responseBody);
        extentTest.get().log(Status.INFO, "<pre>" + responseBody + "</pre>");
    }

    public static Response performGet(String endpoint, io.restassured.specification.RequestSpecification spec) {
        Response response = given()
                .spec(spec)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
        extentTest.get().log(Status.INFO, "Performed GET request to: " + endpoint);
        return response;
    }

    public static Response performPost(String endpoint, Object body, io.restassured.specification.RequestSpecification spec) {
        Response response = given()
                .spec(spec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
        extentTest.get().log(Status.INFO, "Performed POST request to: " + endpoint + " with body: <pre>" + body + "</pre>");
        return response;
    }

    public static Response performPatch(String endpoint, Object body, io.restassured.specification.RequestSpecification spec) {
        Response response = given()
                .spec(spec)
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
        extentTest.get().log(Status.INFO, "Performed PATCH request to: " + endpoint + " with body: <pre>" + body + "</pre>");
        return response;
    }

    public static Response performPut(String endpoint, Object body, io.restassured.specification.RequestSpecification spec) {
        Response response = given()
                .spec(spec)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
        extentTest.get().log(Status.INFO, "Performed PUT request to: " + endpoint + " with body: <pre>" + body + "</pre>");
        return response;
    }

    public static Response performDelete(String endpoint, io.restassured.specification.RequestSpecification spec) {
        Response response = given()
                .spec(spec)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
        extentTest.get().log(Status.INFO, "Performed DELETE request to: " + endpoint);
        return response;
    }
}



/**
Here is without the HTML REPORT
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
 */