Feature: PolicyBazaar Car Insurance Form Validation

  Background:
    Given User navigate to the Policybazaar homepage
    And User navigate to the PolicyBazaar car insurance page
    And User proceed without entering a car number

  Scenario: Car Insurance form with valid data - Get Quote button should be enabled
    When User fill in all required car details with valid data
    Then Get Quote button should be enabled

  Scenario: Car Insurance form with blank name - Get Quote button should be disabled
    When User fill car details but leave name field blank
    Then Get Quote button should be disabled

  Scenario: Car Insurance form with invalid phone number - Error message should appear
    When User fill in the required car details
    And User enter an invalid email or phone number
    Then User should see an error message for invalid contact details
    And retrive and print the error message
		