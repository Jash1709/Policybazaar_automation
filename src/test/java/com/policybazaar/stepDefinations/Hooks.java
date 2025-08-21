package com.policybazaar.stepDefinations;

import com.policybazaar.utils.DriverSetup;
import io.cucumber.java.Before;
import io.cucumber.java.After;

/**
 * Cucumber Hooks class for setup and teardown operations.
 * This class handles @Before and @After annotations for all scenarios.
 */
public class Hooks {
    
    @Before
    public void setUp() {
        System.out.println("Setting up browser for scenario...");
        DriverSetup.initializeDriver(System.getProperty("browser"));
        System.out.println("Browser setup completed");
    }
    
    @After
    public void tearDown() {
        System.out.println("Scenario completed - keeping browser open for next scenario");
        DriverSetup.tearDown();
        // Don't close driver here to allow multiple scenarios to run
        // Driver will be closed by DriverSetup.tearDown() after all scenarios
    }
} 