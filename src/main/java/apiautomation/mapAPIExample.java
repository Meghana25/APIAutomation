package apiautomation;
import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class mapAPIExample {
    public static void main(String[] args) {
        //Validate AddPlace API is working as expected or not
        //RestAssured principles
        //given - all input details
        //when - submit the API - RESOURCE, HTTP METHOD
        //then - validate the response
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(payload.addPlace())
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .body("scope",equalTo("APP")).header("Server",
                        "Apache/2.4.52 (Ubuntu)").extract().body().asString();
        System.out.println(response);

        String header = given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(payload.addPlace()).when().post("/maps/api/place/add/json").then().statusCode(200)
                .extract().header("Server");
        JsonPath jsonPath = reusableMethods.rawToJson(response);
        String place_id = jsonPath.get("place_id");
        System.out.println(place_id);

        // Update Place

        String addressToupdate = "70 Summer walk, USA";
        given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(payload.updatePlace(place_id,addressToupdate)).when().put("/maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200)
                .body("msg",equalTo("Address successfully updated"));

        // Get Place
        given().queryParam("key","qaclick123").queryParam("place_id",place_id).header("Content-Type","application/json")
                .when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).body("address",equalTo(addressToupdate));

        String getAddress =given().log().all().queryParam("key","qaclick123").queryParam("place_id",place_id)
                .when().get("/maps/api/place/get/json").then().statusCode(200)
                .extract().asString();
       JsonPath jsonPath1 = reusableMethods.rawToJson(getAddress);
        System.out.println(jsonPath1.getString("address"));
        Assert.assertEquals(jsonPath1.getString("address"),addressToupdate);

    }
}
