package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features/NewUserRegistration.feature",
        glue = {"StepDefinition", "utility"},
        plugin = {"pretty", "html:target/registration-report.html"},
        monochrome = true
)
public class RegistrationRunner {
}
