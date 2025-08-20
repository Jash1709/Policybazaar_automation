package com.policybazaar.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Model class representing car insurance form data
 */
public class CarInsuranceFormData {
    
    private static final Logger logger = LogManager.getLogger(CarInsuranceFormData.class);
    
    private String city;
    private String carBrand;
    private String fuelType;
    private String carVariant;
    private String fullName;
    private String mobileNumber;
    
    // Default constructor
    public CarInsuranceFormData() {
        logger.debug("Creating new CarInsuranceFormData with default constructor");
    }
    
    // Parameterized constructor
    public CarInsuranceFormData(String city, String carBrand, String fuelType, String carVariant, 
                               String fullName, String mobileNumber) {
        logger.debug("Creating new CarInsuranceFormData with parameters");
        this.city = city;
        this.carBrand = carBrand;
        this.fuelType = fuelType;
        this.carVariant = carVariant;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        logger.debug("CarInsuranceFormData created successfully");
    }
    
    // Getters
    public String getCity() {
        logger.debug("Getting city: {}", city);
        return city;
    }
    
    public String getCarBrand() {
        logger.debug("Getting carBrand: {}", carBrand);
        return carBrand;
    }
    
    public String getFuelType() {
        logger.debug("Getting fuelType: {}", fuelType);
        return fuelType;
    }
    
    public String getCarVariant() {
        logger.debug("Getting carVariant: {}", carVariant);
        return carVariant;
    }
    
    public String getFullName() {
        logger.debug("Getting fullName: {}", fullName);
        return fullName;
    }
    
    public String getMobileNumber() {
        logger.debug("Getting mobileNumber: {}", mobileNumber);
        return mobileNumber;
    }
    
    // Setters
    public void setCity(String city) {
        logger.debug("Setting city from '{}' to '{}'", this.city, city);
        this.city = city;
    }
    
    public void setCarBrand(String carBrand) {
        logger.debug("Setting carBrand from '{}' to '{}'", this.carBrand, carBrand);
        this.carBrand = carBrand;
    }
    
    public void setFuelType(String fuelType) {
        logger.debug("Setting fuelType from '{}' to '{}'", this.fuelType, fuelType);
        this.fuelType = fuelType;
    }
    
    public void setCarVariant(String carVariant) {
        logger.debug("Setting carVariant from '{}' to '{}'", this.carVariant, carVariant);
        this.carVariant = carVariant;
    }
    
    public void setFullName(String fullName) {
        logger.debug("Setting fullName from '{}' to '{}'", this.fullName, fullName);
        this.fullName = fullName;
    }
    
    public void setMobileNumber(String mobileNumber) {
        logger.debug("Setting mobileNumber from '{}' to '{}'", this.mobileNumber, mobileNumber);
        this.mobileNumber = mobileNumber;
    }
    
    @Override
    public String toString() {
        String result = "CarInsuranceFormData{" +
                "city='" + city + '\'' +
                ", carBrand='" + carBrand + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", carVariant='" + carVariant + '\'' +
                ", fullName='" + fullName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
        logger.debug("Converting CarInsuranceFormData to string: {}", result);
        return result;
    }
    
    /**
     * Validates the form data
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        boolean valid = city != null && !city.trim().isEmpty() &&
                       carBrand != null && !carBrand.trim().isEmpty() &&
                       fullName != null && !fullName.trim().isEmpty() &&
                       mobileNumber != null && !mobileNumber.trim().isEmpty();
        
        logger.debug("Validation result for CarInsuranceFormData: {}", valid);
        return valid;
    }
} 