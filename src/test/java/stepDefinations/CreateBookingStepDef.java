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
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class CreateBookingStepDef {
    @Autowired
    private Utility context;
    private static final Logger LOG = LogManager.getLogger(ViewBookingStepDef.class);

    public CreateBookingStepDef(Utility context) {
        this.context = context;
    }

    @When("user creates a booking {string},{string},{string},{string},{string},{string},{string}")
    public void userCreatesABooking(String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {

        JSONObject bookingBody = new JSONObject();
        bookingBody.put("firstname", firstname);
        bookingBody.put("lastname",lastname);
        bookingBody.put("totalprice", Integer.valueOf(totalPrice));
        bookingBody.put("depositpaid", Boolean.valueOf(depositPaid));
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);
        bookingBody.put("bookingdates", bookingDates);
        bookingBody.put("additionalneeds", additionalNeeds);

        context.response = context.request().body(bookingBody.toString())
                .when().post(context.currentSession.get("endpoint").toString());
        context.response.then().log().all();



    }


    @And("booking id is created")
    public void bookingIdIsCreated() {
        Assert.assertNotNull(context.response.path("bookingid"));
        context.currentSession.put("bookingid",context.response.path("bookingid"));
    }

    @And("validate the details in response {string},{string},{string},{string},{string},{string},{string}")
    public void userCanSeeTheDetails(String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {
        Assert.assertEquals(context.response.path("booking.firstname"),firstname);
        Assert.assertEquals(context.response.path("booking.lastname"),lastname);
        Assert.assertEquals(context.response.path("booking.totalprice").toString(),totalPrice);
        Assert.assertEquals(context.response.path("booking.depositpaid").toString(),depositPaid);
        Assert.assertEquals(context.response.path("booking.bookingdates.checkin"),checkin);
        Assert.assertEquals(context.response.path("booking.bookingdates.checkout"),checkout);
        Assert.assertEquals(context.response.path("booking.additionalneeds"),additionalNeeds);

    }

}
