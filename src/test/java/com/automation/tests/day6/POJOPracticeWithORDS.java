package com.automation.tests.day6;

import com.automation.pojos.Employee;
import com.automation.pojos.Link;
import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.baseURI;

public class POJOPracticeWithORDS {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }



    @Test
    public void getEmployeeTest(){

        Response response = get("/employees/{id}",100).prettyPeek();

        Employee employee = response.as(Employee.class);
        System.out.println("employee = " + employee);

        List<Link> link = employee.getLinks();
        System.out.println(link);


    }


}
