package com.policybazaar.tests;

import com.policybazaar.pages.CarInsuranceHomePage;
import com.policybazaar.utils.DriverSetup;
import com.policybazaar.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * TestNG class for Car Insurance Form with 3 test methods
 */
public class CarInsuranceFormTest {
    
    private static final Logger logger = LogManager.getLogger(CarInsuranceFormTest.class);
    private WebDriver driver;
    private CarInsuranceHomePage carPage;
    
    @BeforeClass
    public void setUp() {
        logger.info("Setting up Car Insurance Form Test");
        DriverSetup.initializeDriver(ConfigReader.getProperty("browser"));
        DriverSetup.navigateToApplication();
        driver = DriverSetup.getDriver();
        
        // Initialize CarInsuranceHomePage
        carPage = new CarInsuranceHomePage(driver);
        carPage.clickCarInsurance();
        logger.info("Test setup completed successfully");
    }
    
    @Test(priority = 1, groups = {"valid-data"})
    public void testValidData() {
        logger.info("========== TEST CASE 1: VALID DATA ==========");
        carPage.fillFormWithData();
        
        //Get Quote button should be clickable for valid data
        WebElement getQuoteBtn = driver.findElement(By.xpath("/html/body/section/section/div/div/div/div[2]/div[2]/div/div/div/div[3]/button"));
        Assert.assertTrue(getQuoteBtn.isEnabled(), "Get Quote button should be clickable for valid data");
        
        logger.info("Test Case 1 - Valid Data: COMPLETED");
    }
    
    @Test(priority = 2, groups = {"blank-name"})
    public void testBlankName() {
        logger.info("========== TEST CASE 2: BLANK NAME ==========");
        
        // Navigate fresh and reinitialize
        DriverSetup.navigateToApplication();
        carPage = new CarInsuranceHomePage(driver);
        carPage.clickCarInsurance();
        
        carPage.fillFormWithBlankName();
        
        // Get Quote button should NOT be clickable for blank name
        WebElement getQuoteBtn = driver.findElement(By.xpath("/html/body/section/section/div/div/div/div[2]/div[2]/div/div/div/div[3]/button"));
        Assert.assertFalse(getQuoteBtn.isEnabled(), "Get Quote button should NOT be clickable for blank name");
        
        logger.info("Test Case 2 - Blank Name: COMPLETED");
    }
    
    @Test(priority = 3, groups = {"invalid-phone"})
    public void testInvalidPhone() {
        logger.info("========== TEST CASE 3: INVALID PHONE ==========");
        
        // Navigate fresh and reinitialize
        DriverSetup.navigateToApplication();
        carPage = new CarInsuranceHomePage(driver);
        carPage.clickCarInsurance();
        
        carPage.fillFormWithInvalidPhone();
        
        //Error message should be displayed for invalid phone
        WebElement errorMsg = driver.findElement(By.xpath("/html/body/section/section/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[2]"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message should appear for invalid phone");
        
        logger.info("Test Case 3 - Invalid Phone: COMPLETED");
    }
    
    @AfterClass
    public void tearDown() {
        logger.info("Tearing down Car Insurance Form Test");
        DriverSetup.tearDown();
        logger.info("Test teardown completed");
    }
} 