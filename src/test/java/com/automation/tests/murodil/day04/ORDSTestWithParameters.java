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
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class ORDSTestWithParameters {


    @BeforeAll
    public static void setUp(){
        baseURI="http://54.224.118.38:1000/ords/hr";
    }



    @Test
    public void getCountriesUsingPathParameter(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id","AR")
                .when().get("/countries/{id}");
        response.prettyPeek();

        assertEquals(200,response.statusCode());
        assertEquals("application/json",response.contentType());
        assertTrue(response.body().asString().contains("Argentina"));

    }

    @Test
    public void getEmployeeUsingPathParameter(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",100)
                .when().get("/employees/{id}");
        response.prettyPeek();

        assertEquals(200,response.statusCode());
        assertEquals("application/json",response.contentType());
        assertTrue(response.body().asString().contains("Steven"));

    }




}
