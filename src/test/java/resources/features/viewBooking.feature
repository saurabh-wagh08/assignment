#This feature will test the GetBookingIds Api

@Smoke
Feature: User want to view all the booking ids
  Scenario: To view all the booking IDs
    Given user has access to endpoint "/booking"
    When user makes a request to view booking IDs
    Then user should get the response code 200
    And user should see all the booking IDs
    And user response time is less than 5000 milliseconds