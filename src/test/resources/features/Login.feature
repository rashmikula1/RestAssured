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
@Loginmodule
Feature: LOGIN MODULE

  @01PostTag
  Scenario Outline: Check if the user is able to log in with valid credentials for dietician
    Given User creates POST Request for the dietician API endpoint using "<KeyOption>" and "<Sheetname>" from excel.
    When User sends HTTPS Request with valid credentials on the request Body.
    Then User receives Status with response body "<KeyOption>" and "<Sheetname>" from excel.

    Examples: 
      | KeyOption                    | Sheetname |
      | login_valid_dietician        | login     |
      | login_invalid_dietician      | login     |
      | login_invalidPass_dietician  | login     |
      | login_invalidEmail_dietician | login     |
      | login_missingCred_dietician  | login     |

  @02PostTag
  Scenario Outline: Check if the user is able to log in with invalid API endpoint
    Given User creates POST Request for the dietician API endpoint
    When User sends HTTPS Request with invalid end points
    Then User receives Status with response body
