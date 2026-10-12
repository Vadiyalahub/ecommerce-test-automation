@saucedemo @login
Feature: Login
  As a customer
  I want to log in to the store
  So that I can buy products

  Background:
    Given I am on the login page

  @smoke
  Scenario: Successful login with a valid user
    When I log in as a "standard" user
    Then I should see the products page

  @regression
  Scenario: Locked out user cannot log in
    When I log in as a "locked" user
    Then I should see the login error "Epic sadface: Sorry, this user has been locked out."

  @regression
  Scenario Outline: Login fails with wrong or missing details
    When I log in with username "<username>" and password "<password>"
    Then I should see the login error "<error>"

    Examples:
      | username      | password     | error                                                                     |
      | not_a_user    | secret_sauce | Epic sadface: Username and password do not match any user in this service |
      | standard_user | wrong_pass   | Epic sadface: Username and password do not match any user in this service |
      |               | secret_sauce | Epic sadface: Username is required                                        |
      | standard_user |              | Epic sadface: Password is required                                        |

  @smoke
  Scenario: User can log out
    When I log in as a "standard" user
    And I log out
    Then I should be back on the login page
