@regression
Feature: Amazon departments

  @amazonTestDemo @smoketest
  Scenario: As a User, I am able to select different departments and search
    Given I am on the amazon homepage
    And The departments dropdown is "All Departments"
    When I select the department "Home & Kitchen"
    And I search "Coffee mug"
    Then I should be on "Coffee mug" search result page
    And The departments dropdown is "Home & Kitchen"