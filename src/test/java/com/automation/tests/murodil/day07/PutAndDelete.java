package com.automation.tests.murodil.day07;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.RestAssured;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PutAndDelete {


    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        authentication = basic("admin","admin");
    }

    @Test
    public void updateSpartan_PUT_Test(){
        Map<String,Object> requestMap = new HashMap<>();

        requestMap.put("name","Karim");
        requestMap.put("gender","Male");
        requestMap.put("phone",2001002223L);

        given().
                contentType(ContentType.JSON).
        and().
                body(requestMap).
        when().
                put("/spartans/{id}",245).
        then().
                assertThat().statusCode(204);


    }

    @Test
    public void deleteSpartan_DELETE_Request_Test(){
        Response response = get("/spartans");
        List<Integer> allSpartans = response.jsonPath().getList("");

        Random random = new Random();
        int idToDelete = random.nextInt(allSpartans.size())-1;
        System.out.println("idToDelete = " + idToDelete);
        when().
                delete("/spartans/{id}",idToDelete).prettyPeek().
        then().
                assertThat().statusCode(204);


    }




}
