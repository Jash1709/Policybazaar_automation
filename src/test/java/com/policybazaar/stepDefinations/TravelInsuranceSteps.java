package com.policybazaar.stepDefinations;

import java.util.LinkedHashMap;
import java.util.List;
import java.io.File;

import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import com.policybazaar.pages.TravelInsuranceHomePage;
import com.policybazaar.pages.TravelInsuranceResultsPage;
import com.policybazaar.utils.DriverSetup;
import com.policybazaar.utils.ScreenshotUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TravelInsuranceSteps {
    
    private TravelInsuranceHomePage travelPage;
    private TravelInsuranceResultsPage resultsPage;
    
    @Given("User navigate to the Travel Insurance page")
    public void user_navigate_to_the_travel_insurance_page() {
        System.out.println("✈️ Navigating to Travel Insurance page");
        travelPage = new TravelInsuranceHomePage(DriverSetup.getDriver());
        travelPage.clickTravelInsurance();
        resultsPage = new TravelInsuranceResultsPage(DriverSetup.getDriver());
        System.out.println("✅ Travel Insurance page loaded");
    }
    
    @When("User fill travel insurance form without medical conditions")
    public void user_fill_travel_insurance_form_without_medical_conditions() {
        System.out.println("📝 Filling travel insurance form (No medical conditions)");
        travelPage.fillFormWithNo();
        ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "TravelInsurance_FormFilled_NoMedical");
    }
    
    @Then("User should be navigated to travel insurance results page")
    public void user_should_be_navigated_to_travel_insurance_results_page() {
        System.out.println("🔍 Verifying navigation to results page");
        resultsPage.waitForResultsToLoad();
        String resultsUrl = DriverSetup.getDriver().getCurrentUrl();
        Assert.assertTrue(resultsUrl.contains("/quotes"), "User is not navigated to results page. Current URL: " + resultsUrl);
        
        List<WebElement> planCards = DriverSetup.getDriver().findElements(By.xpath("//*[contains(@class,'plan')]"));
        Assert.assertTrue(planCards.size() > 0, "No plan cards present on the results page");
        System.out.println("✅ Successfully navigated to results page with " + planCards.size() + " plans");
    }
    
    @When("User fill travel insurance form with medical conditions")
    public void user_fill_travel_insurance_form_with_medical_conditions() {
        System.out.println("📝 Filling travel insurance form (With medical conditions)");
        travelPage.fillFormWithYes();
        ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "TravelInsurance_FormFilled_WithMedical");
    }
    
    @Then("User should see travel insurance plans and extract top 3 plans")
    public void user_should_see_travel_insurance_plans_and_extract_top_3_plans() {
        System.out.println("📊 Extracting top 3 travel insurance plans");
        resultsPage.waitForResultsToLoad();
        resultsPage.sortLowtoHigh();
        
        LinkedHashMap<String, String> top3Plans = resultsPage.extractFirst3Plans(false);
        Assert.assertTrue(top3Plans.size() > 0, "No plans extracted");
        
        System.out.println("📋 Top 3 Travel Insurance Plans:");
        int count = 1;
        for (String planName : top3Plans.keySet()) {
            System.out.println(count + ". " + planName + " - Price: " + top3Plans.get(planName));
            count++;
        }
        
        // Verify Excel file was created
        File outFile = new File("travel_insurance_plans.xlsx");
        Assert.assertTrue(outFile.exists(), "Expected Excel file not found: " + outFile.getAbsolutePath());
        Assert.assertTrue(outFile.length() > 0, "Excel file appears to be empty: " + outFile.getAbsolutePath());
        System.out.println("✅ Plans extracted and saved to Excel successfully");
    }
    
    @When("User fill incomplete travel insurance form for validation")
    public void user_fill_incomplete_travel_insurance_form_for_validation() {
        System.out.println("⚠️ Filling incomplete travel insurance form");
        travelPage.fillFormWithValidation();
        ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "TravelInsurance_IncompleteForm");
    }
    

    
    @Then("User should see validation error for incomplete form")
    public void user_should_see_validation_error_for_incomplete_form() {
        System.out.println("🔍 Checking for validation errors");
        String pageSource = DriverSetup.getDriver().getPageSource();
        boolean hasValidationError = pageSource.contains("Please") || 
                                   pageSource.contains("required") || 
                                   pageSource.contains("error");
        
        Assert.assertTrue(hasValidationError, "Validation error message should appear on page");
        ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "TravelInsurance_ValidationError");
        System.out.println("✅ Validation error detected successfully");
    }
}
