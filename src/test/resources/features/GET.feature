@wip
Feature: GET Users

  Scenario: Simple verify
    When I get the current user information from api
    Then status code should be 200
    Then the response should have a first name "George"
    Then the response headers should have "Date" header

  Scenario: Verify by using jsonPath
    When I get user information from api for user 2
    Then the user info should match the following values


