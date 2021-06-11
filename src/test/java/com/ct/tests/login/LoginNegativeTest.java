package com.ct.tests.login;

import com.ct.framework.pages.LoginPage;
import com.ct.model.Customer;
import com.ct.tests.BaseTest;
import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Sign in")
@Story("Customer sign in to Juice Shop")
public class LoginNegativeTest extends BaseTest {
    Customer customer;
    LoginPage loginPage;
    Faker faker;

    @BeforeMethod
    public void openSignUpPage() {
        faker = new Faker();
        customer = Customer.newBuilder().withEmail(faker.name().username() + "gmail.com").withPassword(faker.code().ean8()).build();
        loginPage = new LoginPage(driver);
        loginPage.openPage();
    }


    @Test
    public void loginWithNotValidEmailTest()  {
       loginPage.loginAs(customer);
        Assert.assertEquals(loginPage.getErrorInvalidEmailOrPassword(), "Invalid email or password.", "Text is " + "Text is " + loginPage.getErrorInvalidEmailOrPassword());
    }

    @Test
    public void checkErrorMessageUnderPasswordFieldTest() {
        loginPage.enterEmail(customer.getEmail());
        loginPage.enterEmptyPassword();
        Assert.assertEquals(loginPage.getErrorUnderPasswordField(), "Please provide a password.");
    }

    @Test
    public void checkLoginButtonIsEnabledTest() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(loginPage.loginButtonIsEnabled());
        loginPage.enterEmail(customer.getEmail());
        loginPage.enterPassword(customer.getPassword());
        softAssert.assertTrue(loginPage.loginButtonIsEnabled());
        softAssert.assertAll();
    }
}
