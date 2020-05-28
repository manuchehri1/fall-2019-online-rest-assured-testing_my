package com.automation.tests.day8;
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

public class BearerAuthentication {


    private String token ="Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMDIzIiwiYXVkIjoidGVhY2hlciJ9.9q6jNOQqbPtA7_68BMRbQrVnXfzx7368-5OsqZMe5Ik";

    @BeforeAll
    public static void setUp(){

        baseURI = "https://cybertek-reservation-api-qa3.herokuapp.com";
    }

    @Test
    public void getTokenAccess(){
        Response response = given().
                                    queryParam("email","teacherva5@gmail.com").
                                    queryParam("password","maxpayne").
                            when().
                                    get("/sign").prettyPeek();
        String accessToken = response.jsonPath().getString("accessToken");
        System.out.println(accessToken);

    }


    @Test
    public void getAllStudents(){
        Response response = given().
                                    auth().oauth2(token).
                            and().
                                    accept(ContentType.JSON).
                            when().
                                    get("/api/students");
        List<Map<String,?>> studentsInfo = response.jsonPath().getList("");
        System.out.println("studentsInfo = " + studentsInfo);
    }

    @Test
    @DisplayName("Negative test: attempt to retrieve list of rooms without authentication token")
    public void getRoomsTest(){
        // here were are not providing authentication to get the rooms
        // so ideally we should get 401 status code
        // we get 422 because they put it so but it is ok
        given().get("/api/rooms").prettyPeek().
        then().statusCode(422);
    }

    @Test
    @DisplayName("Positive Test")
    public void  getRoomTest2(){
        // 1. Request : to get token
        Response response = given().
                queryParam("email","teacherva5@gmail.com").
                queryParam("password","maxpayne").
                when().
                get("/sign");
        String accessToken = response.jsonPath().getString("accessToken");

        given().
                auth().oauth2(accessToken).
        and().
                accept(ContentType.JSON).
        when().
                get("/api/rooms").prettyPeek().
        then().
                statusCode(200);

    }

    public String getToken(){
        Response response = given().
                queryParam("email","teacherva5@gmail.com").
                queryParam("password","maxpayne").
                when().
                get("/sign");
        String accessToken = response.jsonPath().getString("accessToken");
        return accessToken;
    }

    public String getToken(String email, String password){
        Response response = given().
                queryParam("email",email).
                queryParam("password",password).
                when().
                get("/sign");
        String accessToken = response.jsonPath().getString("accessToken");
        return accessToken;
    }

}
