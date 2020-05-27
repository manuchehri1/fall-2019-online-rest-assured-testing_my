package com.automation.tests.day2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;


public class SpartanTests {

    String Base_URL = "http://54.224.118.38:8000";
    String username = "admin";
    String password = "admin";

    @Test
    public void getAllSpartans(){
        //401 unauthorized
      given().
         auth().basic("admin","admin").
              baseUri(Base_URL).
      when().
         get("/api/spartans").prettyPeek().
      then().statusCode(200);


    }

    // how to add spartan


    @Test
    @DisplayName("Add new spartan")
    public void addSpartan(){
        File jsonFile = new File(System.getProperty("user.dir")+"/spartan.json");

        String body ="{\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"name\": \"DDT\",\n" +
                "  \"phone\": 1312223334\n" +
                "}";

        given().
                contentType(ContentType.JSON).auth().
                basic("admin","admin").body(body).
                baseUri(Base_URL).
        when().
                post("/api/spartans").prettyPeek().
        then().
                statusCode(201);
    }


    // how to delete a spartan
    @Test
    @DisplayName("Delete some spartan")
    public void deleteSpartanTest(){
        given().
                auth().basic(username,password).
                baseUri(Base_URL).
        when().
                delete("/api/spartans/{id}",135).prettyPeek().
        then().
                statusCode(204);
    }

}
