package com.policybazaar.pages;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.policybazaar.utils.ExcelUtil;
import com.policybazaar.utils.ConfigReader;

public class TravelInsuranceResultsPage {
    
    private static final Logger logger = LogManager.getLogger(TravelInsuranceResultsPage.class);
    private WebDriver driver;
    
    // Plan cards
    @FindBy(xpath = "//div[contains(@class,'plan')]")
    List<WebElement> plans;
    
    @FindBy(xpath="//*[@id=\'root\']/div/div[2]/aside/section[1]/details/summary/p")
    WebElement clickSortBtn;
  
    
    @FindBy(id="17_sort")
    WebElement sort;
    
    public TravelInsuranceResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        logger.info("TravelInsuranceResultsPage initialized");
    }
    
    public void waitForResultsToLoad() {
        logger.info("Waiting for results to load");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,'plan')]")));
        logger.info("Results loaded successfully");
        System.out.println("✓ Results loaded");
    }
    
    public void sortLowtoHigh() {
        try {
            
            clickSortBtn.click();
            logger.info("Sort button clicked successfully");
            
            
            sort.click();
            logger.info("Sort option selected successfully - Low to High");
        } catch (Exception e) {
            logger.error("Error during sort operation", e);
            throw e;
        }
    }
    
    public LinkedHashMap<String, String> extractFirst3Plans() {
        return extractFirst3Plans(false); // Default to false for backward compatibility
    }
    
    public LinkedHashMap<String, String> extractFirst3Plans(boolean hasMedicalCondition) {
        
        try {
            
            List<WebElement> insuranceProvider = driver.findElements(By.className("quotesCard--insurerName"));
            logger.info("Found {} insurance provider elements", insuranceProvider.size());
            
            
            List<WebElement> insurancePrice = driver.findElements(By.className("premiumPlanPrice"));
            logger.info("Found {} insurance price elements", insurancePrice.size());
            
            LinkedHashMap<String, String> planMap = new LinkedHashMap<>();
            
            // Get first 3 plans
            int count = Math.min(3, Math.min(insuranceProvider.size(), insurancePrice.size()));
            logger.info("Extracting data for {} plans", count);
            
            for(int i = 0; i < count; i++) {
                String providerName = insuranceProvider.get(i).getText();
                String price = insurancePrice.get(i).getText();
                
                planMap.put(providerName, price);
                logger.info("Plan {} extracted - Provider: {}, Price: {}", (i + 1), providerName, price);
            }
            
            // Display the HashMap
            logger.info("Displaying extracted plans on console");
            System.out.println("\n=== FIRST 3 INSURANCE PLANS ===");
            for(String name : planMap.keySet()) {
                System.out.println(name + "::" + planMap.get(name));
            }
            System.out.println("=== END ===");
            logger.info("Console display completed");
            
            // Write to Excel with different sheet names based on medical condition
            String sheetName = hasMedicalCondition ? "Plans With Medical" : "Plans Without Medical";
            logger.info("Writing to Excel with sheet name: {}", sheetName);
            ExcelUtil.writeToExcel(planMap, "travel_insurance_plans.xlsx", sheetName);
            logger.info("Plan extraction and Excel write operation completed successfully for medical condition: {}", hasMedicalCondition);

            return planMap;
            
        } catch (Exception e) {
            logger.error("Error during plan extraction process", e);
            throw e;
        }
    }
} 