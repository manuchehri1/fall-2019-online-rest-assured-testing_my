package com.automation.tests.murodil.day04;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpartansTestWithParameters2 {


    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://54.224.118.38:8000/api";
    }


    /**
     * Given accept type is XML
     * And query parameter values are :
     * gender|Female
     * nameContains|e
     * When user sends GET request to /api/spartans/search
     * Then response status code should be 406
     * And response content-type: application/json;charset=UTF-8
     * And "Could not find acceptable representation" should be in response payload
     *
     */
    @Test
    @DisplayName("Verify error message with negative scenario")
    public void invalid_header_test_With_parameters(){

        Response response = given().auth().basic("admin","admin").
                              accept(ContentType.XML).
                              queryParam("gender","Female").and().
                              queryParam("nameContains","e").
                            when().get("/spartans/search");
        Response response2 = given().auth().basic("admin","admin").
                              accept(ContentType.XML).
                              queryParams("gender","Female","nameContains","e").
                             when().get("/spartans/search");

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("gender","Female");
        paramsMap.put("nameContains","e");

        Response response3 = given().auth().basic("admin","admin").
                                accept(ContentType.XML).
                                queryParams(paramsMap).
                            when().get("/spartans/search");

        // Response validation with JUnit assertion
        assertEquals(406,response.statusCode());
        assertEquals(406,response2.statusCode());
        assertEquals(406,response3.statusCode());

        assertTrue(response.body().asString().contains("Could not find acceptable representation"));
        assertTrue(response2.body().asString().contains("Could not find acceptable representation"));
        assertTrue(response3.body().asString().contains("Could not find acceptable representation"));
    }
    /*
    // How to navigate through Json and read values from keys
    // or parsing JSON body (payload) -> convert to the type you need

    // Populars one:
    // -- using path() method
    // -- using JsonPath()
    // -- using deserialization to a collection
    // -- using deserialization to a java custom object (pojo)

    //JSON -> JavaScript Object Notation

    {
    "id": 353,
    "name": "Kirk",
    "gender": "Male",
    "phone": 3012345679
    }

    response.body().path("id") => 10
    response.body().path("name") => Kirk
    response.body().path("gender") => Male or response.path("gender") => Male

    int id = response.body().path("id"); // 10
    String firstName = response.body().path("name"); // Kirk


    */




}
