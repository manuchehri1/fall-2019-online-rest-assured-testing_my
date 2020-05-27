package com.automation.tests.day7;
import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.baseURI;
import org.junit.jupiter.api.Test;
public class BasicAuthentication_Vasil {

    @Test
    public void spartanAuthentication(){
        //in the given part, we provide request specifications
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");

        given().
                auth().basic("user", "user").
                when().
                get("/spartans").prettyPeek().
                then().
                statusCode(200);
    }

    @Test
    public void authorizationTest(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        Spartan spartan = new Spartan("Araz", "Male", 343242342343L);
        given().
                auth().basic("user", "user").
                body(spartan).
                contentType(ContentType.JSON).
                when().
                post("/spartans").prettyPeek().
                then().
                statusCode(403);
        /**
         * user - doesn't have wrights to add, delete or edit users. Only read.
         * admin - has a permission to add new users.
         * 403 - Forbidden access. You logged in, but you are trying to do something that you are not allowed.
         * Authentication problem - you didn't login
         * Authorization problem - you logged in but cannot do some actions.
         */
    }

    @Test
    public void authenticationTest(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        //if don't provide credentials, we must get 401 status code
        get("/spartans").prettyPeek().then().statusCode(401);
    }

    @Test
    public void authenticationTest2(){
        baseURI = "http://practice.cybertekschool.com";

        given().
                auth().basic("admin", "admin").
                when().
                get("/basic_auth").prettyPeek().
                then().
                statusCode(200).
                contentType(ContentType.HTML);
    }
}
