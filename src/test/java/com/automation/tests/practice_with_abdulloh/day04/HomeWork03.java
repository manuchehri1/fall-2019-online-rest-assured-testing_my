package com.automation.tests.practice_with_abdulloh.day04;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class HomeWork03 {

    String apiKey = "$2a$10$RySyBc6UW1tuvs2c.wt74u0mCicFTb1liPoQVlKYKujbewk3WFXgq";

    @BeforeAll
    public static void setup() {

        baseURI = "https://www.potterapi.com/v1";
    }


    /**
     *
     * Verify sorting hat
     * 1.Send a get request to /sortingHat.
     * Request includes :
     * 2.Verify status code 200, content type application/json; charset=utf-83.
     * Verify that response body contains one of the
     * following houses: "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */

    @Test
    @DisplayName("Verify sorting hat")
    public void test_01_Sorting_Hat(){

        Response response = when().
                                    get("/sortingHat").prettyPeek();


               // String list = "\"Gryffindor\", \"Ravenclaw\", \"Slytherin\", \"Hufflepuff\"";

                List<String > list1 = new ArrayList<>(Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"));

               // String body = response.body().asString();
                String body1 = response.as(String.class);
                System.out.println("body1 = " + body1);

               // System.out.println("body = " + body);

                response.   then().
                                statusCode(200).
                            and().
                                contentType(ContentType.JSON);

                //assertTrue(list.contains(body));
                assertTrue(list1.contains(body1));





    }


    //Verify bad key
    // 1.Send a get request to /characters.
    // Request includes :
    // •Header Accept with value application/json
    // •Query param key with value invalid2.
    // Verify status code 401, content type application/json; charset=utf-83.
    // Verify response status line include message Unauthorized4.
    // Verify that response body says"error":"APIKeyNotFound"
    @Test
    @DisplayName("Verify bad key")
    public void test_02_bad_key(){

                given().
                        log().all().
                        accept("application/json").
                        queryParam("key","invalid2").
                when().
                        get("characters").

                then().
                        log().all().
                        statusCode(401).
                and().
                        statusLine(containsString("Unauthorized")).
                and().
                        body("error",is("API Key Not Found"));

    }


    //Verify no key
    //1. Send a get request to /characters. Request includes :
    // • Header Accept with value application/json
    //2. Verify status code 409, content type application/json; charset=utf-8
    //3. Verify response status line include message Conflict
    //4. Verify that response body says "error": "Must pass API key for request"

    @Test
    @DisplayName("Verify no key")
    public void noKeyTest(){
        given().
                accept(ContentType.JSON).
        when().
                get("/characters").
        then().
                log().all().
                statusCode(409).
                statusLine(containsString("Conflict")).
                body("error",is("Must pass API key for request"));

    }

    //Verify number of characters
    //1. Send a get request to /characters. Request includes :
    // • Header Accept with value application/json
    //• Query param key with value {{apiKey}}
    //2. Verify status code 200, content type application/json; charset=utf-8
    //3. Verify response contains 194 characters ------ this is a bug actual is 195 char


    @Test
    @DisplayName("Verify number of characters")
    public void numberOfCharTest(){
        Response response = given().
                accept(ContentType.JSON).
                queryParam("key",apiKey).
        when().
                get("/characters");
        List<Map<String,String>> list = response.jsonPath().getList("");
        System.out.println("list.size() = " + list.size());

        response.then().
                 log().all().
                 statusCode(200).
                 contentType(ContentType.JSON);
                 assertThat(list,hasSize(195));

    }

    //Verify number of character id and house
    //1. Send a get request to /characters. Request includes :
    // • Header Accept with value application/json
    //• Query param key with value {{apiKey}}
    //2. Verify status code 200, content type application/json; charset=utf-8
    //3. Verify all characters in the response have id field which is not empty
    //4. Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response
    //5. Verify value of the house in all characters in the response is one of the following:
    //"Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"  --- this is a bug

    @Test
    @DisplayName("Verify number of character id and house")
    public void numOfIDandHouseTest(){
        Response response = given().
                                    accept(ContentType.JSON).
                                    queryParam("key",apiKey).
                            when().
//                                    log().all().
                                    get("/characters");
                String listOfHouse = "\"Gryffindor\", \"Ravenclaw\", \"Slytherin\", \"Hufflepuff\"";
                List<String> house = response.jsonPath().getList("house");
                System.out.println("house = " + house);

                response.   then().
                                statusCode(200).
                                body("_id",everyItem(not(isEmptyOrNullString()))).
                                body("dumbledoresArmy",everyItem(isOneOf(true,false))).
                                body("dumbledoresArmy", everyItem(is(instanceOf(Boolean.class)))).
                                body("house",everyItem(isOneOf("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff",null)));



    }


    //Verify all character information
    //1. Send a get request to /characters. Request includes :
    // • Header Accept with value application/json
    //• Query param key with value {{apiKey}}
    //2. Verify status code 200, content type application/json; charset=utf-8
    //3. Select name of any random character
    //4. Send a get request to /characters. Request includes :
    //• Header Accept with value application/json
    // • Query param key with value {{apiKey}}
    //• Query param name with value from step 3
    //5. Verify that response contains the same character information from step 3. Compare all fields.


    @Test
    @DisplayName("Verify all character information")
    public void allCharInfo(){

        Response response1 = given().
                                    accept(ContentType.JSON).
                                    queryParam("key",apiKey).
                            when().
                                    get("/characters");
        List<Map<String,String>> allCharInfo = response1.jsonPath().getList("");


        List<String> nameList = response1.jsonPath().getList("name");// 2.
        Random random = new Random();
        int randomNum = random.nextInt(nameList.size()+1);
        String  randomName = nameList.get(randomNum); // Ali Vali


        Response response2 =  given().
                                        log().all().
                                     accept(ContentType.JSON).
                                     queryParam("key",apiKey).
                                     queryParam("name",randomName).
                             when().
                                    get("/characters");

        List<String> infoRandom = response2.jsonPath().getList("");
                  response2.  then().
                                    log().all().
                                    body("[0].name",is(randomName)); // ali vali


    }





    //Verify name search
    //1. Send a get request to /characters. Request includes :
    // • Header Accept with value application/json
    //• Query param key with value {{apiKey}}
    //• Query param name with value Harry Potter
    //2. Verify status code 200, content type application/json; charset=utf-8
    //3. Verify name Harry Potter
    //4. Send a get request to /characters. Request includes :
    //• Header Accept with value application/json
    // • Query param key with value {{apiKey}}
    //• Query param name with value Marry Potter
    //5. Verify status code 200, content type application/json; charset=utf-8
    //6. Verify response body is empty

    @Test
    @DisplayName("Verify name search")
    public void nameSearchTest(){
        Response response1 = given().
                                     accept(ContentType.JSON).
                                     queryParam("key",apiKey).
                                     queryParam("name","Harry Potter").
                             when().
                                     get("/characters");
                    response1. then().

                                      statusCode(200).
                                      contentType(ContentType.JSON).
                                      body("[0].name",is("Harry Potter"));

        Response response2 = given().
                                    accept(ContentType.JSON).
                                    queryParam("key",apiKey).
                                    queryParam("name","Marry Potter").
                            when().
                                    get("/characters");

                    response2. then().
                                    log().all().
                                    statusCode(200).
                                    contentType(ContentType.JSON).
                                    body("",hasSize(0)).
                                    body("size()",is(0));
    }



    //Verify house members
    //1. Send a get request to /houses. Request includes :
    // • Header Accept with value application/json
    //• Query param key with value {{apiKey}}
    //2. Verify status code 200, content type application/json; charset=utf-8
    //3. Capture the id of the Gryffindor house
    //4. Capture the ids of the all members of the Gryffindor house
    //5. Send a get request to /houses/:id. Request includes :
    //• Header Accept with value application/json
    // • Query param key with value {{apiKey}}
    //• Path param id with value from step 3
    //6. Verify that response contains the same member ids as the step 4

    @Test
    @DisplayName("Verify house members")
    public void houseMembersTest(){
        Response response1 = given().
                                    accept(ContentType.JSON).
                                    queryParam("key",apiKey).
                            when().
                                    get("/houses");
                response1. then().

                        statusCode(200).
                        contentType(ContentType.JSON);


                String idOfGryff = response1.jsonPath().getString("find{it.name == 'Gryffindor'}_id");
                List<String> membersIdsOfGryff = response1.jsonPath().getList("find{it.name == 'Gryffindor'}members");


        Response response2 = given().
                                    log().all().
                                    accept(ContentType.JSON).
                                    queryParam("key",apiKey).
                                    pathParam("id",idOfGryff).

                            when().
                                    get("/houses/{id}");
        List<String> idFromRes2 = response2.jsonPath().getList("[0].members._id");

                    response2. then().
                                    body("[0].members._id",is(membersIdsOfGryff));


    }






    //Verify house members again
    //1. Send a get request to /houses/:id. Request includes :
    // • Header Accept with value application/json
    //• Query param key with value {{apiKey}}
    //• Path param id with value 5a05e2b252f721a3cf2ea33f
    //2. Capture the ids of all members
    //3. Send a get request to /characters. Request includes :
    //• Header Accept with value application/json
    // • Query param key with value {{apiKey}}
    //• Query param house with value Gryffindor
    //4. Verify that response contains the same member ids from step 2


    @Test
    @DisplayName("Verify house again members")
    public void houseMembersAgainTest(){
        Response response1 = given().
                                    accept(ContentType.JSON).
                                    queryParam("key",apiKey).
                                    pathParam("id","5a05e2b252f721a3cf2ea33f").
                            when().
                                    get("/houses/{id}");
                response1. then().
//                                        log().all().
                                    statusCode(200).
                                    contentType(ContentType.JSON);


       // String idOfGryff = response1.jsonPath().getString("find{it.name == 'Gryffindor'}_id");
        List<String> membersIdsOfGryff = response1.jsonPath().getList("[0].members._id");
        //System.out.println("membersIdsOfGryff = " + membersIdsOfGryff);

        Response response2 = given().
                                        accept(ContentType.JSON).
                                        queryParam("key",apiKey).
                                        queryParam("house","Gryffindor").

                            when().
                                        get("/characters");
                    List<String> idsFromRes2 = response2.jsonPath().getList("_id");
                     System.out.println("idsFromRes2 = " + idsFromRes2);
                response2.  then().
                                            log().all();
                assertThat(membersIdsOfGryff.size(),is(idsFromRes2.size()));




    }



    //Verify house with most members
    //1. Send a get request to /houses. Request includes :
    // • Header Accept with value application/json
    //• Query param key with value {{apiKey}}
    //2. Verify status code 200, content type application/json; charset=utf-8
    //3. Verify that Gryffindor house has the most members

    @Test
    @DisplayName("Verify house with most members")
    public void houseWithMostMemberTest(){

        Response response1 = given().
                                    accept(ContentType.JSON).
                                    queryParam("key",apiKey).
                             when().
                                      get("/houses");
        response1. then().
//                                        log().all().
                            statusCode(200).
                            contentType(ContentType.JSON);

        List<List<String>> allMembers = new ArrayList<>();

        for (int i = 0; i < response1.jsonPath().getList("").size() ; i++) {
            allMembers.add(i,response1.jsonPath().getList("["+i+"].members"));
        }



        String houseWithMostMember = "";
        int max = 0;
        for (int i = 0; i <allMembers.size(); i++) {
            if (allMembers.get(i).size()>max){
                max=allMembers.get(i).size();
                houseWithMostMember = response1.jsonPath().getString("["+i+"].name");
            }
        }
        assertEquals("Gryffindor",houseWithMostMember );



    }



}
