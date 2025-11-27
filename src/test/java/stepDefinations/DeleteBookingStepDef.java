package stepDefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import utils.PropertyFile;
import utils.Utility;

public class DeleteBookingStepDef {
    @Autowired
    private Utility context;
    private static final Logger LOG = LogManager.getLogger(ViewBookingStepDef.class);

    public DeleteBookingStepDef(Utility context) {
        this.context = context;
    }

    @When("user delete the booking")
    public void userDeleteTheBooking() {
        context.response = context.request()
                .header("Cookie", context.currentSession.get("token").toString())
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .when().delete(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();
    }

    @When("user try to delete the booking without authentication")
    public void userTryToDeleteTheBookingWithoutAuthentication() {
        context.response = context.request()
                .pathParam("bookingID", context.currentSession.get("bookingID"))
                .when().delete(context.currentSession.get("endpoint")+"/{bookingID}");
        context.response.then().log().all();
    }
}
