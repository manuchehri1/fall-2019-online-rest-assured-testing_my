package com.automation.tests.day2;

import io.restassured.response.Response;
import  org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class ORDSTests {
    String BASE_URL = "http://54.224.118.38:1000/ords/hr";

    @Test
    @DisplayName("Get list of all employees")
    public void getAllEmployees() {
        //response can be saved in the Response object
        //prettyPeek() - method that prints response info in nice format
        //also ths method returns Response object
        //response contains body, header and status line
        //body (payload) - contains content that we requested from the web service
        //header - contains meta data
        Response response = given().baseUri(BASE_URL).
                            when().get("/employees").prettyPeek();

        /**
         * RestAssured request has similar structure to BDD scenarios:
         * Start building the request part of the test
         *
         * given() - used for request setup and authentication
         * Syntactic sugar,
         * when() - to specify type of HTTP request: get, put, post, delete, patch, head, etc...
         * then() - to verify response, perform assertions
         */

    }


    @Test
    @DisplayName("Get employee under specific ID")
    public void  getOneEmployee(){
        Response response = given().baseUri(BASE_URL).
                            when().get("/employees/{id}",100).prettyPeek();
    }

    /**
     * - create a @Test getAllRegionsTest
     * - Send a get request to AllRegions API endpoint
     * - print status code
     * - print content type
     * - pretty print response JSON
     * - verify that status code is 200
     * - verify that content type is "application/json"
     * - verify that json response body contains "Americas"
     * - verify that json response body contains "Europe"
     */

    @Test
    public void getAllRegionsTest(){
        Response response = given().baseUri(BASE_URL).when().get("/regions");
        System.out.println(response.getStatusCode());
        System.out.println(response.contentType());
        response.prettyPeek();
        Assertions.assertEquals(200,response.statusCode());
        Assertions.assertTrue(response.contentType().contains("application/json"));
        Assertions.assertTrue(response.getBody().asString().contains("Americas"));
        Assertions.assertTrue(response.getBody().asString().contains("Europe"));

    }
    @Test
    public void getAllCountriesTest(){
        Response response = given().baseUri(BASE_URL).
                            when().get("/countries");
        Assertions.assertTrue(200==response.statusCode());
    }
    @Test
    public void getAllCountriesTest2(){
        given().
                baseUri(BASE_URL).
        when().
                get("/countries").prettyPeek().
        then().
                statusCode(200);
    }


}