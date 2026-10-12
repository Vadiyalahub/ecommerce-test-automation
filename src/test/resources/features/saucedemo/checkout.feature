@saucedemo @checkout
Feature: Checkout
  As a customer
  I want to pay for the products in my cart
  So that I receive my order

  Background:
    Given I am logged in as a "standard" user
    And I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    And I open the cart
    And I start checkout

  @smoke @e2e
  Scenario: Complete a purchase from start to finish
    When I enter checkout details "Jane", "Doe", "85001"
    And I continue to the order overview
    Then the item total should equal the sum of the item prices
    And the order total should equal item total plus tax
    When I finish the order
    Then I should see the confirmation message "Thank you for your order!"

  @regression
  Scenario Outline: Checkout requires all customer details
    When I enter checkout details "<first>", "<last>", "<zip>"
    And I continue to the order overview
    Then I should see the checkout error "<error>"

    Examples:
      | first | last | zip   | error                          |
      |       | Doe  | 85001 | Error: First Name is required  |
      | Jane  |      | 85001 | Error: Last Name is required   |
      | Jane  | Doe  |       | Error: Postal Code is required |
