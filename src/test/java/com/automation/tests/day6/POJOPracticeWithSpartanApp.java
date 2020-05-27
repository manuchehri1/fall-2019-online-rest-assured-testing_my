package com.automation.tests.day6;

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

public class POJOPracticeWithSpartanApp {


    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        authentication = basic("admin","admin");
    }

    @Test
    public void addSpartanTest(){
        /**
         * {
         *   "gender": "Male",
         *   "name": "Nursultan",
         *   "phone": 2223334445
         * }
         */

        Map<String,Object> spartan =  new HashMap<>();
        spartan.put("gender","Male");
        spartan.put("name","Nursultan");
        spartan.put("phone","121241444414");

        Response response = given().auth().basic("admin","admin").
                            and().contentType(ContentType.JSON).
                            and().accept(ContentType.JSON).
                            and().body(spartan).
                            when().post("/spartans").prettyPeek();

        assertEquals(201,response.statusCode());
        assertEquals("A Spartan is Born!",response.path("success"));

        //deserialization
        Spartan spartanResponse = response.jsonPath().getObject("data", Spartan.class);
        Map<String, Object> spartanResponseMap = response.jsonPath().getObject("data", Map.class);
        System.out.println(spartanResponse);
        System.out.println(spartanResponseMap);
        //spartanResponse is a Spartan
        System.out.println(spartanResponse instanceof Spartan);// must be true


    }


    @Test
    public void updateSpartanUpdate(){
        int userToUpdate = 1;
        String name = "Ali";
        //HTTP PUT request to update existing record, for example existing spartan
        //PUT - requires to provide all parameter in body
        Spartan spartan = new Spartan(name,"Male",1234567890);

        // get spartan from web service
        Spartan spartanToUpdate = given().auth().basic("admin","admin").
                                  and().accept(ContentType.JSON).
                                  when().get("/spartans/{id}",userToUpdate).as(Spartan.class);
        System.out.println("Before update"+spartanToUpdate);
        spartanToUpdate.setName(name);
        System.out.println("After update"+spartanToUpdate);

        Response response = given().auth().basic("admin","admin").
                            and().contentType(ContentType.JSON).
                            and().accept(ContentType.JSON).
                            and().body(spartan).
                            when().put("/spartans/{id}",userToUpdate).prettyPeek();
        assertEquals(204,response.statusCode());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Response response1 = given().auth().basic("admin","admin").
                             when().get("/spartans/{id}",userToUpdate).prettyPeek();
        assertEquals(200,response1.statusCode());
        assertEquals(name,response1.path("name")); // verify name is correct after update



    }



    @Test
    @DisplayName("Verify that user can perform PARCH request")
    public void patchUserTest(){
        //PATCH : partial update of existing record

        int userId = 203;

        Response response0 = given().accept(ContentType.JSON).get("/spartans");

        List<Spartan> allSpartans = response0.jsonPath().getList("", Spartan.class);
        // path is empty because json array does have any name (like items in HR json array name)

        Random random = new Random();// to get random

        int randomNum = random.nextInt(allSpartans.size());
        int randomUserID = allSpartans.get(randomNum).getId();
        System.out.println("NAME BEFORE: " + allSpartans.get(randomNum).getName());
        userId = randomUserID;//to assign random user id
        System.out.println(allSpartans);


        Map<String,Object> update = new HashMap<>();
        update.put("name","Mandela");

        Response response = given().contentType(ContentType.JSON).
                            and().body(update).
                            when().patch("/spartans/{id}",userId).prettyPeek();
        response.then().assertThat().statusCode(204);
        // after we sent PATCH request, lets make sure that name is updated
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Response response1 = given().accept(ContentType.JSON).
                            when().get("/spartans/{id}",userId).prettyPeek();
        response1.then().assertThat().statusCode(200).
                and().body("name",is("Mandela"));


    }















}
