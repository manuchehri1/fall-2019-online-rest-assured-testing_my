package com.automation.tests.day05;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;


public class POJOPractice {
    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }


    @Test
    public void  getUser(){

        Response response = given()
                                .auth().basic("admin","admin").
                            when()
                                .get("/spartans/{id}",614).prettyPeek();

        Spartan spartan = response.as(Spartan.class);

        System.out.println(spartan);


        assertEquals(614, spartan.getId());
        assertEquals("toptop",spartan.getName());
        assertEquals("Male",spartan.getGender());
    }

    @Test
    public void addUser(){
        Spartan spartan = new Spartan("Hasan Jan","Male",1112223338L);
        Gson gson = new Gson();

        String pojoAsJSON = gson.toJson(spartan);
        System.out.println("pojoAsJSON = " + pojoAsJSON);

        Response response = given()
                                .auth().basic("admin","admin")
                                .contentType(ContentType.JSON)
                                .body(spartan).
                            when()
                                .post("/spartans").prettyPeek();
        response. then().assertThat().statusCode(201);

        int userId = response.path("data.id");
        System.out.println("userId = " + userId);

        System.out.println(" -----Delete User----- " );
                            given()
                                .auth().basic("admin","admin").
                            when().
                                delete("/spartans/{id}",userId).prettyPeek().
                            then().
                                assertThat().statusCode(204);

    }

}





















