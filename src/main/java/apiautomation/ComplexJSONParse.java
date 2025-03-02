package apiautomation;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJSONParse {
    public static void main(String[] args) {
        JsonPath jsonPath = new JsonPath(payload.coursePrice());
//        List<String> course = jsonPath.get("courses");
//        int courses = course.size();
        int courses = jsonPath.getInt("courses.size()");
        int purchaseAmount = jsonPath.get("dashboard.purchaseAmount");
        System.out.println(courses);
        System.out.println(purchaseAmount);
        String titleOfFirstCourse = jsonPath.get("courses[0].title");
        System.out.println(titleOfFirstCourse);
        int noOfCopiesSold = 0;
        int actualprice =0;
        for(int i=0;i<courses;i++)
        {
            String titlePath = "courses["+i+"].title";
            String pricePath = "courses["+i+"].price";
            System.out.println(jsonPath.getString(titlePath)+"-----"+jsonPath.getInt(pricePath));
            String coursePath = "courses["+i+"].copies";
            noOfCopiesSold = noOfCopiesSold+jsonPath.getInt(coursePath);
            actualprice = actualprice+jsonPath.getInt(coursePath)*jsonPath.getInt(pricePath);
            if(jsonPath.getString(titlePath).equalsIgnoreCase("RPA"))
            {
                System.out.println("No of Copies sold by RPA = "+jsonPath.getInt(coursePath));
            }
        }
        System.out.println("No. of Copies Sold = "+noOfCopiesSold);
        System.out.println("Actual price = "+actualprice);
        Assert.assertEquals(purchaseAmount,actualprice);

    }
}
