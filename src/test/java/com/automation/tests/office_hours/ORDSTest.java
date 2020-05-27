package com.automation.tests.office_hours;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.baseURI;
import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class ORDSTest {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }




          /*
            get all the records from the employees table using the }/employees
            verify that number of employees is more tan 100
         */

        @Test
        public void allEmployeeTest(){
            Response response = given().accept(ContentType.JSON).
                                and().queryParam("limit",110).
                                when().get("/employees");
            response.           then().statusCode(200);


            List<Map<String,?>> employees = response.jsonPath().getList("items");
            int numberOfEmp = employees.size();
            System.out.println(numberOfEmp);
            System.out.println(employees.get(0));
            assertTrue(numberOfEmp>100);

            }

             /*
            get all the employees and their depart ids.
            verify that department id points to the existing record
            in the departmetns table
            verify response 200
            verify department name is not empty
         */

             @Test
             public void departmentsTest(){
                 Response response = given().queryParam("limit",110).
                                    and().accept(ContentType.JSON).
                                    when().get("/employees");

                 List<Integer> departmentIds =  response.jsonPath().getList("items.department_id");
                 //System.out.println(departmentIds);
                 Set<Integer> uniqueDepartmentIds = new HashSet<>();
                 uniqueDepartmentIds.addAll(departmentIds);
                 System.out.println(uniqueDepartmentIds);
                 for (Integer depId : uniqueDepartmentIds) {

                     given().
                             pathParams("id",depId).
                     when().
                             get("/departments/{id}").
                     then().
                             statusCode(200).
                     and().
                             body("department_name", not(emptyOrNullString()));

                 }


             }


}
