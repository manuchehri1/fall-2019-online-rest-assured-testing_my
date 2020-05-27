package com.automation.review;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static io.restassured.RestAssured.*;
public class WeatherAPP_My {
    static {
        baseURI = "https://www.metaweather.com/api/location";
    }

    public static void main(String[] args) {
       // get("/search/?query=san").prettyPeek();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");

        String city = scanner.nextLine();
        String woeid = getWOEID(city);
        printWeatherInfo(woeid);
    }

    public static String getWOEID(String city){
        Response response = given().queryParam("query",city).
                            get("/search");
        String woeid = response.jsonPath().getString("woeid");
        return woeid;
    }
    public static void printWeatherInfo(String woeid ){
        woeid = woeid.replaceAll("\\D","");
        Response response = get("{woeid}",woeid);

        List<String> weatherStateName = response.jsonPath().getList("consolidated_weather.weather_state_name");
        //System.out.println( weatherStateName);
        List<Double> theTemp = response.jsonPath().getList("consolidated_weather.the_temp");
        //System.out.println(theTemp);
        List<String> dates = response.jsonPath().getList("consolidated_weather.applicable_date");
        //System.out.println(dates);
        System.out.println("The Weather Forecast For This Week: ");
        for (int i = 0; i < weatherStateName.size(); i++) {
            String date = dates.get(i);
            date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).
                                    format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));

            System.out.printf("Date: %s, Weather State: %s, Temperature %s\n",date,
                                weatherStateName.get(i),theTemp.get(i));

        }
    }
}
