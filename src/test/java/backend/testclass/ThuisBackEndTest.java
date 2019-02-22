package backend.testclass;

import framework.base.WSTestBase;
import framework.extentFactory.ReportFactory;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.*;

public class ThuisBackEndTest extends WSTestBase {

    //property instantiated
    Properties prop = new Properties();

    //TestNG annotation to run before test
    @BeforeTest

    //configurations for webservice timeout and property file
    public void configTimeOutandProperties() throws IOException {

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
        prop.load(fis);

        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig().
                setParam("http.connection.timeout", 15000).
                setParam("http.socket.timeout", 15000).
                setParam("http.connection-manager.timeout", 15000));
        System.out.println("Preparing connection timeout");
    }

    //TestNG annotation to run the test

    @Test(priority = 1, groups = {"SmokeTest"})
    public void verifyStatus() {
        given()
                .headers("Cache-Control", "max-age=604800", "Connection", "keep-alive");
        when()
                .get(prop.getProperty("HOST"))
                .then()
                .statusCode(200).log().all();
        ReportFactory.getChildTest().info("Service is up and Running!");
    }

    @Test(priority = 2, groups = {"PerformanceTest"})
    public void measureResponseTimes() {

        String url1 = prop.getProperty("HOST");

        Response response = RestAssured.get(url1);
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);

        System.out.println("Time in Milliseconds: " + timeInMS);
        assertEquals(timeInS, timeInMS / 1000);
        ReportFactory.getChildTest().info("Performance Measured for 'GET' method. Result in Milliseconds: " + timeInMS);

    }

    //@Test(priority = 3, groups = {"VerifyTests"})
    public void verifyResponseType() {
        given()
                .get(prop.getProperty("HOST"))
                .then()
                .assertThat()
                .header("Content-Type", "text/html; charset=UTF-8");
        ReportFactory.getChildTest().pass("Verified response type, no problems observed");
    }

    //@Test(priority = 4, groups = {"VerifyTests"})
    public void verifyTitleInResponseBodyForAlbums() {
        String path = "/albums/1";
        String url = prop.getProperty("HOST") + path;

        Response response = given()
                .get(url);

        System.out.println("testBody url: " + url + ' ');

        JsonPath jsonPathEvaluator = response.jsonPath();
        String title = jsonPathEvaluator.get("title");
        System.out.println(title);
        assertNotNull(title);
        assertTrue(title.equals("quidem molestiae enim"));
        System.out.println("title in body is: " + title);
        ReportFactory.getChildTest().pass("Verified Title in albums first record, assertion returned no errors");
    }

   // @Test(priority = 5, groups = {"VerifyTests"})
    public void verifyNameInResponseBodyForUsers() {
        String path = "/users/1";
        String url = prop.getProperty("HOST") + path;

        Response response = given()
                .get(url);

        System.out.println("testBody url: " + url + ' ');

        JsonPath jsonPathEvaluator = response.jsonPath();
        String name = jsonPathEvaluator.get("name");
        System.out.println(name);
        assertNotNull(name);
        assertTrue(name.equals("Leanne Graham"));
        System.out.println("title in body is: " + name);
        ReportFactory.getChildTest().pass("Verified Name in users first record, assertion returned no errors");
    }

    //@Test(priority = 6, groups = {"VerifyTests"})
    public void verifyResponseCountForUsers() {

        String path = "/users";
        String url = prop.getProperty("HOST") + path;

        Response response = given()
                .get(url);
        System.out.println(url);

        List<String> company = response.path("company.name");

        assertTrue(company.size() == 10);

        int statusCode = response.statusCode();
        System.out.println(statusCode);
        assertTrue(statusCode == 200);

        System.out.println("verifyResponseCountForUsers url: " + url);
        System.out.println("verifyResponseCountForUsers: " + company);
        ReportFactory.getChildTest().pass("Verified response by size of company name. Assertion returned no errors");
    }

    //@Test(priority = 7, groups = {"VerifyTests"})
    public void verifyResponseCountForAlbums() {

        String path = "/albums";
        String url = prop.getProperty("HOST") + path;

        Response response = given()
                .get(url);
        System.out.println(url);

        List<String> id = response.path("id");

        assertTrue(id.size() == 100);

        int statusCode = response.statusCode();
        System.out.println(statusCode);
        assertTrue(statusCode == 200);

        System.out.println("verifyResponseCountForAlbums url: " + url);
        System.out.println("verifyResponseCountForAlbums: " + id);
        ReportFactory.getChildTest().pass("Verified response by size of id's. Assertion returned no errors");
    }

    //@Test(priority = 8, groups = {"VerifyTests"})
    public void countRecordsInUsers() {

        given()
                .header("Content-Type", "application/json");
        when().
                get(prop.getProperty("HOST") + "/users?10"). //get("https://jsonplaceholder.typicode.com/users?10").
                then()
                .contentType(ContentType.JSON)
                .statusCode(200).log().all()
                .body("id", hasSize(10));
        ReportFactory.getChildTest().pass("Count of users is equal to 10. Assertion returned no errors");
    }

    //@Test(priority = 9, groups = {"VerifyTests"})
    public void countRecordsInAlbums() {

        given()
                .header("Content-Type", "application/json");
        when().
                get(prop.getProperty("HOST") + "/albums?100"). //get("https://jsonplaceholder.typicode.com/albums?100").
                then()
                .contentType(ContentType.JSON)
                .statusCode(200).log().all()
                .body("id", hasSize(100));
        ReportFactory.getChildTest().pass("Count of albums is equal to 100. Assertion returned no errors");
    }

    //@Test(priority = 10, groups = {"VerifyTests"})
    public void isThereUserWithGivenCity() {
        given()
                .header("Content-Type", "application/json").
                when()
                .get(prop.getProperty("HOST") + "/users/10").
                then()
                .contentType(ContentType.JSON)
                .statusCode(200).log().all()
                .body("address.city", equalTo("Lebsackbury"));
        ReportFactory.getChildTest().pass("Checked if the city exists. Assertion returned no errors");
    }

    //@Test(priority = 11, groups = {"VerifyTests"})
    public void isThereAlbumWithGivenTitle() {
        given()
                .header("Content-Type", "application/json").
                when()
                .get(prop.getProperty("HOST") + "/albums/10").
                then()
                .contentType(ContentType.JSON)
                .statusCode(200).log().all()
                .body("title", equalTo("distinctio laborum qui"));
        ReportFactory.getChildTest().pass("Checked if the title exists. Assertion returned no errors");
    }
}

