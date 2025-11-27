#This feature will test the createBooking Api

Feature: User want to create new booking
  Scenario Outline: User is able to create the booking
    Given user has access to endpoint "/booking"
    When user creates a booking "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 200
    And booking id is created
    And validate the details in response "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    And user response time is less than 5000 milliseconds
    Examples:
      | firstname  | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10 | Breakfast       |


  Scenario Outline: User cannot create booking if mandatory field "<field>" in payload is missing
    Given user has access to endpoint "/booking"
    And user try to create booking but "<field>" is missed in payload "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 500
    Examples:
      |field      | firstname  | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      |firstname  | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10 | Breakfast       |
      |lastname   | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10 | Breakfast       |
      |totalprice | Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10  | Breakfast      |
      |depositpaid| Ricky      | Martin   |   1000     | true        | 2026-01-05 | 2026-01-10  | Breakfast      |

  Scenario Outline: User cannot create booking if booking dates "<field>" in payload is missing
    Given user has access to endpoint "/booking"
    And user try to create booking but "<field>" is missed for booking dates in payload "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 500
    Examples:
      |field| firstname | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      |checkin| Ricky      | Martin      |   1000 | true        | 2026-01-05 | 2026-01-10  | Breakfast       |
      |checkout| Ricky      | Martin      |   1000 | true        | 2026-01-05 | 2026-01-10  | Breakfast       |


  Scenario Outline: Schema validation
    Given user has access to endpoint "/booking"
    When user creates a booking "<firstname>","<lastname>","<totalPrice>","<depositPaid>","<checkin>","<checkout>","<additionalNeeds>"
    Then user should get the response code 200
    And user validates the response with JSON schema "createBookingSchema.json"
    Examples:
      | firstname | lastname | totalPrice | depositPaid | checkin    | checkout   | additionalNeeds |
      | Ricky      | Martin      |   1000 | true        | 2026-01-05 | 2026-01-10 | Breakfast       |
