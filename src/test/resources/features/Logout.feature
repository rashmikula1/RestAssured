
  @Logout
  Feature: Get Logout


  
  Background: Dietician Logged In
   Given User creates POST Request for the login API. 
    When  User sends HTTPS Request with valid credentials on the request Body.
    Then  User receives Status with response body containing token information.

  @01GetTag
  Scenario: Check if the user is able to log out sucessfully from dietician role
    Given User creates GET Request with API endpoint
    When User sends HTTPS Request 
    Then Server should respond back with HTTP Status code 200 with message 'Logout successful'
   
@02GetTag
  Scenario: Check if the user is able to log out with invalid token
   Given User creates GET Request with API endpoint
    When User sends HTTPS Request with invalid token 
    Then Server should respond back with HTTP Status code 401
    
  @03GetTag
  Scenario: Check if the user is able to log out with missing token
   Given User creates GET Request with API endpoint
    When User sends HTTPS Request with missing token 
    Then Server should respond back with HTTP Status code 401
    
  @04GetTag
  Scenario: Check if the user is able to log out with missing endpoint
   Given User creates GET Request with API endpoint
    When User sends HTTPS Request missing endpoint
    Then Server should respond back with HTTP Status code 404


   @05GetTag
   Scenario: Check if the user is able to log out with invalid endpoint
   Given User creates GET Request with API endpoint
    When User sends HTTPS Request invalid endpoint
    Then Server should respond back with HTTP Status code 404