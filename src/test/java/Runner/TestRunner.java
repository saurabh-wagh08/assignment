package Runner;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import io.cucumber.junit.CucumberOptions.SnippetType;
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/resources/features"},
        tags = "@Smoke",
        glue = {"stepDefinations"},
        plugin = {"json:target/jsonReports/cucumber-report.json",
                "html:target/cucumber-report/cucumber.html"})
public class TestRunner {

}
