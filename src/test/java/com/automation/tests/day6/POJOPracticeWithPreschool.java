package com.automation.tests.day6;
import com.automation.pojos.Student;
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

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.baseURI;
import org.junit.jupiter.api.BeforeAll;

public class POJOPracticeWithPreschool {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("PRESCHOOL.URI");
    }


    @Test
    public void assStudentTest(){
        File file = new File("student.json");

        Response response =
                            given().
                                contentType(ContentType.JSON).
                            and().
                                body(file).
                            when().
                                post("/student/create").prettyPeek();

        int studentId = response.path("studentId");


    }

    @Test
    public void getStudentTest(){
        Response response = get("/student/{id}",11613).prettyPeek();
        Student student = response.jsonPath().getObject("students[0]",Student.class);
        System.out.println("student = " + student);

        
    }



}
