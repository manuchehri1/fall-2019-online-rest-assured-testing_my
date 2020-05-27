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
import org.junit.jupiter.api.Test;

public class APIKey {

    private String apiKey = "bffcde4";

    @BeforeAll
    public static void setup(){
        baseURI ="http://omdbapi.com/";
    }

    @Test
    public void apiKeyTest(){
        String itemToSearch ="Titanic";

        Response response = given().
                                  queryParam("t",itemToSearch).
                                  queryParam("apikey",apiKey).
                            when().
                                       get();
                            response.  then().
                                        assertThat().
                                        statusCode(200).
                                        body("Title",containsString(itemToSearch));

        List<Map<String,String>> ratings = response.jsonPath().getList("Ratings");
        System.out.println("ratings = " + ratings);

    }

    @Test
    public void authenticationApiKeyTest(){
        String itemToSearch ="Titanic";
        // the same test as above but not providing api key
        //
        Response response = given().
                               queryParam("t",itemToSearch).
                               when().
                               get().prettyPeek();
                 response.   then().
                                assertThat().
                                statusCode(401);

    }

}
