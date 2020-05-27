package com.automation.tests.day3;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*; // all static member of this class can be called just by their name
import static org.junit.jupiter.api.Assertions.*;
import  static org.hamcrest.Matchers.*;

public class ExchangeRatesAPITests {


    @BeforeAll // must be static
    public static void setup(){
        // for every single request this is a base URI
       // RestAssured.baseURI = "http://api.openrates.io";
        baseURI = "http://api.openrates.io"; // end resource
    }


    // get the latest currency rates
    @Test
    public void getLatestRate(){
                                                // to get response output in the console
        Response response = given().
                                queryParam("base","USD").
                            when().
                                get("/latest").prettyPeek();

        // to read header of response
        Headers headers = response.getHeaders(); // all headers
        String contentType = headers.getValue("Content-Type");
        System.out.println(contentType);

             // verify that GET request to the endpoint was successful
        response.then().assertThat().statusCode(200);
        // to assert that base is USD we are using Matchers class assertion
        response.then().assertThat().body("base", equalTo("USD"));

        String date=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateBody ="2020-05-19";
        response.then().assertThat().body("date",containsString(dateBody));

    }


    @Test
    @DisplayName("Getting history of rates")
    public void getHistoryOfRates(){
        Response response = given().
                                queryParam("base","USD").
                            when().
                                get("/2010-02-01").prettyPeek();

        Headers headers = response.getHeaders(); // response header
        System.out.println(headers);

        response.then().assertThat().
                            statusCode(200).
                            and().
                            body("date", is("2010-02-01")).
                            and().
                            body("rates.USD",is(1.0F));
        // and() doesn't have a functional role, it is just a syntax sugar

        Float param = response.jsonPath().get("rates.USD");
        System.out.println(param);


    }
}
