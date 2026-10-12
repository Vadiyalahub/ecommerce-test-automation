@saucedemo @products
Feature: Product catalogue
  As a customer
  I want to browse and sort products
  So that I can find what I need

  Background:
    Given I am logged in as a "standard" user

  @smoke
  Scenario: All products are shown
    Then I should see 6 products

  @regression
  Scenario Outline: Sort products by <sort option>
    When I sort products by "<sort option>"
    Then the products should be sorted by <field> in <order> order

    Examples:
      | sort option         | field | order      |
      | Name (A to Z)       | name  | ascending  |
      | Name (Z to A)       | name  | descending |
      | Price (low to high) | price | ascending  |
      | Price (high to low) | price | descending |
