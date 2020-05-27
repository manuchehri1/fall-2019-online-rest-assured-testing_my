package com.automation.tests.murodil.day05;
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
public class HamcrestMatchersAPI_Test {
    /**
     * {
     *     "id": 692,
     *     "name": "Helen",
     *     "gender": "Female",
     *     "phone": 1234512345
     * }
     */
    @Test
    public void getSpartans_And_Asserting_Using_Hamcrest(){
        RestAssured.baseURI = "http://54.224.118.38:8000/api";

            given().auth().basic("admin","admin").
            and().accept(ContentType.JSON).
            when().get("/spartans/{id}",692).
            then().assertThat().statusCode(200).
            and().assertThat().contentType("application/json;charset=UTF-8").
            and().assertThat().body("id",equalTo(692),
                    "name",equalTo("Helen"),
                                            "gender",equalTo("Female"),
                                                "phone",equalTo(1234512345));
            


    }

    /**
     * We can also use Java Data Structure/ Collection to store the json file data 
     * and manipulate with them 
     *    Response Json Data:
     *    {
     *           "id": 692,
     *           "name": "Helen",
     *           "gender": "Female",
     *           "phone": 1234512345
     *     }
     *
     *    Map < String, Object> dataMap = response.body().as(Map.class);
     *    dataMap.get("name") --> Helen
     * 
     */
    
    @Test
    public void get_Spartans_Using_Map_Structure(){
        RestAssured.baseURI = "http://54.224.118.38:8000/api";
        
        Response response = given().auth().basic("admin","admin").
                            and().accept(ContentType.JSON).
                            when().get("/spartans/{id}",692);
        
        Map<String,Object> spartanMap = response.body().as(Map.class);
        System.out.println("dataMap = " + spartanMap);
        assertEquals(200,response.statusCode());
        assertEquals(692f,spartanMap.get("id"));
        assertEquals("Helen",spartanMap.get("name"));
        assertEquals("Female",spartanMap.get("gender"));
        assertEquals(1234512345,spartanMap.get("phone"));

        
    }

}
