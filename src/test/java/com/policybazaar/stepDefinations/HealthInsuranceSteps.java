package com.policybazaar.stepDefinations;

import java.io.IOException;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import com.policybazaar.pages.HealthInsurancePage;
import com.policybazaar.utils.DriverSetup;
import com.policybazaar.utils.ScreenshotUtil;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HealthInsuranceSteps {

	private HealthInsurancePage healthPage;
	private List<String> healthInsuranceOptions;
	
	@When("User hover over the Health Insurance menu")
	public void user_hover_over_the_health_insurance_menu() throws IOException {
	    System.out.println("🏥 Hovering over Health Insurance menu");
		healthPage = new HealthInsurancePage(DriverSetup.getDriver());
		ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "HealthInsurance_MenuHover");
		System.out.println("✅ Health Insurance menu hovered successfully");
	}

	@Then("User retrieve all menu items under Health Insurance and print count")
	public void user_retrieve_all_menu_items_under_health_insurance_and_print_count() throws IOException {
	    System.out.println("📊 Retrieving and counting Health Insurance menu items");
		healthInsuranceOptions = healthPage.getAllHealthInsuranceMenuItems();
		int totalOptions = healthInsuranceOptions.size();
		System.out.println("📋 Total Health Insurance Options: " + totalOptions);
		
		// Verify minimum count
		Assert.assertTrue(totalOptions >= 8, "Expected at least 8 Health Insurance items, found: " + totalOptions);
		ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "HealthInsurance_OptionsCount");
	}

	@Then("User display the list of menu items")
	public void user_display_the_list_of_menu_items() {
	    System.out.println("📝 Displaying Health Insurance menu items:");
		for (int i = 0; i < healthInsuranceOptions.size(); i++) {
			System.out.println((i + 1) + ". " + healthInsuranceOptions.get(i));
		}
		
		// Verify no duplicates
		Set<String> unique = new HashSet<>(healthInsuranceOptions);
		Assert.assertEquals(unique.size(), healthInsuranceOptions.size(), "Duplicate items found in Health Insurance menu");
		System.out.println("✅ All menu items displayed and validated");
	}

}
