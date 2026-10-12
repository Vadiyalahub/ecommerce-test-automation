@saucedemo @cart
Feature: Shopping cart
  As a customer
  I want to add and remove products in my cart
  So that I only buy what I want

  Background:
    Given I am logged in as a "standard" user

  @smoke
  Scenario: Add a product to the cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1
    When I open the cart
    Then the cart should contain "Sauce Labs Backpack"

  @regression
  Scenario: Add several products to the cart
    When I add the following products to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    Then the cart badge should show 3

  @regression
  Scenario: Remove a product from the cart
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    And I open the cart
    And I remove "Sauce Labs Backpack" from the cart
    Then the cart should contain only "Sauce Labs Bike Light"
    And the cart badge should show 1
