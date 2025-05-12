package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Config;

import java.time.Duration;
import java.util.List;

import static utility.Config.waitForObservation;

public class FlightSelectionPage {
    WebDriver driver;
    WebDriverWait wait;

    public FlightSelectionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private String selectedDepartureAirport;
    private String selectedArrivalAirport;
    private String selectedReturnDepartureAirport;
    private String selectedReturnArrivalAirport;



    public void waitForFlightList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement flightElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#outbound-journeys > li.item.journey")));
    }


    public void selectFirstAvailableFlight() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        List<WebElement> flights = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("li.item.journey.one-way.available")));

        waitForObservation();

        if (!flights.isEmpty()) {
            try {
                WebElement firstFlight = flights.get(0);

                WebElement origin = firstFlight.findElement(By.cssSelector(".flight-origin"));
                WebElement destination = firstFlight.findElement(By.cssSelector(".flight-arrival"));
                selectedDepartureAirport = origin.getAttribute("title").trim();
                selectedArrivalAirport = destination.getAttribute("title").trim();

                System.out.println("Gidiş Kalkış: " + selectedDepartureAirport);
                System.out.println("Gidiş Varış: " + selectedArrivalAirport);


                firstFlight.click();
                waitForObservation();
                System.out.println("İlk uçuş kartı başarıyla tıklandı.");
            } catch (Exception e) {
                throw new NoSuchElementException("İlk uçuş kartı tıklanamadı.");
            }
        } else {
            throw new NoSuchElementException("Uygun uçuş bulunamadı.");
        }



    }

    public void selectFirstReturnFlight() {
        WebElement returnJourneyBlock = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("return-journeys"))
        );

        List<WebElement> returnFlightCards = returnJourneyBlock.findElements(
                By.cssSelector("li.item.journey")
        );

        if (!returnFlightCards.isEmpty()) {
            WebElement firstCard = returnFlightCards.get(0);

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", firstCard);
            waitForObservation();

            try {
                WebElement origin = firstCard.findElement(By.cssSelector(".flight-origin"));
                WebElement destination = firstCard.findElement(By.cssSelector(".flight-arrival"));
                selectedReturnDepartureAirport = origin.getAttribute("title").trim();
                selectedReturnArrivalAirport = destination.getAttribute("title").trim();

                System.out.println("Dönüş Kalkış: " + selectedReturnDepartureAirport);
                System.out.println("Dönüş Varış: " + selectedReturnArrivalAirport);
                firstCard.click();

                wait.until(ExpectedConditions.attributeContains(firstCard, "class", "open"));

                waitForObservation();
                System.out.println("Dönüş için ilk uçuş başarıyla seçildi.");
                selectEcoClassIfAvailable();
            } catch (Exception e) {
                throw new NoSuchElementException("Dönüş uçuş kartı tıklanamadı.");
            }
        } else {
            throw new NoSuchElementException("Dönüş uçuşu kartı bulunamadı.");
        }
    }

    public String getSelectedDepartureAirport() {
        return selectedDepartureAirport;
    }
    public String getSelectedArrivalAirport() {
        return selectedArrivalAirport;
    }
    public String getSelectedReturnDepartureAirport() {
        return selectedReturnDepartureAirport;
    }
    public String getSelectedReturnArrivalAirport() {
        return selectedReturnArrivalAirport;
    }

    public void selectEcoClassIfAvailable() {

        try {
            WebElement openCard = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("li.item.journey.open")
            ));

            List<WebElement> classOptions = openCard.findElements(
                    By.cssSelector("ul.flys > li .fly-container")
            );

            if (!classOptions.isEmpty()) {
                WebElement firstClassOption = classOptions.get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", firstClassOption);
                wait.until(ExpectedConditions.elementToBeClickable(firstClassOption));
                firstClassOption.click();
                waitForObservation();

                System.out.println("Dönüş için ilk sınıf başarıyla seçildi.");
            } else {
                throw new NoSuchElementException("Dönüş için sınıf seçeneği bulunamadı.");
            }
        } catch (TimeoutException te) {
            if (driver.getCurrentUrl().contains("/odeme")) {
                System.out.println("Sayfa ödeme sayfasına yönlenmiş, sınıf seçimi zaten yapılmış olabilir.");
                return;
            }
            System.out.println("Uçuş kartı artık sayfada değil. Sayfa muhtemelen yönlendi.");
        } catch (Exception e) {
            throw new NoSuchElementException("Dönüş için sınıf seçimi yapılamadı: " + e.getMessage());
        }
    }

    }

