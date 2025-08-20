Feature: PolicyBazar Car Insurance
	
	Scenario: Get the car Insurance Error message with Invalid phone number or Email
		Given User navigate to the Policybazaar homepage
		And User navigate to the PolicyBazaar car insurance page
        When User proceed without entering a car number
	    And User fill in the required car details
	    And User enter an invalid email or phone number
	    Then User should see an error message for invalid contact details
		And retrive and print the error message
		