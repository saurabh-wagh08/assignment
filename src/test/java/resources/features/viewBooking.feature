#This feature will test the GetBookingIds Api
@Booking @View_Booking
Feature: User want to view all the booking ids
  Scenario: To view all the booking IDs
    Given user has access to endpoint "/booking"
    When user makes a request to view booking IDs
    Then user should get the response code 200
    And user should see all the booking IDs
    And user response time is less than 5000 milliseconds

  Scenario Outline: To view all the booking IDs by booking dates
    Given user has access to endpoint "/booking"
    When user makes a request to view booking IDs from "<checkin>" to "<checkout>"
    Then user should get the response code 200
    And user should see all the booking IDs

    Examples:
      | checkin    | checkout   |
      | 2020-01-01 | 2024-12-31 |




  Scenario: To view all the booking IDs filter by name
    Given user has access to endpoint "/booking"
    And user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin    | checkout   | additionalneeds |
      | Roy       | Martin     | 1000         | true          | 2026-01-05 | 2026-01-10 | Breakfast       |
    And user should get the response code 200
    When user makes a request to view booking IDs filter by firstName and lastName
    Then user should get the response code 200
    And user should see the booking id