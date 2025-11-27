package stepDefinations;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.cucumber.datatable.DataTable;
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
import io.restassured.module.jsv.JsonSchemaValidator;

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

    @And("user creates a payload with {string},{string},{string},{string},{string},{string},{string}")
    public void userCreatesAPayloadWith(String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {
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

    }


    @And("user try to create booking but {string} is missed in payload {string},{string},{string},{string},{string},{string},{string}")
    public void userTryToCreateBookingButIsMissedInPayload(String field, String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {
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
        bookingBody.remove(field);
        context.response = context.request().body(bookingBody.toString())
                .when().post(context.currentSession.get("endpoint").toString());
        context.response.then().log().all();

    }

    @And("user try to create booking but {string} is missed for booking dates in payload {string},{string},{string},{string},{string},{string},{string}")
    public void userTryToCreateBookingButIsMissedForBookingDatesInPayload(String field, String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {
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

        context.response = context.request().body(bookingBody.toString())
                .when().post(context.currentSession.get("endpoint").toString());
        context.response.then().log().all();

    }

    @And("user validates the response with JSON schema {string}")
    public void userValidatesTheResponseWithJSONSchema(String schemaFile) {
        System.out.println("saurabh schema/"+schemaFile);
        context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("src/test/java/resources/schema/createBookingSchema.json"));
        LOG.info("Successfully Validated schema from "+schemaFile);
    }

    @When("user creates a booking")
    public void userCreatesABooking(DataTable dataTable) {
        Map<String,String> bookingData = dataTable.asMaps().get(0);
        JSONObject bookingBody = new JSONObject();
        bookingBody.put("firstname", bookingData.get("firstname"));
        bookingBody.put("lastname", bookingData.get("lastname"));
        bookingBody.put("totalprice", Integer.valueOf(bookingData.get("totalprice")));
        bookingBody.put("depositpaid", Boolean.valueOf(bookingData.get("depositpaid")));
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", (bookingData.get("checkin")));
        bookingDates.put("checkout", (bookingData.get("checkout")));
        bookingBody.put("bookingdates", bookingDates);
        bookingBody.put("additionalneeds", bookingData.get("additionalneeds"));

        context.response = context.request().body(bookingBody.toString())
                .when().post(context.currentSession.get("endpoint").toString());

        context.currentSession.put("bookingID", context.response.path("bookingid"));
        context.currentSession.put("firstname", context.response.path("booking.firstname"));
        context.currentSession.put("lastname", context.response.path("booking.lastname"));


    }
}
