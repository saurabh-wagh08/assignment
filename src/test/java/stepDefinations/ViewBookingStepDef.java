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
import org.junit.Assert;
import utils.PropertyFile;
import utils.RestAssuredRequestFilter;
import utils.Utility;
//import utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
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
        int bookingID = context.response.getBody().jsonPath().getInt("[0].bookingid");
        System.out.println("Time Taken"+context.response.getTimeIn(TimeUnit.MILLISECONDS));
        LOG.info("Booking ID: "+bookingID);
        System.out.println("saurabh" +bookingID);
        assertNotNull("Booking ID not found!", bookingID);
        context.currentSession.put("bookingID", bookingID);
    }

    @Then("user should get the response code {int}")
    public void userShouldGetTheResponseCode(int statusCode) {
        Assert.assertEquals(context.response.statusCode(),statusCode);
    }

    @And("user should see all the booking IDs")
    public void userShouldSeeAllTheBookingIDs() {
   //     for(int i=0; i<context.response.getBody().jsonPath().getInt("[0].bookingid"))
//        BookingID[] bookingIDs = ResponseHandler.deserializedResponse(context.response, BookingID[].class);
//        assertNotNull("Booking ID not found!!", bookingIDs);

    }

    @And("user response time is less than {int} milliseconds")
    public void userResponseTimeIsLessThanMilliseconds(int timeInMilliSecond) {
        LOG.info("Response time: "+context.response.getTimeIn(TimeUnit.MILLISECONDS));
        Assert.assertTrue(context.response.getTimeIn(TimeUnit.MILLISECONDS)<=timeInMilliSecond);
    }
}
