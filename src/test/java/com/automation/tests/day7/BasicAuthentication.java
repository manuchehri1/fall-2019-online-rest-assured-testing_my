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

public class BasicAuthentication {
    @BeforeAll
    public static void setup(){


    }

    @Test
    public void spartanAuthentication(){


        //in the given part, we provide request specification
        given().
                auth().basic("admin","admin").
        when().
                get("/spartans").prettyPeek().
        then().
                statusCode(200);
    }


    @Test
    public void authorizationTest(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        Spartan spartan = new Spartan("Karim","Male",1233211231);

        given().
                auth().basic("user","user").
                and().body(spartan).
                and().contentType(ContentType.JSON).
        when().
                post("/spartans").prettyPeek().
        then().
                statusCode(403);
        // user - doesn't have right to add, delete or edit users. Only read

    }
}
