package apiautomation;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraAAPIExample {
    @Test
    public void createBug()
    {
        String auth = "Basic bWVnaGFuYTI1Y2hpdHR0aUBnbWFpbC5jb206QVRBVFQzeEZmR0YwWGNLa01ab0VMcy1YMUNIOEhTU0RZMk9TR09kamk3LWF6QVUwcW9Qd3A2eDljdlJyZGRNNjQ0QVZmd1ZEdzBFbUxOaDZTc3RWTVFXSURzckVxaUUwSU1ZQjFUelE0SlczaHNyd3Rzdno1dlA2RzZiMTZKbEpTcF8wUEVMeXZGZmZ2VnFPZm5EWlAtblhiR0xlc3Uwc3BRekNvX0E1dHlRbEpzcWVEcVNOQUtVPUUzMkIzQkMz";
        RestAssured.baseURI = "https://meghanapractice-api.atlassian.net";
        String response = given().log().all()
                .header("Content-Type","application/json")
                .header("Authorization",auth)
                .body(payload.createBug("Automation - Next button is not working"))
                .when().post("/rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();
        JsonPath jsonPath = reusableMethods.rawToJson(response);
        String id = jsonPath.getString("id");
        System.out.println(id);
        addAttachement(id,auth);
    }

    public void addAttachement(String id,String auth)
    {
        RestAssured.baseURI = "https://meghanapractice-api.atlassian.net";
        String response = given().pathParam("key",id)
                .header("X-Atlassian-Token","no-check").header("Authorization",auth)
                .multiPart("file",new File(System.getProperty("user.dir")+"/src/main/java/files/jira bug.png"))
                .when().post("rest/api/2/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
    }
}
