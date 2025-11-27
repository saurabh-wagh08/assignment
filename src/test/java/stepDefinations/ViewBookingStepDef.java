package stepDefinations;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import io.cucumber.java.en.*;
import org.json.JSONObject;
import org.junit.Assert;
import utils.PropertyFile;
import utils.RestAssuredRequestFilter;
import utils.Utility;
//import utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class ViewBookingStepDef {
@Autowired
    private Utility context;
    private static final Logger LOG = LogManager.getLogger(ViewBookingStepDef.class);

    public ViewBookingStepDef(Utility context) {
        this.context = context;
    }

    @Given("user has access to endpoint {string}")
    public void userHasAccessToEndpoint(String endpoint) {
        context.currentSession.put("endpoint", endpoint);
    }

    @When("user makes a request to view booking IDs")
    public void userMakesARequestToViewBookingIDs() {
        context.response = context.request().when().get(context.currentSession.get("endpoint").toString());
        context.response.then().log().all();
        int bookingID = context.response.getBody().jsonPath().getInt("[0].bookingid");
        System.out.println("Time Taken"+context.response.getTimeIn(TimeUnit.MILLISECONDS));
        LOG.info("Booking ID: "+bookingID);
        System.out.println("saurabh" +bookingID);
        assertNotNull("Booking ID not found!", bookingID);
        context.currentSession.put("bookingID", bookingID);
    }

    @Then("user should get the response code {int}")
    public void userShouldGetTheResponseCode(int statusCode) {
        Assert.assertEquals(statusCode,context.response.statusCode());
    }

    @And("user should see all the booking IDs")
    public void userShouldSeeAllTheBookingIDs() {
        Assert.assertNotNull(context.response.path("bookingid"));
    }

    @And("user response time is less than {int} milliseconds")
    public void userResponseTimeIsLessThanMilliseconds(int timeInMilliSecond) {
        LOG.info("Response time: "+context.response.getTimeIn(TimeUnit.MILLISECONDS));
        Assert.assertTrue(context.response.getTimeIn(TimeUnit.MILLISECONDS)<=timeInMilliSecond);
    }

    @When("user makes a request to view booking IDs from {string} to {string}")
    public void userMakesARequestToViewBookingIDsFromTo(String checkin, String checkout) {
        context.response = context.request()
                .queryParams("checkin",checkin, "checkout", checkout)
                .when().get(context.currentSession.get("endpoint").toString());

    }

    @When("user makes a request to view booking IDs filter by {string} to {string}")
    public void userMakesARequestToViewBookingIDsFilterByTo(String firstName, String lastName) {
        context.response = context.request()
                .queryParams("firstname",firstName, "lastname", lastName)
                .when().get(context.currentSession.get("endpoint").toString());

    }

    @And("user should see the booking id {string}")
    public void userShouldSeeTheBookingId(String bookingId) {

        Assert.assertTrue(context.response.getBody().jsonPath().getString("bookingid").contains(bookingId));
    }


    @When("user creates a auth token with credential {string} & {string}")
    public void userCreatesAAuthTokenWithCredential(String arg0, String arg1) {
    }

    @When("user makes a request to view booking Id")
    public void userMakesARequestToViewBookingId() {
        context.response = context.request()
                .header("Cookie", context.currentSession.get("token").toString())
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .when().get(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();
    }

    @And("validate the details in get booking by id response {string},{string},{string},{string},{string},{string},{string}")
    public void validateTheDetailsInGetBookingById(String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {
        Assert.assertEquals(context.response.path("firstname"),firstname);
        Assert.assertEquals(context.response.path("lastname"),lastname);
        Assert.assertEquals(context.response.path("totalprice").toString(),totalPrice);
        Assert.assertEquals(context.response.path("depositpaid").toString(),depositPaid);
        Assert.assertEquals(context.response.path("bookingdates.checkin"),checkin);
        Assert.assertEquals(context.response.path("bookingdates.checkout"),checkout);
        Assert.assertEquals(context.response.path("additionalneeds"),additionalNeeds);

    }

    @And("user try to update booking but {string} is missed for booking dates in payload {string},{string},{string},{string},{string},{string},{string}")
    public void userTryToUpdateBookingButIsMissedForBookingDatesInPayload(String field, String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {
        JSONObject bookingBody = new JSONObject();
        bookingBody.put("firstname", firstname);
        bookingBody.put("lastname",lastname);
        bookingBody.put("totalprice", Integer.valueOf(totalPrice));
        bookingBody.put("depositpaid", Boolean.valueOf(depositPaid));
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);
        bookingDates.remove(field);
        bookingBody.put("bookingdates", bookingDates);
        bookingBody.put("additionalneeds", additionalNeeds);

        context.response = context.request()
                .header("Cookie", context.currentSession.get("token").toString())
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .body(bookingBody.toString())
                .when().put(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();

    }

    @And("user should see the booking id")
    public void userShouldSeeTheBookingId() {
        Assert.assertNotNull(context.response.path("bookingid"));
    }

    @When("user makes a request to view booking IDs filter by firstName and lastName")
    public void userMakesARequestToViewBookingIDsFilterByFirstNameAndLastName() {
        LOG.info("Session firstname: "+context.currentSession.get("firstname"));
        LOG.info("Session lastname: "+context.currentSession.get("lastname"));
        context.response = context.request()
                .queryParams("firstname", context.currentSession.get("firstname"), "lastname", context.currentSession.get("lastname"))
                .when().get(context.currentSession.get("endpoint").toString());

    }


}
