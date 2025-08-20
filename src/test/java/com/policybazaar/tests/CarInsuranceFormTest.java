package com.policybazaar.tests;

import com.policybazaar.pages.CarInsuranceHomePage;
import com.policybazaar.utils.DriverSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.*;

/**
 * TestNG class for Car Insurance Form with 3 test methods
 */
@Epic("PolicyBazaar Automation")
@Feature("Car Insurance")
public class CarInsuranceFormTest extends BaseTest {
    
    private static final Logger logger = LogManager.getLogger(CarInsuranceFormTest.class);
    private CarInsuranceHomePage carPage;
    
    @BeforeClass
    @Step("Setup Car Insurance Test Environment")
    public void setUp() {
        logger.info("Setting up Car Insurance Form Test");
        // Navigate to application (driver is already initialized by BaseTest)
        navigateToHome();
        
        // Initialize CarInsuranceHomePage
        carPage = new CarInsuranceHomePage(driver);
        carPage.clickCarInsurance();
        logger.info("Test setup completed successfully");
    }
    
    @Test(priority = 1, groups = {"valid-data"})
    @Story("Car Insurance Form Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test car insurance form with valid data - Get Quote button should be enabled")
    public void testValidData() {
        logger.info("========== TEST CASE 1: VALID DATA ==========");
        carPage.fillFormWithData();
        
        //Get Quote button should be clickable for valid data
        WebElement getQuoteBtn = driver.findElement(By.xpath("/html/body/section/section/div/div/div/div[2]/div[2]/div/div/div/div[3]/button"));
        Assert.assertTrue(getQuoteBtn.isEnabled(), "Get Quote button should be clickable for valid data");
        takeScreenshot("Car Insurance Valid Data Test");
        
        logger.info("Test Case 1 - Valid Data: COMPLETED");
    }
    
    @Test(priority = 2, groups = {"blank-name"})
    public void testBlankName() {
        logger.info("========== TEST CASE 2: BLANK NAME ==========");
        
        // Navigate fresh and reinitialize
        navigateToHome();
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
        navigateToHome();
        carPage = new CarInsuranceHomePage(driver);
        carPage.clickCarInsurance();
        
        carPage.fillFormWithInvalidPhone();
        
        //Error message should be displayed for invalid phone
        WebElement errorMsg = driver.findElement(By.xpath("/html/body/section/section/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[2]"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message should appear for invalid phone");
        
        logger.info("Test Case 3 - Invalid Phone: COMPLETED");
    }
} 