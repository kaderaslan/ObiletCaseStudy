package StepDefinition;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.FlightSearchPage;
import pages.FlightSelectionPage;
import pages.HomePage;
import pages.PaymentPage;
import utility.Driver;

public class FlightSearchSteps {

    WebDriver driver = Driver.getDriver();
    HomePage homePage = new HomePage(driver);
    FlightSearchPage flightSearchPage = new FlightSearchPage(driver);
    FlightSelectionPage flightSelectionPage = new FlightSelectionPage(driver);
    PaymentPage paymentPage = new PaymentPage(driver);

    private String selectedOrigin;
    private String selectedDestination;

    @Given("Kullanıcı Obilet ana sayfasına gider")
    public void kullanici_obilet_ana_sayfasina_gider() {
        homePage.openObilet();
    }

    @When("Kullanıcı \"Uçak\" sekmesine tıklar")
    public void kullanici_ucak_sekmesine_tiklar() {
        homePage.clickFlightTab();
    }


    @And("Kullanıcı kalkış yeri {string} seçer")
    public void kullanici_kalkis_yeri_secer(String kalkisSehri) {
        selectedOrigin = kalkisSehri;
        flightSearchPage.selectCity("origin-input", kalkisSehri);
    }

    @And("Kullanıcı varış yeri {string} seçer")
    public void kullanici_varis_yeri_secer(String varisSehri) {
        selectedDestination = varisSehri;
        flightSearchPage.selectCity("destination-input", varisSehri);
    }

    @And("Kullanıcı gidiş tarihini {int}, dönüş tarihini {int} gün sonraya ayarlar")
    public void kullanici_gidis_ve_donus_tarihlerini_ayarlar(int gidisGunu, int donusGunu) {
        flightSearchPage.selectDates(gidisGunu, donusGunu);
    }

    @And("Kullanıcı \"Uçuş Ara\" butonuna tıklar")
    public void kullanici_ucus_ara_butonuna_tiklar() {
        flightSearchPage.clickSearchButton();
    }

    @Then("Uçuşlar listelenir")
    public void ucuslar_listelenir() {
        flightSelectionPage.waitForFlightList();
    }

    @When("Kullanıcı gidiş uçuşunu seçer")
    public void kullanici_gidis_ucusunu_secer() {
        flightSelectionPage.selectFirstAvailableFlight();
    }

    @And("Gidiş için birden fazla sınıf varsa ilkini seçer")
    public void gidis_icin_eco_sinifi_secilir() {
        flightSelectionPage.selectEcoClassIfAvailable();
    }

    @When("Kullanıcı dönüş uçuşunu seçer")
    public void kullanici_donus_ucusunu_secer() {
        flightSelectionPage.selectFirstReturnFlight();
    }


    @And("Dönüş için birden fazla sınıf varsa ilkini seçer")
    public void donus_icin_eco_sinifi_secilir() {
        flightSelectionPage.selectEcoClassIfAvailable();
    }

    @Then("Ödeme sayfası açılır ve seçilen uçuşlar doğrulanır")
    public void odeme_sayfasi_acilir_ve_ucuslar_dogrulanir() {
        String dep = flightSelectionPage.getSelectedDepartureAirport();
        String arr = flightSelectionPage.getSelectedArrivalAirport();
        String retDep = flightSelectionPage.getSelectedReturnDepartureAirport();
        String retArr = flightSelectionPage.getSelectedReturnArrivalAirport();

        paymentPage.verifySelectedFlights(dep, arr, retDep, retArr);
    }
}