package com.automation.tests.murodil.day04;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.get;

public class ORDSTestsWithPath {


    @BeforeAll
    public static void setUp(){
        baseURI="http://54.224.118.38:1000/ords/hr";
    }

    /**
     *  "items": [
     *         {
     *             "country_id": "AR",
     *             "country_name": "Argentina",
     *             "region_id": 2,
     *             "links": [
     *                 {
     *                     "rel": "self",
     *                     "href": "http://54.224.118.38:1000/ords/hr/countries/AR"
     *                 }
     *             ]
     *         },
     *         {
     *             "country_id": "AU",
     *             "country_name": "Australia",
     *             "region_id": 3,
     *             "links": [
     *                 {
     *                     "rel": "self",
     *                     "href": "http://54.224.118.38:1000/ords/hr/countries/AU"
     *                 }
     *             ]
     *         },
     */

    @Test
    public void getCountries_And_Extract_nested_key_values_using_path(){


        Response response = get("/countries").prettyPeek();

        String firstCountryID = response.path("items.country_id[0]");
        System.out.println("firstCountryID = " + firstCountryID);

        String firstCountryName = response.path("items.country_name[0]");
        System.out.println("firstCountryName = " + firstCountryName);

        List<String> allCountryNames = response.path("items.country_name");
        System.out.println("allCountryNames = " + allCountryNames);

    }

}
