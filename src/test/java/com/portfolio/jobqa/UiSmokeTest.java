package com.portfolio.jobqa;

import java.time.Duration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;

class UiSmokeTest {
    private static Process app;
    private WebDriver driver;
    private final String baseUrl = "http://localhost:8080";

    @BeforeAll static void startApp() throws Exception {
        app = new ProcessBuilder("mvn", "spring-boot:run", "-Dspring-boot.run.arguments=--server.port=8080")
            .redirectErrorStream(true).redirectOutput(ProcessBuilder.Redirect.DISCARD).start();
        long deadline = System.currentTimeMillis() + 45000;
        while (System.currentTimeMillis() < deadline) {
            try {
                var connection = new java.net.URL("http://localhost:8080/api/jobs").openConnection();
                connection.setConnectTimeout(500); connection.getInputStream().close(); return;
            } catch (Exception ignored) { Thread.sleep(500); }
        }
        throw new IllegalStateException("Application did not start on port 8080");
    }

    @AfterAll static void stopApp() { if (app != null) app.destroy(); }

    @BeforeEach void createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1440,1000");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @AfterEach void closeDriver() { if (driver != null) driver.quit(); }

    @Test void homepageShowsJobListings() {
        driver.get(baseUrl);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.findElements(By.cssSelector(".job")).size() == 4);
        assertEquals("JobFinder | QA Portfolio Demo", driver.getTitle());
        assertEquals("4 job(s) found", driver.findElement(By.id("resultCount")).getText());
    }

    @Test void keywordSearchFiltersJobs() {
        driver.get(baseUrl);
        driver.findElement(By.id("search")).sendKeys("QA Engineer");
        driver.findElement(By.id("searchButton")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElements(By.cssSelector(".job")).size() == 1);
        assertTrue(driver.findElement(By.cssSelector(".job h3")).getText().contains("QA Engineer"));
    }

    @Test void locationFilterReturnsRemoteJobs() {
        driver.get(baseUrl);
        driver.findElement(By.id("location")).sendKeys("Remote");
        driver.findElement(By.id("searchButton")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElements(By.cssSelector(".job")).size() == 2);
        assertTrue(driver.findElements(By.cssSelector(".job")).stream()
            .allMatch(card -> card.getText().contains("Remote")));
    }

    @Test void noResultsShowsHelpfulMessage() {
        driver.get(baseUrl);
        driver.findElement(By.id("search")).sendKeys("ThisJobDoesNotExist");
        driver.findElement(By.id("searchButton")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.id("emptyState")).isDisplayed());
        assertTrue(driver.findElement(By.id("emptyState")).getText().contains("No jobs found"));
    }

    @Test void jobDetailsPageShowsSelectedRole() {
        driver.get(baseUrl + "/job.html?id=1");
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.id("jobTitle")).isDisplayed());
        assertEquals("QA Engineer", driver.findElement(By.id("jobTitle")).getText());
        assertEquals("Northstar AI", driver.findElement(By.id("company")).getText());
    }
}
