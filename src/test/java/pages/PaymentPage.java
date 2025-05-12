package pages;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    WebDriver driver;
    WebDriverWait wait;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    private By departureAirport = By.cssSelector(".flight-info-container .origin");
    private By departureArrivalAirport = By.cssSelector(".flight-info-container .destination");

    private By returnDepartureAirport = By.cssSelector(".flight-info-container .destination");
    private By returnArrivalAirport = By.cssSelector(".flight-info-container .origin");


    public void verifySelectedFlights(String expectedDepartureOrigin, String expectedDepartureDestination,
                                      String expectedReturnOrigin, String expectedReturnDestination) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(departureAirport));
        wait.until(ExpectedConditions.visibilityOfElementLocated(departureArrivalAirport));
        wait.until(ExpectedConditions.visibilityOfElementLocated(returnDepartureAirport));
        wait.until(ExpectedConditions.visibilityOfElementLocated(returnArrivalAirport));

        String actualDepartureOrigin = driver.findElement(departureAirport).getText().trim();
        String actualDepartureDestination = driver.findElement(departureArrivalAirport).getText().trim();
        String actualReturnOrigin = driver.findElement(returnDepartureAirport).getText().trim();
        String actualReturnDestination = driver.findElement(returnArrivalAirport).getText().trim();

        expectedDepartureOrigin = expectedDepartureOrigin.replace("Havalimanı", "").trim().toLowerCase();
        expectedDepartureDestination = expectedDepartureDestination.replace("Havalimanı", "").trim().toLowerCase();
        expectedReturnOrigin = expectedReturnOrigin.replace("Havalimanı", "").trim().toLowerCase();
        expectedReturnDestination = expectedReturnDestination.replace("Havalimanı", "").trim().toLowerCase();

        actualDepartureOrigin = actualDepartureOrigin.replace("Havalimanı", "").trim().toLowerCase();
        actualDepartureDestination = actualDepartureDestination.replace("Havalimanı", "").trim().toLowerCase();
        actualReturnOrigin = actualReturnOrigin.replace("Havalimanı", "").trim().toLowerCase();
        actualReturnDestination = actualReturnDestination.replace("Havalimanı", "").trim().toLowerCase();

        System.out.println("Gidiş Kalkış: Beklenen=" + expectedDepartureOrigin + ", Gerçek=" + actualDepartureOrigin);
        System.out.println("Gidiş Varış: Beklenen=" + expectedDepartureDestination + ", Gerçek=" + actualDepartureDestination);
        System.out.println("Dönüş Kalkış: Beklenen=" + expectedReturnOrigin + ", Gerçek=" + actualReturnOrigin);
        System.out.println("Dönüş Varış: Beklenen=" + expectedReturnDestination + ", Gerçek=" + actualReturnDestination);

        Assert.assertTrue("Gidiş kalkış havalimanı eşleşmiyor!", actualDepartureOrigin.contains(expectedDepartureOrigin));
        Assert.assertTrue("Gidiş varış havalimanı eşleşmiyor!", actualDepartureDestination.contains(expectedDepartureDestination));
        Assert.assertTrue("Dönüş kalkış havalimanı eşleşmiyor!", actualReturnOrigin.contains(expectedReturnOrigin));
        Assert.assertTrue("Dönüş varış havalimanı eşleşmiyor!", actualReturnDestination.contains(expectedReturnDestination));
    }


}