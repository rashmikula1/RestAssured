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
@GetAllMorbidityByCondition
Feature: GetAllMorbidityByCondition.feature

 Background: Dietician Logged In
    Given User creates POST Request for the login API. 
    When  User sends HTTPS Request with valid credentials on the request Body.
    Then  User receives Status with response body containing token information.
    
Scenario Outline: Check if user as Dietician able to retrieve morbidity details by test name with "<dataKey>" endpoint and request body.
	 	Given User creates GET Request for Morbidity by test name API endpoint with fields from "<sheetName>" with "<dataKey>".
 		When User sends HTTPS Request for Morbidity by test name API "<dataKey>".
		Then User receives response for Morbidity by test name API "<sheetName>" with "<dataKey>".
 	Examples:
	| dataKey ||sheetName|
	|Fasting Glucose||MorbidityWithCondition|
	|Average Glucose||MorbidityWithCondition|
	|Plasma Glucose||MorbidityWithCondition|
	|HBA1C||MorbidityWithCondition|
	|TSH||MorbidityWithCondition|
	|T3||MorbidityWithCondition|
	|T4||MorbidityWithCondition|
	

