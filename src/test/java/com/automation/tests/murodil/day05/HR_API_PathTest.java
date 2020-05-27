package com.automation.tests.murodil.day05;
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


import static io.restassured.RestAssured.baseURI;

public class HR_API_PathTest {

    @BeforeAll
    public static void setup(){

    baseURI="http://54.224.118.38:1000/ords/hr";

    }

    /**
     * Warm up task:
     * ORDS:
     * - Given accept type is Json
     * - Path param value- US
     * - When users sends request to /countries
     * - Then status code is 200
     * - And country_id is US
     * - And Country_name is United States of America
     * - And Region_id is 2
     */
    @Test
    public void countryTest(){
        Response response = given().accept(ContentType.JSON).
                            and().pathParam("country_id","US").
                            when().get("/countries/{country_id}").prettyPeek();
        int statusCode = response.statusCode();
        String countryID = response.body().path("country_id");
        int regionID = response.body().path("region_id");

        assertEquals(200,statusCode);
        assertEquals("US",countryID);
        assertEquals(2,regionID);

        String links1 = response.body().path("links.href[1]");
        System.out.println("links1 = " + links1);
        List<String > links = response.body().path("links.href");
        System.out.println("links = " + links);

    }

    /**
     * Warm up task 2:
     * ORDS:
     * - Given accept type is Json
     * - query param value - q = {"department_id":"80"}
     * - When users sends request to /employees
     * - Then status code is 200
     * - And all job_id starts with SA
     * - And all department_id starts with 80
     * - count is 25
     */

    @Test
    public void  employeeTest(){
        Response response = given().accept(ContentType.JSON).
                            and().queryParam("q","{\"department_id\":\"80\"}").
                            when().get("/employees").prettyPeek();
        int statusCode = response.statusCode();
        List<String> job_id = response.body().path("items.job_id");
        List<Integer> department_id =response.body().path("items.department_id");
        int count = response.body().path("count");
        for (String eachJobID : job_id) {
            assertTrue(eachJobID.startsWith("SA"));
        }
        job_id.forEach(id->assertTrue(id.startsWith("SA")));
        for (Integer eachDepartment_ID : department_id) {
            eachDepartment_ID=80;
        }
        department_id.forEach(id -> assertEquals(80,id));
        assertEquals(200,statusCode);

        assertEquals(25,count);
    }







}
