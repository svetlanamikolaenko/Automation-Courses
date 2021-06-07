package com.ct.tests.signup;

import com.ct.framework.pages.LoginPage;
import com.ct.framework.pages.RegistrationPage;
import com.ct.model.Customer;
import com.ct.tests.BaseTest;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class RegistrationFieldsErrorsTest extends BaseTest {

    final private String registerPageTitle = "User Registration";

    private String emailField = "//input[contains(@aria-label,'Email')]";
    private String passwordField = "//input[@aria-label='Field for the password']";
    private String passwordRepeatField = "//input[@aria-label='Field to confirm the password']";
    private String securityQuestionPicklist = "//*[@name='securityQuestion']";
    private String answerField = "//input[contains(@data-placeholder,'Answer')]";
    private String registerButton = "//button[@id='registerButton']";

    private String accountButton = "//button[@id='navbarAccount']";
    private String loginNavButton = "//button[@id='navbarLoginButton']";

    Customer customer;
    RegistrationPage registrationPage;
    LoginPage loginPage;
    Faker faker;


    @BeforeMethod
    public void openSignUpPage() {
        faker = new Faker();
        customer = Customer.newBuilder().withEmail("svitlana8@gmail.com").withPassword("passw0rd").build();
        registrationPage = new RegistrationPage(driver);
        registrationPage.openPage();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void signUpPageIsOpenTest() {
        String title = registrationPage.getCaption();
        Assert.assertEquals(title, registerPageTitle);
    }

    @Test
    public void emailMustBeUniqueErrorTest() {
        registrationPage.registerAs(customer);
        Assert.assertEquals(registrationPage.getErrorUniqueUser(),
                "Email must be unique", "Email is unique. User not exists!");
    }

    @Test
    public void checkErrorMessagesUnderEmailFieldTest() {
        String errorMessage = registrationPage
                .enterEmptyEmail()
                .getEmailErrorMessage();
        Assert.assertEquals(errorMessage, "Please provide an email address.");

        errorMessage = registrationPage
                .enterNotValidEmail()
                .getEmailErrorMessage();
        Assert.assertEquals(errorMessage, "Email address is not valid.");
    }

    @Test
    public void checkErrorMessagesUnderPasswordFieldTest() {
        SoftAssert softAssert = new SoftAssert();

        String errorMessage = registrationPage.enterEmptyPassword()
                .getPasswordErrorMessage();
        softAssert.assertEquals(errorMessage, "Please provide a password.");

        errorMessage = registrationPage
                .enterShortPassword()
                .getPasswordErrorMessage();

        softAssert.assertEquals(errorMessage, "Password must be 5-20 characters long.");
        softAssert.assertAll();
    }


    @Test
    public void checkErrorMessagesUnderRepeatPasswordFieldTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(passwordRepeatField)).clear();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys(Keys.TAB);

        String errorMessage = registrationPage
                .enterEmptyRepeatPassword()
                .getRepeatPasswordErrorMessage();
        softAssert.assertEquals(errorMessage, "Please repeat your password.");
        registrationPage.enterPassword(customer.getPassword());

        errorMessage  = registrationPage
                .enterAnyRepeatPassword()
                .getRepeatPasswordErrorMessage();
        softAssert.assertEquals(errorMessage, "Passwords do not match");
        softAssert.assertAll();
    }


    @Test
    public void checkErrorMessageUnderSecurityQuestionFieldTest() {
        driver.findElement(By.xpath(securityQuestionPicklist)).click();
        driver.findElement(By.xpath(securityQuestionPicklist)).sendKeys(Keys.ESCAPE);
        String errorMessage = "//*[@name='securityQuestion']/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorMessage)));
        Assert.assertEquals(driver.findElement(By.xpath(errorMessage)).getText(), "Please select a security question.");
    }

    @Test
    public void checkErrorMessagesUnderAnswerFieldTest() {
        driver.findElement(By.xpath(answerField)).clear();
        driver.findElement(By.xpath(answerField)).sendKeys(Keys.TAB);
        String errorMessage = "//input[contains(@data-placeholder,'Answer')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorMessage)));
        Assert.assertEquals(driver.findElement(By.xpath(errorMessage)).getText(), "Please provide an answer to your security question.");
    }
}
