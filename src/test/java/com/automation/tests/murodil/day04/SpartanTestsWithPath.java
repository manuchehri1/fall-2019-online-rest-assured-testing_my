package com.automation.tests.murodil.day04;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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
public class SpartanTestsWithPath {

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "http://54.224.118.38:8000/api";
    }

    /*
    Given accept type is json
    And path param id is 10
    When user sends a get request to "/spartans/{id}"
    Then status code is 200
    And content-type is "application/json"
    And response payload values match the following
    {
    "id": 353,
    "name": "Kirk",
    "gender": "Male",
    "phone": 3012345679
    }
     */
    
    @Test
    public void spartanGetWith_Json_Test_Using_Path(){
    Response response = given().auth().basic("admin","admin").
                         accept(ContentType.JSON).
                         and().pathParam("id",353).
                         when().get("/spartans/{id}");
    
        int id = response.body().path("id");
        System.out.println("id = " + id);
        String name =  response.body().path("name");
        System.out.println("name = " + name);
        String gender = response.path("gender");
        System.out.println("gender = " + gender);
        long phoneNum = response.body().path("phone");
        System.out.println("phoneNum = " + phoneNum);

        assertEquals(353,id);
        assertEquals("Kirk",name);
        assertEquals("Male",gender);
        assertEquals(3012345679L,phoneNum);

        assertEquals(200,response.statusCode());

    }

    @Test
    public void getAllSpartans_using_path_with_list(){
        Response response = given().auth().basic("admin","admin").
                             get("/spartans/");
        // extracting first id                  
        int firstID = response.path("id[0]");
        System.out.println("firstID = " + firstID);
        // extracting first name
        String firstName = response.path("name[0]");
        System.out.println("firstName = " + firstName);
        // extracting the last name
        String lastName = response.path("name[-1]");
        System.out.println("lastName = " + lastName);
        
        // extract all first names and print out

        List<String> names = response.path("name");
        System.out.println("names = " + names);
        
        List<Object> phoneNumbers = response.path("phone");
        //System.out.println("phoneNumbers = " + phoneNumbers);
        for (Object eachPhoneNum : phoneNumbers) {
            System.out.println("eachPhoneNum = " + eachPhoneNum);
        }
        
    }
















}
