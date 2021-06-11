package com.ct.tests.signup;

import com.ct.framework.pages.LoginPage;
import com.ct.framework.pages.RegistrationPage;
import com.ct.model.Customer;
import com.ct.tests.BaseTest;
import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Sign up")
@Story("Customer sign up to Juice Shop")
public class RegistrationFieldsErrorsTest extends BaseTest {
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
        Assert.assertEquals(title, "User Registration");
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
    public void checkErrorMessageUnderSecurityQuestionFieldTest() throws InterruptedException {
        String errorMessage = registrationPage
                .selectEmptySecurityQuestion()
                .getSecurityQuestionErrorMessage();
        Assert.assertEquals(errorMessage, "Please select a security question.");
    }

    @Test
    public void checkErrorMessagesUnderAnswerFieldTest() {
        String errorMessage = registrationPage
                .enterEmptyAnswer()
                .getAnswerErrorMessage();
        Assert.assertEquals(errorMessage, "Please provide an answer to your security question.");
    }
}
