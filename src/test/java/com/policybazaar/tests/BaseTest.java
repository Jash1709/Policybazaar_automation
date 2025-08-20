package com.policybazaar.tests;

import com.policybazaar.utils.DriverSetup;
import com.policybazaar.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Base test class to manage WebDriver at suite level
 * All test classes should extend this to share the same browser instance
 */
public class BaseTest {
    
    protected static WebDriver driver;
    
    @BeforeSuite
    public void setUpSuite() {
        System.out.println("========== INITIALIZING BROWSER FOR ALL TESTS ==========");
        DriverSetup.initializeDriver(ConfigReader.getProperty("browser"));
        DriverSetup.navigateToApplication();
        driver = DriverSetup.getDriver();
        System.out.println("Browser initialized successfully for suite execution");
    }
    
    @AfterSuite
    public void tearDownSuite() {
        System.out.println("========== CLOSING BROWSER AFTER ALL TESTS ==========");
        DriverSetup.tearDown();
        System.out.println("Browser closed successfully after suite execution");
    }
    
    /**
     * Get the shared WebDriver instance
     * @return WebDriver instance
     */
    public WebDriver getDriver() {
        return driver;
    }
    
    /**
     * Navigate back to the home page (useful between test classes)
     */
    @Step("Navigate to home page")
    public void navigateToHome() {
        DriverSetup.navigateToApplication();
    }
    
    /**
     * Take screenshot for Allure report
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        if (driver != null) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return null;
    }
    
    /**
     * Take screenshot with custom name
     */
    @Attachment(value = "{name}", type = "image/png")
    public byte[] takeScreenshot(String name) {
        return takeScreenshot();
    }
} 