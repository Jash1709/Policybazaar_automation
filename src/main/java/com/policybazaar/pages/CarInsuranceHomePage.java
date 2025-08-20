package com.policybazaar.pages;
 
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.policybazaar.models.CarInsuranceFormData;
import com.policybazaar.utils.JsonDataReader;
import com.policybazaar.utils.ScreenshotUtil;

 
public class CarInsuranceHomePage {
 
	private static final Logger logger = LogManager.getLogger(CarInsuranceHomePage.class);
	private WebDriver driver;
	private JavascriptExecutor js;
 
	public CarInsuranceHomePage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
		logger.info("CarInsuranceHomePage initialized successfully");
	}
	
	@FindBy(xpath="//span[contains(text(),'91% Discount')]/ancestor::div[contains(@class,'prd-icon')]")
	WebElement carInsuranceLink;
	
	//div[@class='headingV3 fontNormal']//span[contains(@class, 'blueTextButton') and contains(text(), 'Click here')]

	@FindBy(xpath="//span[contains(@class,'blueTextButton') and contains(text(),'Click here')]")
	WebElement clickHere;

	@FindBy(xpath="//input[@placeholder='Search City' and contains(@class,'form-control')]")

	WebElement cityField;

	@FindBy(xpath="//div[contains(@class,'dropdown')]//div[contains(@class,'options') and contains(@class,'open')]//div[contains(@class,'option')]")

	WebElement cityOption;

	@FindBy(xpath="//input[@placeholder='Search Car Make']")
	WebElement carBrandField;

	@FindBy(xpath="//div[@class='options open']/div")

	WebElement carOption;
	
	@FindBy(xpath="//input[@placeholder='Search Car Vaiants' and contains(@class,'form-control')]")
	WebElement carVariantInput;
	
	@FindBy(xpath="//div[contains(@class,'options open')]/div")
	WebElement carVariantOption;

	@FindBy(xpath="//input[@id='name-form-control']")

	WebElement nameField;

	@FindBy(xpath="//input[@id='mobile-form-control']")

	WebElement mobileNumberField;

	@FindBy(xpath="//div[@class='errorMsg']")

	WebElement errMsg;
 
	public void clickCarInsurance() {
        logger.info("Clicking Car Insurance link from HomePage");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(carInsuranceLink));
        carInsuranceLink.click();
        logger.info("Clicked Car Insurance link successfully");
    }

	public void continueWithoutCarNumber() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement clickHere = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//span[contains(@class,'blueTextButton') and contains(text(),'Click here')]")));
	    js.executeScript("arguments[0].scrollIntoView(false)", clickHere);
	    js.executeScript("arguments[0].click()", clickHere);
	}



	public void setCity(String city) {
		logger.debug("Setting city: {}", city);
		cityField.sendKeys(city);
		cityOption.click();
		logger.info("City '{}' is selected successfully", city);
	}

	public void setCarBrand(String carBrand) {
		logger.debug("Setting car brand: {}", carBrand);
		carBrandField.sendKeys(carBrand);
		carOption.click();
		logger.info("Car brand '{}' is selected successfully", carBrand);
	}

	public void setFuelType(String fuelType) {
	    try {
	        logger.debug("Setting fuel type: {}", fuelType);
	        WebElement fuelOption = driver.findElement(By.xpath("//ul[contains(@class,'gridList')]//li[normalize-space()='" + fuelType + "']"));
	        fuelOption.click();
	        logger.info("Fuel type '{}' is selected successfully", fuelType);
	    } catch (Exception e) {
	        logger.warn("Fuel type '{}' is not available: {}", fuelType, e.getMessage());
	    }
	}

	
	public void setCarVariant(String carVariant) {
		logger.debug("Setting car variant: {}", carVariant);
		carVariantInput.sendKeys(carVariant);
		carVariantOption.click();
		logger.info("Car variant '{}' is set successfully", carVariant);
	}
	
	
	public void setName(String name) {
		logger.debug("Setting name: {}", name);
		nameField.sendKeys(name);
		logger.info("Name '{}' is entered in name field", name);
	}

	public void setMobileNumber(String mobileNumber) {
		logger.debug("Setting mobile number: {}", mobileNumber);
		mobileNumberField.sendKeys(mobileNumber);
		logger.info("Mobile number '{}' is entered in mobile number field", mobileNumber);
	}
	
	public void setNameAndPhone(String name,String mobileNumber) {
		logger.debug("Setting name and phone - Name: {}, Mobile: {}", name, mobileNumber);
		nameField.sendKeys(name);
		logger.info("Name '{}' is entered in name field", name);
		
		mobileNumberField.sendKeys(mobileNumber);
		logger.info("Mobile number '{}' is entered in mobile number field", mobileNumber);
		
		// Take screenshot after filling name and phone
		try {
			// Wait for fields to be properly filled using WebDriverWait
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.attributeToBe(nameField, "value", name != null ? name : ""));
			
			if (name == null || name.trim().isEmpty()) {
				ScreenshotUtil.takeErrorScreenshot(driver, "CarInsuranceForm", "BlankNameError");
				logger.info("Screenshot taken for blank name scenario");
			} else if (mobileNumber.length() < 10) {
				ScreenshotUtil.takeErrorScreenshot(driver, "CarInsuranceForm", "InvalidPhoneError");
				logger.info("Screenshot taken for invalid phone scenario");
			} else {
				ScreenshotUtil.takeScreenshot(driver, "CarInsuranceForm_ValidData");
				logger.info("Screenshot taken for valid data scenario");
			}
		} catch (Exception e) {
			logger.error("Error while taking screenshot: {}", e.getMessage());
		}
	}
	

	public String validateErrorMessage() {
		String expectedError = "Enter a valid mobile number";
		String actualError = errMsg.getText();
		
		logger.debug("Validating error message - Expected: '{}', Actual: '{}'", expectedError, actualError);

		if(actualError.equals(expectedError)) {
			logger.info("Error validation: PASS");
			return "PASS";
		}

		logger.warn("Error validation: FAIL - Expected: '{}', but got: '{}'", expectedError, actualError);
		return "FAIL";
	}
//	public void valid() {
//		
//		continueWithoutCarNumber();
//		setCity("mumbai");
//		
//	}

	public String getErrorMsg() {
		String errMessage = errMsg.getText();
		logger.info("Retrieved error message: {}", errMessage);
		return errMessage;
	}

	/**
	 * Fills the complete car insurance form using data from model class
	 * @param formData CarInsuranceFormData object containing form data
	 */
	public void fillFormWithData() {
		logger.info("Starting to fill form with valid data");
		CarInsuranceFormData formData = JsonDataReader.readCarInsuranceData("car_insurance_data.json");
		logger.debug("Filling form with data: {}", formData.toString());
		
		// Continue without car number first
		continueWithoutCarNumber();
		
		// Fill city
		if (formData.getCity() != null) {
			setCity(formData.getCity());
		}
		
		// Fill car brand
		if (formData.getCarBrand() != null) {
			setCarBrand(formData.getCarBrand());
		}
		
		// Fill fuel type
		if (formData.getFuelType() != null) {
			setFuelType(formData.getFuelType());
		}
		
		// Fill car variant
		if (formData.getCarVariant() != null) {
			setCarVariant(formData.getCarVariant());
		}
		
		// Fill name and mobile number
		if (formData.getFullName() != null && formData.getMobileNumber() != null) {
			setNameAndPhone(formData.getFullName(), formData.getMobileNumber());
		}
		
		logger.info("Form filled successfully with valid data");
	}
	
	/**
	 * Fills the car insurance form with blank name (Test Case 2)
	 * This method tests validation when name field is empty
	 */
	public void fillFormWithBlankName() {
		logger.info("Test Case 2: Starting to fill form with blank name");
		
		try {
			// Read data from JSON file for blank name test case
			CarInsuranceFormData formData = JsonDataReader.readCarInsuranceData("car_insurance_data_blank_name.json");
			
			logger.debug("Loaded test data: {}", formData.toString());
			
			// Continue without car number first
			continueWithoutCarNumber();
			
			// Fill city
			if (formData.getCity() != null && !formData.getCity().isEmpty()) {
				setCity(formData.getCity());
			}
			
			// Fill car brand
			if (formData.getCarBrand() != null && !formData.getCarBrand().isEmpty()) {
				setCarBrand(formData.getCarBrand());
			}
			
			// Fill fuel type
			if (formData.getFuelType() != null && !formData.getFuelType().isEmpty()) {
				setFuelType(formData.getFuelType());
			}
			
			// Fill car variant
			if (formData.getCarVariant() != null && !formData.getCarVariant().isEmpty()) {
				setCarVariant(formData.getCarVariant());
			}
			
			// Fill name and mobile number (name will be blank)
			setNameAndPhone(formData.getFullName(), formData.getMobileNumber());
			
			logger.info("Form filled with blank name - ready for validation testing");
			
		} catch (Exception e) {
			logger.error("Error in fillFormWithBlankName: {}", e.getMessage(), e);
		}
	}
	
	/**
	 * Fills the car insurance form with invalid phone number (Test Case 3)
	 * This method tests validation when phone number is incorrect
	 */
	public void fillFormWithInvalidPhone() {
		logger.info("Test Case 3: Starting to fill form with invalid phone number");
		
		try {
			// Read data from JSON file for invalid phone test case
			CarInsuranceFormData formData = JsonDataReader.readCarInsuranceData("car_insurance_data_invalid_phone.json");
			
			logger.debug("Loaded test data: {}", formData.toString());
			
			// Continue without car number first
			continueWithoutCarNumber();
			
			// Fill city
			if (formData.getCity() != null && !formData.getCity().isEmpty()) {
				setCity(formData.getCity());
			}
			
			// Fill car brand
			if (formData.getCarBrand() != null && !formData.getCarBrand().isEmpty()) {
				setCarBrand(formData.getCarBrand());
			}
			
			// Fill fuel type
			if (formData.getFuelType() != null && !formData.getFuelType().isEmpty()) {
				setFuelType(formData.getFuelType());
			}
			
			// Fill car variant
			if (formData.getCarVariant() != null && !formData.getCarVariant().isEmpty()) {
				setCarVariant(formData.getCarVariant());
			}
			
			// Fill name and mobile number (phone will be invalid)
			if (formData.getFullName() != null && formData.getMobileNumber() != null) {
				setNameAndPhone(formData.getFullName(), formData.getMobileNumber());
			}
			
			logger.info("Form filled with invalid phone number - ready for validation testing");
			
		} catch (Exception e) {
			logger.error("Error in fillFormWithInvalidPhone: {}", e.getMessage(), e);
		}
	}

 
}

 