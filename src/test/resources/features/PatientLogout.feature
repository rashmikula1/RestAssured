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
@tag
Feature: Title of your feature
  I want to use this template for my feature file

  Background: User logged in as patient
   Given User creates POST Request for the patient API endpoint  
    When  User sends HTTPS Request with valid credentials for patient on the request Body.
    Then  User receives Status 200 with response body.
    


  @tag1
  Scenario: Check if the user is able to log out successfully for patient role
    Given User creates GET Request with API endpoint 
    When User sends HTTPS Request 
    Then Server should respond back with HTTP Status code 200 with message 'Logout successful'
    
