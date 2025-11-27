package stepDefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import utils.PropertyFile;
import utils.Utility;

public class PartialUpdateBookingStepDef {
    @Autowired
    private Utility context;
    private static final Logger LOG = LogManager.getLogger(ViewBookingStepDef.class);

    public PartialUpdateBookingStepDef(Utility context) {
        this.context = context;
    }

    @When("user wants to change a booking {string},{string}")
    public void userWantsToChangeABooking(String firstName, String lastName) {
        JSONObject bookingBody = new JSONObject();
        bookingBody.put("firstname", firstName);
        bookingBody.put("lastname",lastName);
        context.response = context.request()
                .header("Cookie", context.currentSession.get("token").toString())
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .body(bookingBody.toString())
                .when().put(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();

    }

    @When("user wants to change a booking without authentication {string},{string}")
    public void userWantsToChangeABookingWithoutAuthentication(String firstName, String lastName) {
        JSONObject bookingBody = new JSONObject();
        bookingBody.put("firstname", firstName);
        bookingBody.put("lastname",lastName);
        context.response = context.request()
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .body(bookingBody.toString())
                .when().put(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();

    }
}
