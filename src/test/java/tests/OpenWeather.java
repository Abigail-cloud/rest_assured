package tests;

import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestUtility;

import static org.hamcrest.Matchers.*;

public class OpenWeather {
    private final String city = "London";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = TestConfig.OPENWEATHER_BASE_URI;
        TestConfig.setup();
    }

    @Test
    public void getCurrentWeather() {
        Response response = TestUtility.performGet("/weather?q=" + city, TestConfig.openWeatherSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("name", equalTo(city))
                .body("main.temp", notNullValue())
                .body("weather[0].description", notNullValue());
    }

    @Test
    public void getWeatherByCoordinates() {
        double lat = 51.5074; // London coordinates
        double lon = -0.1278;
        Response response = TestUtility.performGet("/weather?lat=" + lat + "&lon=" + lon, TestConfig.openWeatherSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("coord.lat", closeTo(lat, 0.1))
                .body("coord.lon", closeTo(lon, 0.1))
                .body("main.temp", notNullValue());
    }

    // OpenWeather doesn't support POST, PUT, PATCH, DELETE for weather data
    // Adding another GET for forecast to demonstrate variety
    @Test
    public void getForecast() {
        Response response = TestUtility.performGet("/forecast?q=" + city, TestConfig.openWeatherSpec);
        TestUtility.printTheResponse(response);
        TestUtility.verifyStatusCode(response, 200);
        TestUtility.verifyResponseTime(response, 4500);
        response.then()
                .body("city.name", equalTo(city))
                .body("list", hasSize(greaterThan(0)))
                .body("list[0].main.temp", notNullValue());
    }
}