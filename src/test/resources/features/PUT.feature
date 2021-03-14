Feature: PUT feature

  Scenario: PUT Test
    Given the user sends PUT request
    Then the response code should be 200
    And the response should have updated info