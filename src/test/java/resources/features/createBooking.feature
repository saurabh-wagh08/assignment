#This feature will test the createBooking Api
@Smoke
Feature: User want to create new booking
  Scenario Outline: User is able to create the booking
    Given user has access to endpoint "/booking"
    When user creates a booking "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 200
    And booking id is created
    And validate the details in response "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    And user response time is less than 5000 milliseconds
    Examples:
      | firstname | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      | Ricky      | Martin      |   1000 | true        | 2021-05-05 | 2021-05-15 | Breakfast       |
