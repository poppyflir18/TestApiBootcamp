import io.restassured.RestAssured;
import io.restassured.internal.path.json.mapping.JsonPathJackson1ObjectDeserializer;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class RestAssuredTest {
    @Test
    public void singleUserBddTest(){
        when().get("https://reqres.in/api/users/2")
                .then().statusCode(200)
                .body("data.email",equalTo("janet.weaver@reqres.in"))
                .time(lessThan(1000L));
    }

    @Test
    public void singleTestUser(){
        RestAssured.baseURI="https://reqres.in";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("/api/users/2");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        System.out.println(bodyAsString);
        Assert.assertTrue(bodyAsString.contains("janet.weaver@reqres.in"), "String not found");
    }

    @Test
    public void createTestUser()
    {
        RestAssured.baseURI ="https://reqres.in";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "morpheus");
        requestParams.put("job", "leader");
        request.body(requestParams.toString());
        Response response = request.post("/api/users");
        ResponseBody body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
        Assert.assertTrue(body.asString().contains("createdAt"), "false");

    }



}
