#This feature will test the updateBooking Api
@Booking @Partial_Update_Booking
Feature: User want to update his booking
  Background:
    Given user has access to endpoint "/booking"
    When user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin    | checkout   | additionalneeds |
      | Ricky       | Martin     | 1000         | true          | 2026-01-05 | 2026-01-10 | Breakfast       |
    Then user should get the response code 200

  Scenario Outline: User want to make partial update in the booking
    Given user has access to endpoint "/auth"
    And user creates a auth token with credential
    And user should get the response code 200
    And user has access to endpoint "/booking"
    When user wants to change a booking "<firstname>","<lastname>"
    Then user should get the response code 200
    Given user has access to endpoint "/booking"
    When user makes a request to view booking Id
    Then user should get the response code 200
    And validate the details in get booking by id response "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    And user response time is less than 5000 milliseconds
    Examples:
      | firstname | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      | Rocky     | Rao      |   1200     | true        | 2026-05-05 | 2026-05-10 | Snacks          |

  Scenario Outline: Error is thrown when user try to update the booking without Authentication
    Given user has access to endpoint "/booking"
    When user wants to change a booking without authentication "<firstname>","<lastname>"
    Then user should get the response code 403
      Examples:
      | firstname | lastname |
      | Rocky     | Rao      |
