package com.ct.tests.signup;

import com.ct.framework.pages.LoginPage;
import com.ct.framework.pages.RegistrationPage;
import com.ct.model.Customer;
import com.ct.tests.BaseTest;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class RegistrationTest extends BaseTest  {


    Customer customer;
    RegistrationPage registrationPage;
    LoginPage loginPage;

    @BeforeMethod
    public void openSignUpPage() {
        Faker faker = new Faker();
        customer = Customer.newBuilder().withEmail(faker.name().username() + "@gmail.com").withPassword(faker.code().ean8()).build();
        registrationPage = new RegistrationPage(driver);
        registrationPage.openPage();
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void refreshPage()  {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
        driver.navigate().refresh();
    }

    @Test
    public void customerCanRegisterTest(){
        registrationPage.registerAs(customer);
        String registerSuccessMessage = registrationPage.getRegisterSuccessMessage();
        Assert.assertEquals(registerSuccessMessage,
                "Registration completed successfully. You can now log in.", "User is already exists and not unique");
    }

    @Test
    public void customerCanLoginAfterRegisterTest() {
        registrationPage.registerAs(customer);
        String actualPageCaption = registrationPage.getActualCaption();
        Assert.assertEquals(actualPageCaption, "Login");
        loginPage.loginAs(customer);
        String profilePageHeading = loginPage.getActualHeading();
        Assert.assertEquals(profilePageHeading, "All Products");
        loginPage.clickOnAccountButton();
        String actualAccountName = loginPage.getActualAccountName(customer.getEmail());
        Assert.assertEquals(actualAccountName, customer.getEmail());
    }
}
