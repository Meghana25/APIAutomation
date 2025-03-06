package serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import serialization.pojo.Location;
import serialization.pojo.Places;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class AddPlace {
    public static void main(String[] args) {
        Places addPlace = new Places();
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);
        addPlace.setAccuracy(50);
        addPlace.setName("Frontline house");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setTypes(Arrays.asList("shoe park", "shop"));
        addPlace.setWebsite("http://google.com");
        addPlace.setLanguage("French-IN");

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Response response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(addPlace).when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();
        String responseString = response.asString();
        System.out.println(responseString);

    }

}


