package deserialization;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import deserialization.pojo.GetCourse;
import deserialization.pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        GetCourse getCourse = given().queryParam("access_token",access_token)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                        .as(GetCourse.class);
        System.out.println(getCourse.getLinkedIn());
        System.out.println(getCourse.getInstructor());
        int size = getCourse.getCourses().getApi().size();
        for(int i=0;i<size;i++)
        {
            if(getCourse.getCourses().getApi().get(i).getCourseTitle().contains("SoapUI"))
            {
                System.out.println(getCourse.getCourses().getApi().get(i).getPrice());
            }
        }

        String[] courses = {"Selenium Webdriver Java","Cypress","Protractor"};
        ArrayList<String> courseTitles = new ArrayList<>();

        List<WebAutomation> webAutomations = getCourse.getCourses().getWebAutomation();
        for(int i=0;i<webAutomations.size();i++)
        {
            courseTitles.add(webAutomations.get(i).getCourseTitle());
        }
        List<String> expectedList = Arrays.asList(courses);
        Assert.assertEquals(courseTitles,expectedList);


    }

}
