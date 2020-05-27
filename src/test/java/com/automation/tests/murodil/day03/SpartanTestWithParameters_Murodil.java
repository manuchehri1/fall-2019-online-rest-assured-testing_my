package com.automation.tests.murodil.day03;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SpartanTestWithParameters_Murodil {

    @BeforeAll
    public static void  setUp(){
        // set Base URI so that we don't have to type this every time
        RestAssured.baseURI = "http://54.224.118.38:8000/api";
    }

    /**
     * Given no headers provided
     * When Users sends request to /api/hello
     *Then response status code should be 200
     *And Content type header should be "text/plain;charset=UTF-8"
     * And header should contain date
     * And Content-Length should be 17
     *And body should be "Hello from Sparta"
     */
    @Test
    public void helloTest(){
        //request
       Response response = given().auth().basic("admin","admin").
                                get("/hello").prettyPeek();
       // header
        Headers headers = response.getHeaders();
        String contentType = headers.getValue("Content-Type");
        String date = headers.getValue("date");

        assertEquals(200, response.statusCode());
        assertEquals("text/plain;charset=UTF-8", response.contentType());
        assertTrue(response.headers().hasHeaderWithName("date"));
        assertEquals("17",response.getHeader("Content-Length"));
        assertEquals("Hello from Sparta",response.getBody().asString());

    }

    /** /api/spartans/{id}
     * Positive Scenario: sent GET request with valid ID that exists in database
     * or wrong data type:
     * expected status code: 404
     * error message: Spartan not found
     *
     * Given accept type is Json
     * And Id parameter value is 5
     * When user sends GET request to /api/spartans/{id}
     * Then response status code should be 200
     * And response content-type: application/json;charset=UTF-8
     * And "sevimm" should be in response payload
     */


    @Test
    public void getSpartanByID_Positive_Path_Param_Test(){

        Response response = given().
                            auth().basic("admin","admin").
                            pathParam("id",369).
                            get("/spartans/{id}");

        response.prettyPeek();
        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8",response.contentType());
        assertTrue(response.body().asString().contains("Karim"));

    }

}
