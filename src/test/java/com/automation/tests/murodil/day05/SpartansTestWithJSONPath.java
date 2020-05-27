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
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class SpartansTestWithJSONPath {

    @BeforeAll
    public static void setUp(){


    }

    /** Ways to navigate JSON and extract values:
     * --> It used GPath syntax (Groovy)
     * --> JsonPath / XMLPath
     *
     *
     */


    /**
     * {
     *     "id": 692,
     *     "name": "Helen",
     *     "gender": "Female",
     *     "phone": 1234512345
     *   }
     *   pathParam id = 692
     *   accept JSON
     *   status code 200
     *   content type
     */

    @Test
    public void spartansInfoUsingJsonPath(){
        RestAssured.baseURI = "http://54.224.118.38:8000/api";
        Response response = given().auth().basic("admin","admin").
                            accept(ContentType.JSON).
                            and().pathParam("id",692).
                            when().get("/spartans/{id}");
        assertEquals(200,response.statusCode());


        JsonPath json = response.jsonPath();
        //JsonPath json = response.body().jsonPath();
        JsonPath json2 = new JsonPath(response.body().asString());
        // getObject works for all type of data
        int id = json.getInt("id");
        String name = json.getString("name");
        String gender = json.getString("gender");
        Long phone = json.getLong("phone");
        System.out.println("name = " + name);
        System.out.println("phone = " + phone);

        assertEquals(692,id);
        assertEquals("Helen",name);
        assertEquals("Female",gender);
        assertEquals(1234512345L,phone);

    }


    @Test
    public void getCountries(){
    JsonPath json = get("http://54.224.118.38:1000/ords/hr/countries").body().jsonPath();

    // List of all countries in items array
    List<String> country_names = json.getList("items.country_name");
        System.out.println("country_names = " + country_names);
    // all region_ids
    List<Integer> country_region_ids = json.getList("items.region_id");
        System.out.println("country_region_ids = " + country_region_ids);


    String first_country_name  = json.getString("items.country_name[1]");
        System.out.println("String = " + first_country_name);

    String first_href = json.getString("items.links.href[1]");
        System.out.println("href = " + first_href);

        // get countryNames of all countries in region id 2
        List<String> country_names_with_region_id_2 = json.getList("items.findAll{it.region_id==2}.country_name");
        System.out.println("countries_with_region_id_2 = " + country_names_with_region_id_2);
    }
}
