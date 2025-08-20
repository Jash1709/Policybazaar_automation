package com.policybazaar.main;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;

import com.policybazaar.pages.TravelInsuranceHomePage;
import com.policybazaar.pages.TravelInsuranceResultsPage;
import com.policybazaar.pages.CarInsuranceHomePage;
import com.policybazaar.pages.HealthInsurancePage;
import com.policybazaar.utils.ExcelUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
    
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
        logger.info("=== STARTING POLICYBAZAAR COMPLETE AUTOMATION ===");
        WebDriver driver = null;
        
        try {
            // ===========================================
            // STEP 1: BROWSER SETUP
            // ===========================================
            driver = setupBrowser();
            
            // Navigate to PolicyBazaar
            String url = "https://www.policybazaar.com/";
            logger.info("Navigating to URL: {}", url);
            driver.get(url);
            logger.info("Successfully navigated to PolicyBazaar page");
            
            // ===========================================
            // STEP 2: TRAVEL INSURANCE AUTOMATION
            // ===========================================
            runTravelInsuranceAutomation(driver);
            
            // ===========================================
            // STEP 3: CAR INSURANCE AUTOMATION
            // ===========================================
            runCarInsuranceAutomation(driver);
            
            // ===========================================
            // STEP 4: HEALTH INSURANCE AUTOMATION
            // ===========================================
            runHealthInsuranceAutomation(driver);
            
            logger.info("=== ALL AUTOMATION MODULES COMPLETED SUCCESSFULLY ===");
            
        } catch (Exception e) {
            logger.error("Fatal error during automation execution", e);
            logger.error("Error details: {}", e.getMessage());
            
        } finally {
            if (driver != null) {
                logger.info("Closing browser and cleaning up resources");
                driver.quit();
                logger.info("Browser closed and resources cleaned up successfully");
            }
        }
    }
    
    /**
     * Sets up Chrome browser with optimized configuration
     * @return WebDriver instance
     */
    private static WebDriver setupBrowser() {
        logger.info("=== BROWSER SETUP STARTED ===");
        
        // Setup Chrome driver automatically
        logger.info("Setting up WebDriverManager for Chrome");
        WebDriverManager.chromedriver().setup();
        logger.info("WebDriverManager setup completed successfully");
        
        // Configure Chrome options
        logger.info("Configuring Chrome options");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        logger.info("Chrome options configured successfully");
        
        // Initialize WebDriver
        logger.info("Initializing Chrome WebDriver");
        WebDriver driver = new ChromeDriver(options);
        logger.info("Chrome WebDriver created successfully");
        
        // Configure timeouts and window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
        
        logger.info("=== BROWSER SETUP COMPLETED ===");
        return driver;
    }
    
         /**
      * Executes Travel Insurance automation workflow
      * @param driver WebDriver instance
      */
     private static void runTravelInsuranceAutomation(WebDriver driver) {
         logger.info("=== TRAVEL INSURANCE AUTOMATION STARTED ===");
         
         try {
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
             
             // Initialize Travel Insurance pages
             logger.info("Initializing TravelInsuranceHomePage");
             TravelInsuranceHomePage travelHomePage = new TravelInsuranceHomePage(driver);
             
             // First navigate to Travel Insurance section
             logger.info("Navigating to Travel Insurance section");
             travelHomePage.clickTravelInsurance();
             
             // Wait for travel insurance page to load
             wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
             logger.info("Travel Insurance page loaded successfully");
             
             // Fill the travel insurance form
             logger.info("Starting Travel Insurance form automation");
             travelHomePage.fillFormWithYes(); // You can also use fillFormWithNo() based on requirement
             logger.info("Travel Insurance form automation completed successfully");
             
             // Process results page
             logger.info("Processing Travel Insurance results page");
             TravelInsuranceResultsPage travelResultsPage = new TravelInsuranceResultsPage(driver);
             
             logger.info("Waiting for travel insurance results to load");
             travelResultsPage.waitForResultsToLoad();
             
             logger.info("Sorting travel insurance results by price: Low to High");
             travelResultsPage.sortLowtoHigh();
             
             logger.info("Extracting first 3 travel insurance plans");
             travelResultsPage.extractFirst3Plans();
             
             logger.info("=== TRAVEL INSURANCE AUTOMATION COMPLETED ===");
             
         } catch (Exception e) {
             logger.error("Error during Travel Insurance automation: {}", e.getMessage(), e);
             logger.warn("Travel Insurance automation failed, continuing with other modules...");
         }
     }
    
         /**
      * Executes Car Insurance automation workflow
      * @param driver WebDriver instance
      */
     private static void runCarInsuranceAutomation(WebDriver driver) {
         logger.info("=== CAR INSURANCE AUTOMATION STARTED ===");
         
         try {
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
             
             // Navigate back to home page for car insurance
             logger.info("Navigating back to PolicyBazaar home page for Car Insurance");
             driver.get("https://www.policybazaar.com/");
             
             // Wait for home page to load completely
             wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
             logger.info("Home page loaded successfully");
             
             // Initialize Car Insurance page
             logger.info("Initializing CarInsuranceHomePage");
             CarInsuranceHomePage carHomePage = new CarInsuranceHomePage(driver);
             
             // Click on Car Insurance section
             logger.info("Clicking on Car Insurance section");
             carHomePage.clickCarInsurance();
             
             // Test Case 1: Fill form with valid data
             logger.info("=== Test Case 1: Car Insurance with Valid Data ===");
             carHomePage.fillFormWithData();
             
             // Wait for form completion before proceeding to next test case
             wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
             
             // Test Case 2: Fill form with blank name (navigate back to car insurance)
             logger.info("=== Test Case 2: Car Insurance with Blank Name ===");
             driver.get("https://www.policybazaar.com/");
             wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
             
             // Re-initialize and navigate to car insurance for test case 2
             CarInsuranceHomePage carHomePage2 = new CarInsuranceHomePage(driver);
             carHomePage2.clickCarInsurance();
             carHomePage2.fillFormWithBlankName();
             
             // Test Case 3: Fill form with invalid phone (navigate back to car insurance)  
             logger.info("=== Test Case 3: Car Insurance with Invalid Phone ===");
             driver.get("https://www.policybazaar.com/");
             wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
             
             // Re-initialize and navigate to car insurance for test case 3
             CarInsuranceHomePage carHomePage3 = new CarInsuranceHomePage(driver);
             carHomePage3.clickCarInsurance();
             carHomePage3.fillFormWithInvalidPhone();
             
             logger.info("=== CAR INSURANCE AUTOMATION COMPLETED ===");
             
         } catch (Exception e) {
             logger.error("Error during Car Insurance automation: {}", e.getMessage(), e);
             logger.warn("Car Insurance automation encountered errors, continuing with other modules...");
         }
     }
    
         /**
      * Executes Health Insurance automation workflow
      * @param driver WebDriver instance
      */
     private static void runHealthInsuranceAutomation(WebDriver driver) {
         logger.info("=== HEALTH INSURANCE AUTOMATION STARTED ===");
         
         try {
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
             
             // Navigate back to home page for health insurance
             logger.info("Navigating back to PolicyBazaar home page for Health Insurance");
             driver.get("https://www.policybazaar.com/");
             
             // Wait for home page to load completely
             wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
             logger.info("Home page loaded successfully for Health Insurance");
             
             // Read desired heading(s) from Excel input
             String inputFile = "menu_headings.xlsx";
             String inputSheet = "Sheet1";
             logger.info("Reading menu headings from Excel file: {}", inputFile);
             
             List<String> headings = ExcelUtil.readFirstColumn(inputFile, inputSheet);
             if (headings.isEmpty()) {
                 logger.warn("No headings found in Excel. Defaulting to 'Health Insurance'");
                 headings.add("Health Insurance");
             }
             logger.info("Found {} headings to process: {}", headings.size(), headings);
             
             // Initialize Health Insurance page
             HealthInsurancePage healthPage = new HealthInsurancePage(driver);
             
             // Process each heading and extract menu items
             for (String heading : headings) {
                 logger.info("=== Processing {} Menu Items ===", heading);
                 List<String> menuItems = healthPage.getMenuItemsByHeading(heading);
                 
                 logger.info("Found {} menu items for '{}':", menuItems.size(), heading);
                 for (String item : menuItems) {
                     System.out.println("📋 " + item);
                     logger.debug("Menu item: {}", item);
                 }
             }
             
             // Export Health Insurance menu items to Excel
             String outputFile = "health_insurance_items.xlsx";
             String outputSheet = "Health Insurance";
             logger.info("Exporting Health Insurance menu items to Excel");
             healthPage.exportHealthInsuranceMenuItemsToExcel(outputFile, outputSheet);
             logger.info("Health Insurance items exported to: {} (Sheet: {})", outputFile, outputSheet);
             
             logger.info("=== HEALTH INSURANCE AUTOMATION COMPLETED ===");
             
         } catch (Exception e) {
             logger.error("Error during Health Insurance automation: {}", e.getMessage(), e);
         }
     }
}
