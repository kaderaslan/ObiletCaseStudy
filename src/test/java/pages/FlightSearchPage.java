package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Config;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;


import static utility.Config.waitForObservation;

public class FlightSearchPage {
    WebDriver driver;
    WebDriverWait wait;

    public FlightSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private By flightTab = By.xpath("//a[.//svg[@class='icon'] and contains(., 'Uçak')]");

    private By fromInput = By.id("origin-input");
    private By toInput = By.id("destination-input");


    private By datePickerInput = By.cssSelector("#departure .display");
    private By returnDateInput = By.id("return-input-placeholder");


    private By searchButton = By.id("search-button");



    public void selectCity(String inputId, String cityText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id(inputId)));
        js.executeScript("arguments[0].click();", input);
        waitForObservation();
        input.clear();
        input.sendKeys(cityText);
        waitForObservation();

        WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                input.findElement(By.xpath("..")),
                By.cssSelector(".results ul li.item")
        )).get(0).findElement(By.xpath("./ancestor::div[contains(@class, 'results')]"));

        WebElement firstItem = resultsContainer.findElement(By.cssSelector("ul li.item"));

        firstItem.click();
        waitForObservation();
    }



    public void selectDates(int departureDaysFromToday, int returnDaysFromToday) {
        selectDateFromCalendar(departureDaysFromToday, true); // Gidiş tarihi
        selectDateFromCalendar(returnDaysFromToday, false);   // Dönüş tarihi
    }

    private void selectDateFromCalendar(int daysFromToday, boolean isDeparture) {
        LocalDate targetDate = LocalDate.now().plusDays(daysFromToday);
        String day = String.valueOf(targetDate.getDayOfMonth());

        if (isDeparture) {
            wait.until(ExpectedConditions.elementToBeClickable(datePickerInput)).click(); // Gidiş takvimi açılır
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(returnDateInput)).click(); // Dönüş takvimi açılır
        }

        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@type='button'][@data-date][span[text()='" + day + "']]")));

        dayElement.click();
        waitForObservation();
    }
    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        waitForObservation();
    }

}
