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
@GetAllMorbidityModule
Feature: GetAllMorbidities.feature

 Background: Dietician Logged In
    Given User creates POST Request for the login API. 
    When  User sends HTTPS Request with valid credentials on the request Body.
    Then  User receives Status with response body containing token information.
   
	Scenario: Check if user as Dietician able to retrieve all morbidity details with "<dataKey>" endpoint and request body.
 	Given Dietician creates GET Request for all Morbidity API endpoint with fields from "<sheetName>" with "<dataKey>".
 	When Dietician sends HTTPS Request for all Morbidity API "<dataKey>".
	Then Dietician receives response for all Morbidity API "<sheetName>" with "<dataKey>".
	 Examples: 
      |dataKey | sheetName|
      |get_all_morbidity_valid|getmorbidity|
      |get_all_morbidity_invalid_endpoint|getmorbidity|
      |get_all_morbidity_invalid_token|getmorbidity|
      |get_all_morbidity_patient_token|getmorbidity|
