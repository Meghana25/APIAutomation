package apiautomation;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class LibraryAPIExample {
    @Test(dataProvider = "BookDetails")
    public void addBook(String isbn,String aisle)
    {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type","application/json")
                .body(payload.addBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath jsonPath = reusableMethods.rawToJson(response);
        String id = jsonPath.getString("ID");
        System.out.println(id);
        Assert.assertEquals(jsonPath.getString("Msg"),"successfully added");
        deleteBook(id);
    }

    public void deleteBook(String id)
    {
        RestAssured.baseURI = "http://216.10.245.166";
        given().header("Content-Type","application/json")
                .body(payload.deleteBookId(id))
                .when().post("/Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200).body("msg",equalTo("book is successfully deleted"));
    }

    @DataProvider(name = "BookDetails")
    public Object[][] getData()
    {
        return new Object[][]{{"C","1"},{"Java","2"},{"Python","3"}};
    }
}
