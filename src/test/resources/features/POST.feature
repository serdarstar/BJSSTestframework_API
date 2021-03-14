@wip
Feature: POST Feature

  Scenario: POST Test
    When the user send a POST request
    Then the status code should be 201
    And the user info should be correct