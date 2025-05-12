package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static utility.Config.waitForObservation;
import static utility.Config.BASE_URL;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By loginTab = By.cssSelector("a.login");
    private final By registerPopupButton = By.xpath("//button[contains(text(), 'Üye Ol')]");
    private final By cookieAcceptButton = By.id("accept");
    private final By flightTab = By.xpath("//a[contains(., 'Uçak')]");


    public void handlePopups() {
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.presenceOfElementLocated(cookieAcceptButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cookieButton);
        } catch (TimeoutException e) {
            System.out.println("Çerez bildirimi görünmedi.");
        }
    }

    public void openObilet() {

        driver.get(utility.Config.BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginTab));
        handlePopups();
        waitForObservation();
    }

    public void clickRegisterTab() {
        waitForObservation();
        wait.until(ExpectedConditions.elementToBeClickable(loginTab)).click();
    }

    public void clickRegisterButton() {
        waitForObservation();
        wait.until(ExpectedConditions.elementToBeClickable(registerPopupButton)).click();
    }


    public void clickFlightTab() {
        waitForObservation();
        WebElement flightTabElement = wait.until(ExpectedConditions.elementToBeClickable(flightTab));
        flightTabElement.click();
    }

}
