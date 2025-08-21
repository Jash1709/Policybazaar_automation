Feature: PolicyBazaar Travel Insurance

  Background:
    Given User navigate to the Policybazaar homepage

  Scenario: Get travel insurance quotes without medical conditions
    Given User navigate to the Travel Insurance page
    When User fill travel insurance form without medical conditions
    Then User should be navigated to travel insurance results page
    And User should see travel insurance plans and extract top 3 plans

  Scenario: Get travel insurance quotes with medical conditions
    Given User navigate to the Travel Insurance page
    When User fill travel insurance form with medical conditions
    Then User should be navigated to travel insurance results page
    And User should see travel insurance plans and extract top 3 plans

  Scenario: Validate incomplete travel insurance form submission
    Given User navigate to the Travel Insurance page
    When User fill incomplete travel insurance form for validation
    Then User should see validation error for incomplete form 