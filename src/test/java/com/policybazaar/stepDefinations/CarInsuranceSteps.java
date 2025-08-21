package com.policybazaar.stepDefinations;

import java.io.IOException;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import com.policybazaar.pages.CarInsuranceHomePage;
import com.policybazaar.models.CarInsuranceFormData;
import com.policybazaar.utils.DriverSetup;
import com.policybazaar.utils.JsonDataReader;
import com.policybazaar.utils.ScreenshotUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CarInsuranceSteps {

	private CarInsuranceHomePage carPage;
	private CarInsuranceFormData validData;
	private CarInsuranceFormData blankNameData;
	private CarInsuranceFormData invalidPhoneData;

	@Given("User navigate to the PolicyBazaar car insurance page")
	public void user_navigate_to_the_policy_bazaar_car_insurance_page() {
		System.out.println("🚗 Navigating to Car Insurance page");
		carPage = new CarInsuranceHomePage(DriverSetup.getDriver());
		carPage.clickCarInsurance();
		System.out.println("✅ Car Insurance page loaded");
	}

	@When("User proceed without entering a car number")
	public void user_proceed_without_entering_a_car_number() {
		System.out.println("🔄 Proceeding without car number (clicking continue)");
		carPage.continueWithoutCarNumber();
	}

	@When("User fill in the required car details")
	public void user_fill_in_the_required_car_details() {
		System.out.println("📝 Filling required car details (city, brand, fuel type)");
		invalidPhoneData = JsonDataReader.readCarInsuranceData("car_insurance_data_invalid_phone.json");
		carPage.setCity(invalidPhoneData.getCity());
		carPage.setCarBrand(invalidPhoneData.getCarBrand());
		carPage.setFuelType(invalidPhoneData.getFuelType());
		carPage.setCarVariant(invalidPhoneData.getCarVariant());
	}

	@When("User enter an invalid email or phone number")
	public void user_enter_an_invalid_email_and_phone_number() {
		System.out.println("❌ Entering name and invalid phone number");
		carPage.setName(invalidPhoneData.getFullName());
		carPage.setMobileNumber(invalidPhoneData.getMobileNumber());
	}

	@Then("User should see an error message for invalid contact details")
	public void user_should_see_an_error_message_for_invalid_contact_details() throws IOException {
	    System.out.println("🔍 Validating error message appears");
	    WebElement errorMsg = DriverSetup.getDriver().findElement(By.xpath("//div[@class='errorMsg']"));
	    Assert.assertTrue(errorMsg.isDisplayed(), "Error message should appear for invalid phone");
	    ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "CarInsurance_InvalidPhone_Error");
	    System.out.println("✅ Error message validation completed");
	}

	@Then("retrive and print the error message")
	public void retrive_and_print_the_error_message() {
		System.out.println("📋 Retrieving and printing error message");
	    WebElement errorElement = DriverSetup.getDriver().findElement(By.xpath("//div[@class='errorMsg']"));
	    String errorMessage = errorElement.getText();
	    System.out.println("🚫 Error Message: " + errorMessage);
	}

	// =============== VALID DATA SCENARIO ===============
	@When("User fill in all required car details with valid data")
	public void user_fill_in_all_required_car_details_with_valid_data() {
		System.out.println("✅ Filling all car details with valid data");
		carPage.fillFormWithData();
	}

	@Then("Get Quote button should be enabled")
	public void get_quote_button_should_be_enabled() {
		System.out.println("🔍 Verifying Get Quote button is enabled");
			    WebElement getQuoteBtn = DriverSetup.getDriver().findElement(By.xpath("/html/body/section/section/div/div/div/div[2]/div[2]/div/div/div/div[3]/button"));
	    Assert.assertTrue(getQuoteBtn.isEnabled(), "Get Quote button should be clickable for valid data");
	    ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "CarInsurance_ValidData_GetQuoteEnabled");
		System.out.println("✅ Get Quote button is enabled - Valid data test passed");
	}

	// =============== BLANK NAME SCENARIO ===============
	@When("User fill car details but leave name field blank")
	public void user_fill_car_details_but_leave_name_field_blank() {
		System.out.println("❌ Filling car details with blank name");
		carPage.fillFormWithBlankName();
	}

	@Then("Get Quote button should be disabled")
	public void get_quote_button_should_be_disabled() {
		System.out.println("🔍 Verifying Get Quote button is disabled");
			    WebElement getQuoteBtn = DriverSetup.getDriver().findElement(By.xpath("/html/body/section/section/div/div/div/div[2]/div[2]/div/div/div/div[3]/button"));
	    Assert.assertFalse(getQuoteBtn.isEnabled(), "Get Quote button should NOT be clickable for blank name");
	    ScreenshotUtil.takeScreenshot(DriverSetup.getDriver(), "CarInsurance_BlankName_GetQuoteDisabled");
		System.out.println("✅ Get Quote button is disabled - Blank name test passed");
	}

}
