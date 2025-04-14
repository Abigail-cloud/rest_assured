

package tests;

import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestUtility;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

@Listeners(config.TestListener.class)
public class JsonPlaceholder {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = TestConfig.JSONPLACEHOLDER_BASE_URI;
        TestConfig.setup();
    }

    @Test(description = "Get all posts from JSONPlaceholder")
    public void getAllPosts() {
        Response response = TestUtility.performGet("/posts", TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue());
    }

    @Test(description = "Get a specific post by ID")
    public void getPostById() {
        int postId = 2;
        Response response = TestUtility.performGet("/posts/" + postId, TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("id", equalTo(postId))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test(description = "Create a new post")
    public void createPost() {
        Map<String, Object> post = new HashMap<>();
        post.put("title", "Test Post");
        post.put("body", "This is a test post");
        post.put("userId", 1);

        Response response = TestUtility.performPost("/posts", post, TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 201);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("title", equalTo("Test Post"))
                .body("id", notNullValue());
    }

    @Test(description = "Update an existing post")
    public void updatePost() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("id", 1);
        updates.put("title", "Updated Title");
        updates.put("body", "Updated Body");
        updates.put("userId", 1);

        Response response = TestUtility.performPut("/posts/1", updates, TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("title", equalTo("Updated Title"));
    }

    @Test(description = "Patch an existing post")
    public void patchPost() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Patched Title");

        Response response = TestUtility.performPatch("/posts/1", updates, TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("title", equalTo("Patched Title"));
    }

    @Test(description = "Delete a post")
    public void deletePost() {
        Response response = TestUtility.performDelete("/posts/1", TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
    }
}

/**
 * WITHOUT HTML REPORT ONLY LOGS ON TERMINAL
 *
 * package tests;

import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestUtility;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class JsonPlaceholder {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = TestConfig.JSONPLACEHOLDER_BASE_URI;
        TestConfig.setup();
    }

    @Test
    public void getAllPosts() {
        Response response = TestUtility.performGet("/posts", TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue());
    }

    @Test
    public void getPostById() {
        int postId = 2;
        Response response = TestUtility.performGet("/posts/" + postId, TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("id", equalTo(postId))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    public void createPost() {
        Map<String, Object> post = new HashMap<>();
        post.put("title", "Test Post");
        post.put("body", "This is a test post");
        post.put("userId", 1);

        Response response = TestUtility.performPost("/posts", post, TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 201);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("title", equalTo("Test Post"))
                .body("id", notNullValue());
    }

    @Test
    public void updatePost() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("id", 1);
        updates.put("title", "Updated Title");
        updates.put("body", "Updated Body");
        updates.put("userId", 1);

        Response response = TestUtility.performPut("/posts/1", updates, TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("title", equalTo("Updated Title"));
    }

    @Test
    public void patchPost() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Patched Title");

        Response response = TestUtility.performPatch("/posts/1", updates, TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("title", equalTo("Patched Title"));
    }

    @Test
    public void deletePost() {
        Response response = TestUtility.performDelete("/posts/1", TestConfig.jsonPlaceholderSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
    }
}
 */