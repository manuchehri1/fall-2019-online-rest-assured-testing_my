package com.automation.tests.murodil.day07;

import org.junit.jupiter.api.BeforeAll;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.RestAssured;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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
public class authorizationAndAuthentication {

    private String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NiIsImF1ZCI6InN0dWRlbnQtdGVhbS1sZWFkZXIifQ.lEfjcu6RpBfcZ4qWthzZU8uH8fX4FCJFfxBnPNgh4Mo";

    @BeforeAll
    public static void setUp(){

        baseURI = "https://cybertek-reservation-api-qa3.herokuapp.com/api";
    }

    @Test
    public void getAllCampusesUsing_access_token(){
 Response response =    given().
                            header("Authorization",accessToken).
                            accept(ContentType.JSON).
                        when().get("/campuses").prettyPeek();

     String nameOfRoom111 = response.jsonPath().getString("clusters[0].rooms[0].name[0]");
        System.out.println("nameOfRoom111 = " + nameOfRoom111);
     
    }
}
