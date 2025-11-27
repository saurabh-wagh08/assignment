package utils;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class Utility {
    public Response response;
    public Map<String, Object> currentSession = new HashMap<String, Object>();
    private static final String CONTENT_TYPE = PropertyFile.getProperty("content.type");
    public RequestSpecification request() {
        RestAssured.reset();
        Options options = Options.builder().logStacktrace().build();
        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);
        RestAssured.baseURI = PropertyFile.getProperty("BaseUrl");
        System.out.println(PropertyFile.getProperty("BaseUrl"));
        return RestAssured.given()
                .config(config).relaxedHTTPSValidation()
                .filter(new RestAssuredRequestFilter())
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE);
    }


}
