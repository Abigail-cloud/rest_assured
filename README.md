# 📘 REST Assured Beginner's Guide

**REST Assured**, is a powerful Java library for testing RESTful APIs. 
---

## 🚀 What is REST Assured?

**REST Assured** is a Java DSL (Domain-Specific Language) that simplifies testing of REST services. It eliminates the need for writing complex boilerplate code to validate HTTP responses.

> ✅ Ideal for automated API testing in Java-based projects.

---

## 🧠 Key Concepts

### 1. HTTP Methods
REST Assured allows you to send the most common HTTP methods:

- **GET**: Retrieve data
- **POST**: Create new resources
- **PUT**: Update existing resources
- **DELETE**: Remove resources

![HTTP Methods](https://dummyimage.com/600x300/87cefa/ffffff&text=GET+POST+PUT+DELETE)

---

### 2. Basic REST Assured Syntax

```java
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SimpleTest {
    public static void main(String[] args) {
        given().
            baseUri("https://jsonplaceholder.typicode.com").
        when().
            get("/posts/1").
        then().
            statusCode(200).
            body("userId", equalTo(1));
    }
}
```

### Breakdown:
- **given()**: Setup request
- **when()**: Define HTTP action
- **then()**: Validate response

---

## 🔧 Setup and Dependencies

To use REST Assured, add the dependency to your `pom.xml` if you're using Maven:

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.0</version>
    <scope>test</scope>
</dependency>
```

> 🛠 You also need JUnit or TestNG for structured test execution.

---

## 🔍 Validating Response Details

### ✅ Status Code
```java
.then().statusCode(200);
```

### ✅ Response Body Content
```java
.then().body("title", containsString("sunt aut"));
```

### ✅ Headers
```java
.then().header("Content-Type", "application/json; charset=utf-8");
```

---

## 🧪 Common Test Scenarios

### 🔁 Sending POST Requests
```java
given().
    header("Content-type", "application/json").
    body("{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }").
when().
    post("/posts").
then().
    statusCode(201);
```

### 🔎 Parsing JSON Response
```java
String title =
    given().
    when().
        get("/posts/1").
    then().
        extract().path("title");
System.out.println(title);
```

---

## 🎯 Best Practices

- Use `before()` methods to set common base URI
- Validate both status code and response body
- Parametrize endpoints and payloads for flexibility
- Integrate with CI/CD pipelines for automation

---

## 🖼 Visual Flow of REST Assured Test

```
[ Setup Request ]
       ↓
[ Send Request ]
       ↓
[ Get Response ]
       ↓
[ Validate Status, Body, Headers ]
```

![REST Assured Flow](https://dummyimage.com/600x300/f08080/ffffff&text=Request+→+Response+→+Validation)

---

## 📚 Resources

- [Official REST Assured Docs](https://rest-assured.io/)
- [JSONPlaceholder (Free API)](https://jsonplaceholder.typicode.com/)

---

## ✅ Summary

REST Assured is a concise, readable, and powerful tool for REST API testing in Java. This guide walked you through:

- What REST Assured is
- Basic syntax and structure
- Setup and dependencies
- Testing with GET, POST, and validations

> 🔄 Keep practicing with real APIs to master it!

---

Great Testing! 🧪✨
