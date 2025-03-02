package apiautomation;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class readJsonfromFile {
    @Test
    public void addBook() throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/main/java/jsonFiles/addBook.json"))))
                .when().post("/Library/Addbook.php").then().log().all().assertThat()
                .statusCode(200).extract().response().asString();
//        JsonPath jsonPath = reusableMethods.rawToJson(response);
//        String id = jsonPath.getString("ID");
//        System.out.println(id);
    }
}
