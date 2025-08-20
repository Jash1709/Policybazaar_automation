package com.policybazaar.stepDefinations;

import java.io.IOException;
import java.util.List;

import factory.BaseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.ExcelUtil;
import utilities.ScreenShot;

public class HealthInsuranceSteps {

	
	@When("user hover over the Health Insurance menu")
	public void user_hover_over_the_health_insurance_menu() throws IOException {
	    //hover to health insurance options
		BaseClass.getLogger().info("Go to Insurance Products --> Retrieve health Insurance Options.");
		BaseStep.homePageObject.hoverHealInsuranceMenu();
		ScreenShot.screenShotTC(BaseClass.getDriver(), "HealtInsuranceOptions");
	}

	@Then("user retrieve all menu items under Health Insurance and print count")
	public void user_retrieve_all_menu_items_under_health_insurance_and_print_count() throws IOException {
	    //get total number of health Insurance
		BaseClass.getLogger().info("Count the list options --> Display list");
		int totalOptions = BaseStep.homePageObject.getCountOfHealthInsuranceOptions();
		ExcelUtil.writeDataIntoExcel("HealthInsurance", 1, 0, String.valueOf(totalOptions));
		
		ScreenShot.screenShotTC(BaseClass.getDriver(), "HealthInsuranceOptions");
	}

	@Then("user display the list of menu items")
	public void user_display_the_list_of_menu_items() {
	    //get list of health Insurance options
		List<String> healthInsuranceOptions = BaseStep.homePageObject.getHealthInsuranceOptionsList();
		int r=1;
		for(String option: healthInsuranceOptions) {
			ExcelUtil.writeDataIntoExcel("HealthInsurance", r++, 1, option);
		}
	}

}
