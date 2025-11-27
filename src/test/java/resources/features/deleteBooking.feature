#This feature will test the delete booking Api
@Booking @Delete_Booking
Feature: User want to delete his booking
  Background:
    Given user has access to endpoint "/booking"
    When user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin    | checkout   | additionalneeds |
      | Ricky       | Martin     | 1000         | true          | 2026-01-05 | 2026-01-10 | Breakfast       |
    Then user should get the response code 200

  Scenario: User want to delete the booking recently created
    Given user has access to endpoint "/auth"
    And user creates a auth token with credential
    And user should get the response code 200
    And user has access to endpoint "/booking"
    When user delete the booking
    Then user should get the response code 201
    And user makes a request to view booking Id
    And user should get the response code 404

  Scenario: Error is thrown when user try to delete the booking without Authentication
    Given user has access to endpoint "/booking"
    When user try to delete the booking without authentication
    Then user should get the response code 403

