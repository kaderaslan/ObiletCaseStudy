package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features/FlightSearch.feature",
        glue = {"StepDefinition", "utility"},
        plugin = {"pretty", "html:target/flightsearch-report.html"},
        monochrome = true
)
public class FlightSearchRunner {
}
