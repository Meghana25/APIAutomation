package apiautomation;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import serialization.pojo.Location;
import serialization.pojo.Places;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
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

        RequestSpecification requestSpecBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123").setContentType(ContentType.JSON).build();
        RequestSpecification request = given().spec(requestSpecBuilder).body(addPlace);

        ResponseSpecification responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        Response response = request.when().post("/maps/api/place/add/json").then().spec(responseSpecBuilder).extract().response();
        String responseString = response.asString();
        System.out.println(responseString);


    }
}
