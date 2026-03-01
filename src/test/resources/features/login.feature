Feature: Login

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters valid credentials
    Then the user should be redirected to the home page

  Scenario: Successful login with valid credentials2
    Given the user is on the login page
    When the user enters valid credentials
    Then the user should be redirected to the home page