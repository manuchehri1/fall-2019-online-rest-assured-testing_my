package com.automation.tests.murodil.day06;
import com.automation.utilities.ConfigurationReader;
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
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JsonToCollections {

    @BeforeAll
    public static void  setup(){
        baseURI= ConfigurationReader.getProperty("ORDS.URI");

    }

    @Test
    public void hrApiEmployee_json_to_javaObject(){
        baseURI= ConfigurationReader.getProperty("ORDS.URI");
        
        Response response = given().accept(ContentType.JSON).
                            and().pathParam("employee_id",105).
                            when().get("/employees/{employee_id}").prettyPeek();
        
        Map<String,?> employeeMap =response.body().as(Map.class);
        System.out.println("employeeMap.toString() = " + employeeMap.toString());
        
        String firstName = employeeMap.get("first_name").toString();
        System.out.println("firstName = " + firstName);

        double salary = (Double) employeeMap.get("salary");
        System.out.println("salary = " + salary);
        assertEquals(4800.0,salary,1);
        // delta amount. Difference by delta amount or less
        // values will be considered equal
        
    }
    
    //List< Map < String , Object >>
    @Test
    public void convertAll_spartans_to_list_of_map(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        authentication = basic("admin","admin");
        
        Response response = given().accept(ContentType.JSON).
                            when().get("/spartans/");
        
        List< Map<String , ? >> jsonListOfMap = response.body().as(List.class);
        
        // print all data of first spartan
        System.out.println("jsonListOfMap.get(0) = " + jsonListOfMap.get(0));
        
        Map<String, ? > firstSpartan = jsonListOfMap.get(0);
        System.out.println("firstSpartan = " + firstSpartan);
        String nameOfFirstSpartans = (String) firstSpartan.get("name");
        System.out.println("nameOfFirstSpartans = " + nameOfFirstSpartans);
        int counter = 1;
        for (Map<String,?> spartan: jsonListOfMap){
            System.out.println(counter+" - spartan = "+spartan);
            counter++;
        }
        
        
    }
    @Test
    public void region_data_to_collections(){
        baseURI= ConfigurationReader.getProperty("ORDS.URI");

        Response response = given().accept(ContentType.JSON).
                when().get("/regions/");
        Map< String ,?> dataMap = response.body().as(Map.class);
        System.out.println(dataMap);

        //extract Europe, Americas, Asia from above dataMap
        System.out.println(dataMap.get("items"));
        List<Map<String , ? >> regionList =(List<Map<String,?>>) dataMap.get("items");
        System.out.println(regionList.get(0).get("region_name"));// Europe
        System.out.println(regionList.get(1).get("region_name"));//Americas
        System.out.println(regionList.get(2).get("region_name"));//Asia
    }

    /*
    SERIALIZATION AND DESERIALIZATION
    I use Gson library for this process

    SERIALIZATION
    Java Object --> Json Format

    DESERIALIZATION
    Json --> Java Object





     */
    
    
    
    
}





















