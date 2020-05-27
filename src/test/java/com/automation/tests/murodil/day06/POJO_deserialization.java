package com.automation.tests.murodil.day06;
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
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;


public class POJO_deserialization {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        authentication = basic("admin","admin");
    }
    
    @Test
    public void spartan_to_pojo_object_deserialization(){
        Response response = given().accept(ContentType.JSON).
                            when().get("/spartans/{id}",325);

        // This Process is called Deserialization
        // Converting From JSON to Java Custom Class Object

        // JSON response body --->> Custom Java class object
        Spartan spartan = response.body().as(Spartan.class);
        System.out.println("spartan = " + spartan);
        System.out.println("spartan.getName() = " + spartan.getName());
        System.out.println("spartan.getGender() = " + spartan.getGender());
        assertEquals("Nur",spartan.getName());
        assertEquals("Female",spartan.getGender());
        assertEquals(325,spartan.getId());
        assertEquals(1111111111,spartan.getPhone());
    }
    
    
    @Test
    public void gSonExample(){
        
        Spartan spartan = new Spartan(345,"Mevlana", "Male",1112223334L);
        Gson gson = new Gson();
        // Serialize spartan object to JSON format using GSON
        String json = gson.toJson(spartan);
        System.out.println("json = " + json);
        
        String myJson = "{\"id\":365,\"name\":\"Jomi\",\"gender\":\"Male\",\"phone\":22233334}";
        // Deserialization Convert JSON to Java Spartan object
        Spartan Jomi = gson.fromJson(myJson,Spartan.class);
        System.out.println("Jomi = " + Jomi);
        
    }
    
    @Test
    public void list_of_spartans_pojo_deserialization(){
        
        Response response = given().accept(ContentType.JSON).
                            when().get("/spartans/");
        
        List<Spartan> allSpartans = response.body().as(new TypeRef<List<Spartan>>() {});
        //System.out.println(allSpartans);
        String firstSpartan = allSpartans.get(0).toString();
        System.out.println("firstSpartan = " + firstSpartan);
        System.out.println(allSpartans.get(0));

        int counter =1;
        for (Spartan eachSpartan : allSpartans) {
            System.out.println(counter+" "+eachSpartan.toString());
        counter++;
        }

        // Using AllSpartans class for deserialization

        AllSpartans allSpartansObj = response.body().as(AllSpartans.class);

        System.out.println(allSpartansObj.getSpartanList().get(0).toString());

        
    }

    /**
     * {"gender":"Male",
     * "name":"Maximus",
     * "phone":8877445596}
     * When user sends POST request to '/spartans/'
     * Then status code 201
     * And content type should be application/json
     * And json payload/response should contain:
     * "A Spartan is Born!" message
     * and same data what is posted
     */

    @Test
    public void post_new_spartan_test(){
        Response response = given().contentType(ContentType.JSON).
                            and().accept(ContentType.JSON).
                            and().body("{\"gender\":\"Male\",\n" +
                                         "\"name\":\"String Test\",\n" +
                                         "\"phone\":8877445596}").
                            when().post("/spartans");
        assertEquals(201,response.statusCode());
        assertEquals("application/json",response.contentType());
        // extract message using path method
        String message1 = response.path("success");
        // extract message using JsonPath
        JsonPath json = response.jsonPath();
        String message2 = json.getString("success");
        System.out.println("message = " + message1);
        System.out.println("message2 = " + message2);
        assertEquals("A Spartan is Born!",message1);
        assertEquals("A Spartan is Born!",message2);
        assertEquals("String Test", response.path("data.name"));
        assertEquals("String Test", json.get("data.name"));
        assertEquals("Male", json.getString("data.gender"));
        assertEquals("8877445596",json.getString("data.phone"));

    }

    @Test
    public void post_new_spartan_with_map_test(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("gender","Male");
        requestMap.put("name","Vali");
        requestMap.put("phone",1234567890L);

        Response response = given().contentType(ContentType.JSON).
                            and().accept(ContentType.JSON).
                            and().body(requestMap).
                            when().post("/spartans");

        assertEquals(201,response.statusCode());
        assertEquals("application/json",response.contentType());
        // extract message using path method
        String message1 = response.path("success");
        // extract message using JsonPath
        JsonPath json = response.jsonPath();
        String message2 = json.getString("success");
        System.out.println("message = " + message1);
        System.out.println("message2 = " + message2);
        assertEquals("A Spartan is Born!",message1);
        assertEquals("A Spartan is Born!",message2);
        assertEquals("Vali", response.path("data.name"));
        assertEquals("Vali", json.get("data.name"));
        assertEquals("Male", json.getString("data.gender"));
        assertEquals("1234567890",json.getString("data.phone"));

        int spartanID = json.getInt("data.id");
        System.out.println("spartanID = " + spartanID);
        System.out.println(" Sending get request with new born spartan id "+spartanID);
        get("/spartans/{id}",spartanID).prettyPeek();


    }

    @Test
    public void post_new_spartan_with_pojo_object_test(){

        Spartan newSpartan = new Spartan("Karim","Male",1112223334L);
        System.out.println("newSpartan = " + newSpartan);

        Response response = given().contentType(ContentType.JSON).
                and().accept(ContentType.JSON).
                and().body(newSpartan).
                when().post("/spartans");
        assertEquals(201,response.statusCode());
        assertEquals("application/json",response.contentType());
        // extract message using path method
        String message1 = response.path("success");
        // extract message using JsonPath
        JsonPath json = response.jsonPath();
        String message2 = json.getString("success");
        System.out.println("message = " + message1);
        System.out.println("message2 = " + message2);
        assertEquals("A Spartan is Born!",message1);
        assertEquals("A Spartan is Born!",message2);
        assertEquals("Karim", response.path("data.name"));
        assertEquals("Karim", json.get("data.name"));
        assertEquals("Male", json.getString("data.gender"));
        assertEquals(1112223334L,json.getLong("data.phone"));

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        int spartanID = json.getInt("data.id");
        System.out.println("spartanID = " + spartanID);
        System.out.println(" Sending get request with new born spartan id "+spartanID);
        get("/spartans/{id}",spartanID).prettyPeek();

    }



    
    

}
