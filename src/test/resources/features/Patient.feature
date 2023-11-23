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
@PatientModule
Feature: Patient.feature

  Background: Dietician Logged In
    Given User creates POST Request for the login API. 
    When  User sends HTTPS Request with valid credentials on the request Body.
    Then  User receives Status with response body containing token information.

  Scenario Outline: Check if user able to create a patient with "<dataKey>" endpoint and request body.
    Given User creates POST Request for the dietician/patient API endpoint with fields from "<sheetName>" with "<dataKey>"
    When User sends HTTP POST patient Request
    Then User receives response for POST patient "<sheetName>" with "<dataKey>"
        Examples: 
      |dataKey | sheetName|
      |post_patient_valid|patient|
      |post_patient_duplicate|patient|
      |post_patient_missing_mandatory_firstname|patient|
      |post_patient_missing_mandatory_lastname|patient|
      |post_patient_missing_mandatory_contactnumber|patient|
      |post_patient_missing_mandatory_email|patient|
      |post_patient_incorrect_format_allery|patient|
      |post_patient_incorrect_format_foodcat|patient|
      |post_patient_missing_mandatory_dob|patient|
      |post_patient_incorrect_format_dob|patient|
      |post_patient_incorrect_format_email|patient|
      |post_patient_incorrect_format_contactnumber|patient|
      |post_patient_invalid_endpoint|patient|
      |post_patient_invalid_token|patient|
      |post_patient_patient_token|patient|



