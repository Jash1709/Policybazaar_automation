package com.policybazaar.stepDefinations;

import com.policybazaar.utils.DriverSetup;

import io.cucumber.java.en.Given;

/**
 * Common Step Definitions shared across all feature files.
 * This class contains @Given steps that are used by multiple features.
 */
public class CommonSteps {
    
    @Given("User navigate to the Policybazaar homepage")
    public void user_navigate_to_the_policybazaar_homepage() {
        System.out.println("🏠 Navigating to PolicyBazaar homepage...");
        DriverSetup.navigateToApplication();
    }
} 