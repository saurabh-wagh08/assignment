#This feature will test the GetBookingIds Api


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


    # data is deleted from record
  Scenario Outline: To view all the booking IDs filter by name
    Given user has access to endpoint "/booking"
    When user makes a request to view booking IDs filter by "<firstName>" to "<lastName>"
    Then user should get the response code 200
    And user should see the booking id "<bookingId>"

    Examples:
      | firstName    | lastName   | bookingId|
      | Raj        | Kapoor      |     3751      |

