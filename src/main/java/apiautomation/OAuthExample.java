package apiautomation;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class OAuthExample {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type","client_credentials")
                .formParams("scope","trust")
                .when().log().all()
                .post("/oauthapi/oauth2/resourceOwner/token").asString();
        JsonPath jsonPath = reusableMethods.rawToJson(response);
        String access_token = jsonPath.getString("access_token");
        System.out.println(access_token);
        getCourseDetails(access_token);
    }
    public static void getCourseDetails(String access_token)
    {
        String response = given().queryParam("access_token",access_token)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .asString();
        System.out.println(response);
    }

}
