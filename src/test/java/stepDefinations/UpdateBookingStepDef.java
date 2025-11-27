package stepDefinations;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import utils.PropertyFile;
import utils.Utility;

import java.util.Map;

public class UpdateBookingStepDef {
    @Autowired
    private Utility context;
    private static final Logger LOG = LogManager.getLogger(ViewBookingStepDef.class);

    public UpdateBookingStepDef(Utility context) {
        this.context = context;
    }

    @When("user update a booking {string},{string},{string},{string},{string},{string},{string}")
    public void userUpdateABooking(String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {

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

        context.response = context.request()
                .header("Cookie", context.currentSession.get("token").toString())
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .body(bookingBody.toString())
                .when().put(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();



    }

    @When("user update a booking without authentication {string},{string},{string},{string},{string},{string},{string}")
    public void userUpdateABookingWithoutAuthentication(String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {

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

        context.response = context.request()
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .body(bookingBody.toString())
                .when().put(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();



    }


    @When("user creates a auth token with credential")
    public void userCreatesAAuthTokenWithCredential() {
        JSONObject credentials = new JSONObject();
        credentials.put("username", PropertyFile.getProperty("username"));
        credentials.put("password", PropertyFile.getProperty("password"));
        context.response = context.request().body(credentials.toString())
                .when().post(context.currentSession.get("endpoint").toString());
        String token = context.response.path("token");
        LOG.info("Auth Token: "+token);
        context.currentSession.put("token", "token="+token);
    }
    @And("user try to update booking but {string} is missed in payload {string},{string},{string},{string},{string},{string},{string}")
    public void userTryToUpdateBookingButIsMissedInPayload(String field, String firstname, String lastname, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds) {
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
        context.response = context.request()
                .header("Cookie", context.currentSession.get("token").toString())
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .body(bookingBody.toString())
                .when().put(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();

    }

}
