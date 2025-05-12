package StepDefinition;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.RegistrationPage;
import utility.Driver;
import org.junit.Assert;

import java.time.Duration;
import java.util.UUID;

public class RegistrationSteps {
    WebDriver driver = Driver.getDriver();
    HomePage homePage = new HomePage(driver);
    RegistrationPage registrationPage = new RegistrationPage(driver);

    @Given("Kullanıcı Obilet giriş sayfasına gider")
    public void kullanici_obilet_giris_sayfasina_gider() {
        homePage.openObilet();
    }

    @And("Kullanıcı \"Üye Girişi\" sekmesine tıklar")
    public void kullanici_uye_girisi_butonuna_tiklar() {
        homePage.clickRegisterTab();
    }

    @And("Kullanıcı \"Üye Ol\" butonuna tıklar")
    public void kullanici_uye_ol_butonuna_tiklar() {
        homePage.clickRegisterButton();
    }

    @When("Kullanıcı e-posta {string} ve şifre {string} girer")
    public void kullanici_eposta_ve_sifre_girer(String email, String password) {
        if (email.equalsIgnoreCase("random")) {
            email = "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        }

        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.acceptTerms();
    }


    @Then("Kayıt başarılı olur ve kullanıcı yönlendirilir")
    public void kayit_basarili_olur_ve_kullanici_yonlendirilir() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement userMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("li.user-profile a.user-name span")));

        Assert.assertTrue("Kullanıcı menüsü görünmüyor", userMenu.isDisplayed());
        Assert.assertEquals("Hesabım", userMenu.getText().trim());
    }

    @Then("{string} hata mesajı görüntülenir")
    public void hata_mesaji_goruntulenir(String expectedMessage) {
        String actualMessage;

        if (expectedMessage.contains("E-mail")) {
            actualMessage = registrationPage.getEmailErrorMessage();
        } else if (expectedMessage.contains("Şifre")) {
            actualMessage = registrationPage.getPasswordErrorMessage();
        } else {
            throw new IllegalArgumentException("Bilinmeyen hata mesajı: " + expectedMessage);
        }

        Assert.assertNotNull("Hata mesajı bulunamadı.", actualMessage);
        Assert.assertEquals(expectedMessage, actualMessage);
    }

}
