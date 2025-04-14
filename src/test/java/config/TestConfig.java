package config;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class TestConfig {
    public static final String JSONPLACEHOLDER_BASE_URI = "https://jsonplaceholder.typicode.com/";
    public static final String OPENWEATHER_BASE_URI = "https://api.openweathermap.org/data/2.5/";
    public static final String REQRES_BASE_URI = "https://reqres.in/api/";

    public static RequestSpecification jsonPlaceholderSpec;
    public static RequestSpecification openWeatherSpec;
    public static RequestSpecification reqResSpec;

    public static void setup() {
        // JSONPlaceholder API
        jsonPlaceholderSpec = new RequestSpecBuilder()
                .setBaseUri(JSONPLACEHOLDER_BASE_URI)
                .setContentType("application/json")
                .build();

        // OpenWeather API
        String apiKey = System.getenv("OPENWEATHER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("OPENWEATHER_API_KEY environment variable not set");
        }
        openWeatherSpec = new RequestSpecBuilder()
                .setBaseUri(OPENWEATHER_BASE_URI)
                .addQueryParam("appid", apiKey)
                .build();

        // ReqRes API
        reqResSpec = new RequestSpecBuilder()
                .setBaseUri(REQRES_BASE_URI)
                .setContentType("application/json")
                .build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}