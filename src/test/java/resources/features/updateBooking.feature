#This feature will test the updateBooking Api
@Booking @Update_Booking
Feature: User want to update his booking
  Background:
    Given user has access to endpoint "/booking"
    When user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin    | checkout   | additionalneeds |
      | Ricky       | Martin     | 1000         | true          | 2026-01-05 | 2026-01-10 | Breakfast       |
    Then user should get the response code 200

  Scenario Outline: User want to update the booking
    Given user has access to endpoint "/auth"
    And user creates a auth token with credential
    And user should get the response code 200
    And user has access to endpoint "/booking"
    When user update a booking "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 200
    And user response time is less than 5000 milliseconds
    And user has access to endpoint "/booking"
    And user makes a request to view booking Id
    And user should get the response code 200
    And validate the details in get booking by id response "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    And user response time is less than 5000 milliseconds
    Examples:
      | firstname | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      | Rocky     | Rao      |   1200     | true        | 2026-05-05 | 2026-05-10 | Snacks          |

  Scenario Outline: User cannot update booking if booking dates "<field>" in payload is missing
    Given user has access to endpoint "/auth"
    And user creates a auth token with credential
    And user should get the response code 200
    And user has access to endpoint "/booking"
    And user try to update booking but "<field>" is missed for booking dates in payload "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 400
    Examples:
      |field| firstname | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      |checkin| Raj      | Martin      |   1000 | true        | 2026-01-05 | 2026-01-10  | Breakfast       |
      |checkout| Raj      | Martin      |   1000 | true        | 2026-01-05 | 2026-01-10  | Breakfast       |

  Scenario Outline: User cannot update booking if mandatory field "<field>" in payload is missing
    Given user has access to endpoint "/auth"
    And user creates a auth token with credential
    And user should get the response code 200
    And user has access to endpoint "/booking"
    And user try to create booking but "<field>" is missed in payload "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 500
    Examples:
      |field      | firstname  | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      |firstname  | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10 | Breakfast       |
      |lastname   | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10 | Breakfast       |
      |totalprice | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10  | Breakfast      |
      |depositpaid| Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10  | Breakfast      |

  Scenario Outline: User cannot update booking authentication
    Given user has access to endpoint "/auth"
    And user creates a auth token with credential
    And user should get the response code 200
    And user has access to endpoint "/booking"
    And user try to create booking but "<field>" is missed in payload "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 500
    Examples:
      |field      | firstname  | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      |firstname  | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10 | Breakfast       |
      |lastname   | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10 | Breakfast       |
      |totalprice | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10  | Breakfast      |
      |depositpaid| Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10  | Breakfast      |

  Scenario Outline: Error is thrown if user want to update the booking without authentication
    Given user has access to endpoint "/booking"
    When user update a booking without authentication "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 403
      Examples:
      | firstname | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      | Rocky     | Rao      |   1200     | true        | 2026-05-05 | 2026-05-10 | Snacks          |
