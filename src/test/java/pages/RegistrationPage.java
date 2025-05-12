package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static utility.Config.waitForObservation;



public class RegistrationPage {
    WebDriver driver;
    WebDriverWait wait;


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    private By emailField = By.cssSelector("input[type='email'][name='email']");
    private By passwordField = By.xpath("//input[@placeholder='Şifre']");
    private By register = By.xpath("//button[contains(@class, 'register') and contains(@class, 'register-button')]");
    private By termsCheckbox = By.cssSelector("#contract-checkbox");
    private By emailErrorMessage = By.id("email-error");
    private By passwordErrorMessage = By.id("password-error");



    public void enterEmail(String email) {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailField));
        try {
            WebElement emailElement = wait.until(ExpectedConditions.elementToBeClickable(emailField));
            emailElement.clear();
            emailElement.sendKeys(email);
        } catch (StaleElementReferenceException e) {
            WebElement emailElement = driver.findElement(emailField);
            emailElement.clear();
            emailElement.sendKeys(email);
        } catch (Exception e) {
            System.out.println("Normal sendKeys başarısız oldu, JS ile deniyoruz...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement emailElement = driver.findElement(emailField);
            js.executeScript("arguments[0].value = arguments[1];", emailElement, email);
        }
    }


    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        waitForObservation();
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public void acceptTerms() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(termsCheckbox));
        waitForObservation();

        String checkboxClass = checkbox.getAttribute("class");
        if (checkboxClass == null || !checkboxClass.contains("checked")) {
            checkbox.click();
        }
    }

    public String getEmailErrorMessage() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailErrorMessage));
            return errorElement.getText().trim();
        } catch (TimeoutException e) {
            System.out.println("Hata mesajı bulunamadı.");
            return null;
        }
    }

    public String getPasswordErrorMessage() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorMessage));
            return errorElement.getText().trim();
        } catch (TimeoutException e) {
            return null;
        }
    }

}