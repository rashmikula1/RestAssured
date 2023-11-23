@PatientLogin
  Feature: Patient Login



     
   Background: Dietician Logged In
   Given User creates POST Request for the login API. 
    When  User sends HTTPS Request with credentials on the request Body.
    Then  User receives Status with response body containing token information.
    
   @01PostTag
  Scenario: Check if the user is able to log in with valid credentials for patient
    Given User creates POST Request for the patient API endpoint  
    When  User sends HTTPS Request with valid credentials for patient on the request Body.
    Then  User receives Status 200 with response body.

 