#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@GetpatientModule
Feature: Getpatient.feature


 Background: Dietician Logged In
    Given User creates POST Request for the login API. 
    When  User sends HTTPS Request with valid credentials on the request Body.
    Then  User receives Status with response body containing token information.

 
  Scenario Outline: Check if user able to retrieve all patients with "<dataKey>" endpoint and request body.
    Given User creates GET Request for the dietician/patient API endpoint with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP Get patient Request "<dataKey>"
    Then User receives response for Get patient "<sheetName>" with "<dataKey>"
        Examples: 
      |dataKey | sheetName|
      |get_patient_valid|getpatient|
      |get_patient_invalid_endpoint|getpatient|
      |get_patient_invalid_token|getpatient|
      |get_patient_patient_token|getpatient|
