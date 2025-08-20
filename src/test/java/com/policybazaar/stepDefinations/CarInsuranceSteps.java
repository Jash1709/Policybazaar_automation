package com.policybazaar.stepDefinations;

import java.io.IOException;

import com.policybazaar.pages.CarInsuranceHomePage;

import factory.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.policybazaar.pages.CarInsuranceHomePage;
import com.policybazaar.utils.ExcelUtil;
import utilities.XmlReader;
import utilities.ScreenShot;

public class CarInsuranceSteps  {
	
	
	CarInsuranceHomePage carInsuranceObject;
	String[] carData = XmlReader.getCarData();

	@Givsen("User navigate to the PolicyBazaar car insurance page")
	public void user_navigate_to_the_policy_bazaar_car_insurance_page() {
		BaseClass.getLogger().info("Got to products section --> click on Car Insurance");
		
		System.out.println("-----------------------------");
		System.out.println("Home page is opened.");
	    BaseStep.homePageObject.clickCarInsurance();
		
	}

	@When("User proceed without entering a car number")
	public void user_proceed_without_entering_a_car_number() {
		carInsuranceObject = new CarInsuranceHomePage(BaseClass.getDriver());
		carInsuranceObject.clickCarInsurance();;
	}

	@When("User fill in the required car details")
	public void user_fill_in_the_required_car_details() {
		BaseClass.getLogger().info("Fill the required car details");
		carInsuranceObject.setCity(carData[0]);
		carInsuranceObject.setCarBrand(carData[1]);
		carInsuranceObject.setFuelType(carData[2]);
		
	}

	@When("User enter an invalid email or phone number")
	public void user_enter_an_invalid_email_and_phone_number() {
		BaseClass.getLogger().info("Fill the Name and Invalid phone number");
	    carInsuranceObject.setName(carData[3]);
	    carInsuranceObject.setMobileNumber(carData[4]);
	}

	@Then("User should see an error message for invalid contact details")
	public void user_should_see_an_error_message_for_invalid_contact_details() throws IOException {
	    String status = carInsuranceObject.validateErrorMessage();
	    ExcelUtil.writeDataIntoExcel("CarInsurance", 1, 0, status);
	    ScreenShot.screenShotTC(BaseClass.getDriver(), "CarErrorMessage");
	}

	@Then("retrive and print the error message")
	public void retrive_and_print_the_error_message() {
		BaseClass.getLogger().info("Get the error message and display it.");
	    String errMessage = carInsuranceObject.getErrorMsg();
	    
	    ExcelUtil.writeDataIntoExcel("CarInsurance", 1, 1, errMessage);
	    
	}



	

}
