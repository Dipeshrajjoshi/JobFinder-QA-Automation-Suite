package com.portfolio.jobqa;

import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class JobApiTest {
    private static Process app;
    @BeforeAll static void startApp() throws Exception {
        app = new ProcessBuilder("mvn", "spring-boot:run", "-Dspring-boot.run.arguments=--server.port=8081")
            .redirectErrorStream(true).redirectOutput(ProcessBuilder.Redirect.DISCARD).start();
        long deadline = System.currentTimeMillis() + 45000;
        while (System.currentTimeMillis() < deadline) {
            try {
                var c = new java.net.URL("http://localhost:8081/api/jobs").openConnection();
                c.setConnectTimeout(500); c.getInputStream().close(); break;
            } catch (Exception ignored) { Thread.sleep(500); }
        }
        baseURI = "http://localhost:8081";
    }
    @AfterAll static void stopApp() { if(app != null) app.destroy(); }

    @Test void getJobsReturnsFourJobs() {
        given().when().get("/api/jobs").then().statusCode(200).body("size()", equalTo(4));
    }
    @Test void keywordQueryFiltersJobs() {
        given().queryParam("q","QA").when().get("/api/jobs").then()
            .statusCode(200).body("size()", equalTo(1)).body("[0].title", equalTo("QA Engineer"));
    }
    @Test void locationQueryFiltersJobs() {
        given().queryParam("location","Remote").when().get("/api/jobs").then()
            .statusCode(200).body("size()", equalTo(2)).body("location", everyItem(equalTo("Remote")));
    }
    @Test void knownJobIdReturnsJobDetails() {
        given().when().get("/api/jobs/1").then().statusCode(200)
            .body("title", equalTo("QA Engineer")).body("company", equalTo("Northstar AI"));
    }
    @Test void unknownJobIdReturnsNotFound() {
        given().when().get("/api/jobs/999").then().statusCode(404);
    }
}
