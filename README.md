Below is the complete tutorial (original + expanded) organized as a GitBook. I’ll provide:

SUMMARY.md: The table of contents.
Markdown files for each chapter/section.
Instructions to set up a GitBook.
SUMMARY.md
markdown

Collapse

Wrap

Copy
# Summary

* [Introduction to Rest Assured](#introduction-to-rest-assured)
* [Setting Up Your Project](#setting-up-your-project)
* [Understanding the Reqres API](#understanding-the-reqres-api)
* [Testing REST API Methods](#testing-rest-api-methods)
  * [GET Requests](#testing-rest-api-methods/get-requests)
  * [POST Requests](#testing-rest-api-methods/post-requests)
  * [PUT Requests](#testing-rest-api-methods/put-requests)
  * [PATCH Requests](#testing-rest-api-methods/patch-requests)
  * [DELETE Requests](#testing-rest-api-methods/delete-requests)
* [Advanced Rest Assured Features](#advanced-rest-assured-features)
  * [Query and Path Parameters](#advanced-rest-assured-features/query-and-path-parameters)
  * [JSON Schema Validation](#advanced-rest-assured-features/json-schema-validation)
  * [Authentication](#advanced-rest-assured-features/authentication)
  * [Request and Response Specifications](#advanced-rest-assured-features/request-and-response-specifications)
  * [Response Extraction](#advanced-rest-assured-features/response-extraction)
  * [Testing Error Scenarios](#advanced-rest-assured-features/testing-error-scenarios)
  * [Parallel Testing](#advanced-rest-assured-features/parallel-testing)
* [Best Practices](#best-practices)
* [Debugging and Troubleshooting](#debugging-and-troubleshooting)
* [Practicing with Other APIs](#practicing-with-other-apis)
* [Conclusion](#conclusion)
Markdown Files
introduction-to-rest-assured.md
markdown

Collapse

Wrap

Copy
# Introduction to Rest Assured

Rest Assured is a Java library for testing RESTful APIs. It provides a fluent, readable syntax for making HTTP requests and validating responses, making it ideal for both beginners and experienced testers.

**Why Use Rest Assured?**
- Simplifies API testing with a BDD-style (given-when-then) syntax.
- Supports all HTTP methods (GET, POST, PUT, DELETE, PATCH).
- Integrates with JUnit/TestNG for assertions and reporting.
- Handles JSON/XML responses with built-in parsing.

In this guide, we'll use the [Reqres API](https://reqres.in/) to demonstrate testing all REST methods.
setting-up-your-project.md
markdown

Collapse

Wrap

Copy
# Setting Up Your Project

## Prerequisites
- Java 11 or higher
- Maven
- IDE (IntelliJ, Eclipse, or VS Code)

## Create a Maven Project
1. Create a new Maven project with the following `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>rest-assured-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>5.5.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.11.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>json-path</artifactId>
            <version>5.5.0</version>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>json-schema-validator</artifactId>
            <version>5.5.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.5.0</version>
                <configuration>
                    <parallel>methods</parallel>
                    <threadCount>4</threadCount>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
Import the project into your IDE or run mvn install to download dependencies.
Project Structure
Create src/test/java/com/example/tests for test classes.
Create src/test/resources for schemas or config files.
Verify Setup
Run mvn clean to ensure the project builds correctly.

text

Collapse

Wrap

Copy

#### `understanding-the-reqres-api.md`

```markdown
# Understanding the Reqres API

The [Reqres API](https://reqres.in/) is a free, public API for testing. It supports:

- **GET**: `/api/users?page=2` - List users.
- **GET**: `/api/users/2` - Get a single user.
- **POST**: `/api/users` - Create a user.
- **PUT**: `/api/users/2` - Update a user.
- **PATCH**: `/api/users/2` - Partially update a user.
- **DELETE**: `/api/users/2` - Delete a user.

**Key Features**:
- No authentication required.
- Returns JSON responses.
- Simulates real-world CRUD operations.
testing-rest-api-methods.md
markdown

Collapse

Wrap

Copy
# Testing REST API Methods

We'll create a test class `ReqresApiTests.java` to demonstrate all HTTP methods.

## Setup
```java
package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresApiTests {
    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        requestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .build();
        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectResponseTime(lessThan(3000L))
            .build();
    }
}
GET Requests
List Users
java

Collapse

Wrap

Copy
@Test
public void testGetUsers() {
    given()
        .spec(requestSpec)
        .queryParam("page", 2)
    .when()
        .get("/users")
    .then()
        .spec(responseSpec)
        .body("page", equalTo(2))
        .body("data", hasSize(greaterThan(0)))
        .body("data[0].id", notNullValue())
        .log().all();
}
Single User
java

Collapse

Wrap

Copy
@Test
public void testGetSingleUser() {
    given()
        .spec(requestSpec)
        .pathParam("userId", 2)
    .when()
        .get("/users/{userId}")
    .then()
        .spec(responseSpec)
        .body("data.id", equalTo(2))
        .body("data.email", equalTo("janet.weaver@reqres.in"))
        .body("support.url", containsString("reqres.in"))
        .log().all();
}
POST Requests
java

Collapse

Wrap

Copy
@Test
public void testCreateUser() {
    Map<String, String> user = new HashMap<>();
    user.put("name", "John Doe");
    user.put("job", "Developer");

    given()
        .spec(requestSpec)
        .body(user)
    .when()
        .post("/users")
    .then()
        .statusCode(201)
        .body("name", equalTo("John Doe"))
        .body("job", equalTo("Developer"))
        .body("id", notNullValue())
        .body("createdAt", notNullValue())
        .log().all();
}
PUT Requests
java

Collapse

Wrap

Copy
@Test
public void testUpdateUserPut() {
    Map<String, String> user = new HashMap<>();
    user.put("name", "John Doe Updated");
    user.put("job", "Senior Developer");

    given()
        .spec(requestSpec)
        .body(user)
    .when()
        .put("/users/2")
    .then()
        .spec(responseSpec)
        .body("name", equalTo("John Doe Updated"))
        .body("job", equalTo("Senior Developer"))
        .body("updatedAt", notNullValue())
        .log().all();
}
PATCH Requests
java

Collapse

Wrap

Copy
@Test
public void testUpdateUserPatch() {
    Map<String, String> user = new HashMap<>();
    user.put("name", "John Partial Update");

    given()
        .spec(requestSpec)
        .body(user)
    .when()
        .patch("/users/2")
    .then()
        .spec(responseSpec)
        .body("name", equalTo("John Partial Update"))
        .body("updatedAt", notNullValue())
        .log().all();
}
DELETE Requests
java

Collapse

Wrap

Copy
@Test
public void testDeleteUser() {
    given()
        .spec(requestSpec)
    .when()
        .delete("/users/2")
    .then()
        .statusCode(204)
        .body(emptyOrNullString())
        .log().all();
}
text

Collapse

Wrap

Copy

#### `advanced-rest-assured-features.md`

```markdown
# Advanced Rest Assured Features

## Query and Path Parameters
```java
@Test
public void testGetUserWithQueryAndPathParams() {
    given()
        .spec(requestSpec)
        .queryParam("page", 1)
        .pathParam("userId", 3)
    .when()
        .get("/users/{userId}")
    .then()
        .spec(responseSpec)
        .body("data.id", equalTo(3))
        .log().all();
}
JSON Schema Validation
Create src/test/resources/user-schema.json:

json

Collapse

Wrap

Copy
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "properties": {
    "data": {
      "type": "object",
      "properties": {
        "id": { "type": "integer" },
        "email": { "type": "string" },
        "first_name": { "type": "string" },
        "last_name": { "type": "string" },
        "avatar": { "type": "string" }
      },
      "required": ["id", "email", "first_name", "last_name", "avatar"]
    },
    "support": {
      "type": "object",
      "properties": {
        "url": { "type": "string" },
        "text": { "type": "string" }
      },
      "required": ["url", "text"]
    }
  },
  "required": ["data", "support"]
}
Test:

java

Collapse

Wrap

Copy
@Test
public void testGetUserWithSchemaValidation() {
    given()
        .spec(requestSpec)
    .when()
        .get("/users/2")
    .then()
        .spec(responseSpec)
        .body(matchesJsonSchemaInClasspath("user-schema.json"))
        .log().all();
}
Authentication
java

Collapse

Wrap

Copy
@Test
public void testWithApiKey() {
    given()
        .spec(requestSpec)
        .header("X-API-Key", "dummy-key-123")
    .when()
        .get("/users/2")
    .then()
        .spec(responseSpec)
        .log().all();
}

@Test
public void testWithBasicAuth() {
    given()
        .spec(requestSpec)
        .auth().basic("username", "password")
    .when()
        .get("/users/2")
    .then()
        .spec(responseSpec)
        .log().all();
}
Request and Response Specifications
Defined in setup() (see ).

Response Extraction
java

Collapse

Wrap

Copy
@Test
public void testExtractResponse() {
    Response response = given()
        .spec(requestSpec)
    .when()
        .get("/users/2")
    .then()
        .spec(responseSpec)
        .extract().response();

    String email = response.path("data.email");
    assertEquals("janet.weaver@reqres.in", email);
}
Testing Error Scenarios
java

Collapse

Wrap

Copy
@Test
public void testGetNonExistentUser() {
    given()
        .spec(requestSpec)
    .when()
        .get("/users/999")
    .then()
        .statusCode(404)
        .body(is("{}"))
        .log().all();
}
Parallel Testing
Enable in pom.xml:

xml

Collapse

Wrap

Copy
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.0</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>4</threadCount>
    </configuration>
</plugin>
Create src/test/resources/junit-platform.properties:

properties

Collapse

Wrap

Copy
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent
text

Collapse

Wrap

Copy

#### `best-practices.md`

```markdown
# Best Practices

- **Organize Tests**: Group by feature (e.g., `UserTests`, `AuthTests`).
- **Descriptive Names**: Use `testGetUserById` instead of `test1`.
- **Log Strategically**: Use `log().ifError()` to reduce noise.
- **Validate Response Time**: Add `.responseTime(lessThan(3000L))`.
- **Use Specs**: Reuse request/response configurations.
- **Test Edge Cases**: Include 4xx/5xx scenarios.
- **Report Results**: Integrate with Allure or ExtentReports.

Example:
```java
@Test
public void testGetUsersWithBestPractices() {
    given()
        .spec(requestSpec)
        .log().ifValidationFails()
    .when()
        .get("/users?page=2")
    .then()
        .log().ifError()
        .spec(responseSpec)
        .responseTime(lessThan(3000L))
        .body("data", hasSize(greaterThan(0)));
}
text

Collapse

Wrap

Copy

#### `debugging-and-troubleshooting.md`

```markdown
# Debugging and Troubleshooting

- **Test Failures**:
  - Check logs (`log().all()`).
  - Verify endpoint and payload against [Reqres docs](https://reqres.in/).
  - Ensure internet connectivity.

- **Common Issues**:
  - **404**: Check URL path.
  - **415**: Set `contentType(ContentType.JSON)`.
  - **Assertion Errors**: Compare actual vs. expected in logs.

- **Detailed Logging**:
```java
given()
    .log().all()
.when()
    .get("/users")
.then()
    .log().all();
text

Collapse

Wrap

Copy

#### `practicing-with-other-apis.md`

```markdown
# Practicing with Other APIs

Try these public APIs:
- **JSONPlaceholder** (https://jsonplaceholder.typicode.com/): CRUD for posts, users.
- **Postman Echo** (https://postman-echo.com/): Test headers, params.
- **SWAPI** (https://swapi.dev/): Star Wars data.

**Steps**:
1. Read the API docs.
2. Adapt test cases from this guide.
3. Add auth if required.
conclusion.md
markdown

Collapse

Wrap

Copy
# Conclusion

You’ve learned to:
- Set up Rest Assured with Maven.
- Test GET, POST, PUT, PATCH, DELETE with Reqres API.
- Use advanced features like schema validation, auth, and specs.
- Apply best practices for maintainable tests.

**Next Steps**:
- Test a real-world API.
- Integrate with CI/CD (e.g., Jenkins).
- Explore reporting tools like Allure.

For questions, revisit this guide or experiment with new endpoints!
