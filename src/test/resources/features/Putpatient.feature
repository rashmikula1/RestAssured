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
@PutpatientModule
Feature: Putpatient.feature


 Background: Dietician Logged In
    Given User creates POST Request for the login API. 
    When  User sends HTTPS Request with valid credentials on the request Body.
    Then  User receives Status with response body containing token information.

 
  Scenario Outline: Check if user able to update a patient with "<dataKey>" endpoint and request body.
    Given User creates Put Request for the dietician/patient API endpoint with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP Put patient Request "<dataKey>"
    Then User receives response for Put patient "<sheetName>" with "<dataKey>"
        Examples: 
      |dataKey | sheetName|
      |put_patient_valid|putpatient|
      |put_patient_incorrect_format_allery|putpatient|
      |put_patient_incorrect_format_foodcat|putpatient|
      |put_patient_incorrect_format_dob|putpatient|
      |put_patient_incorrect_format_email|putpatient|
      |put_patient_incorrect_format_contactnumber|putpatient|
      |put_patient_invalid_endpoint|putpatient|
      |put_patient_invalid_token|putpatient|
      |put_patient_patient_token|putpatient|
      
      

