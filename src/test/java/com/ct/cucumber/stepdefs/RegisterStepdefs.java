package com.ct.cucumber.stepdefs;

import com.ct.cucumber.page.RegistrationPage;
import com.ct.cucumber.runner.CucumberBaseTest;
import com.ct.framework.config.TestConfig;
import com.ct.framework.driver.WebDriverRunner;
import com.ct.framework.pages.LoginPage;
import com.ct.model.Customer;
import com.github.javafaker.Faker;
import com.google.gson.internal.bind.JsonTreeWriter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class RegisterStepdefs extends CucumberBaseTest {
    private Faker faker = new Faker();
    private LoginPage loginPage = new LoginPage(WebDriverRunner.getWebDriver());
    private RegistrationPage registrationPage = new RegistrationPage(WebDriverRunner.getWebDriver());
    private Customer customer = Customer.newBuilder().withEmail(faker.name().username() + "@gmail.com").withPassword(faker.code().ean8()).build();


    @Given("Customer goes to Registration Page")
    public void customerGoesToRegistrationPage() {
        registrationPage.openPage();
    }

    @When("Customer fills in random email")
    public void customerFillsInEmail() {
        registrationPage.enterEmail(customer.getEmail());
    }

    @And("Customer fills in random password")
    public void customerFillsInPassword() {
        registrationPage.enterPassword(customer.getPassword());
    }

    @And("Customer repeats random password")
    public void customerRepeatsPassword() {
        registrationPage.repeatPassword(customer.getPassword());
    }

    @And("Customer clicks on Security Question drop-down")
    public void customerClicksOnSecurityQuestionDropDown() {
        registrationPage.clickOnSelectQuestionDropDown();
    }

    @And("Customer selects any security question")
    public void customerSelectsSecurityQuestion() {
        registrationPage.chooseSecurityQuestion();
    }

    @And("Customer enters any Answer")
    public void customerEntersAnswer() {
        registrationPage.enterAnswer();
    }

    @And("Customer clicks on Register Button")
    public void customerClicksOnRegisterButton() {
        registrationPage.clickOnRegisterButton();
    }
    @Then("Customer should receive {string}")
    public void customerShouldReceiveSuccessMessage(String successMessage) {
        String actualSuccessMessage = registrationPage.getRegisterSuccessMessage();
        Assert.assertEquals(actualSuccessMessage, successMessage, "User is already exists and not unique");
    }

    @And("User goes to Login Page")
    public void userGoesToLoginPage() {
        loginPage.navigateToLoginPage();
    }

    @And("User enters email and password")
    public void userEntersEmailAndPassword() {
        loginPage.loginAs(customer);
    }

    @And("Customer clicks on Account Button")
    public void customerClicksOnAccountButton() {
        loginPage.clickOnAccountButton();
    }

    @Then("Customer should be logged in to Juice Shop")
    public void customerShouldBeLoggedInToJuiceShop() {
        String actualAccountName = loginPage.getActualAccountName(customer.getEmail());
        Assert.assertEquals(actualAccountName, customer.getEmail());
    }
}
