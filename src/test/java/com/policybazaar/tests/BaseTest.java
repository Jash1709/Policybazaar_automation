package com.policybazaar.tests;

import com.policybazaar.utils.DriverSetup;
import com.policybazaar.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

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
    public void navigateToHome() {
        DriverSetup.navigateToApplication();
    }
} 