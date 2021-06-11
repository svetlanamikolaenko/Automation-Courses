package com.ct.tests.signup;

import com.ct.framework.pages.LoginPage;
import com.ct.framework.pages.RegistrationPage;
import com.ct.model.Customer;
import com.ct.tests.BaseTest;
import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Sign up")
@Story("Customer sign up to Juice Shop")
public class RegistrationTest extends BaseTest  {

    Customer customer;
    RegistrationPage registrationPage;
    LoginPage loginPage;
    Faker faker;

    @BeforeMethod
    public void openSignUpPage() {
        faker = new Faker();
        customer = Customer.newBuilder().withEmail(faker.name().username() + "@gmail.com").withPassword(faker.code().ean8()).build();
        registrationPage = new RegistrationPage(driver);
        registrationPage.openPage();
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void clearLocalStorage()  {
        loginPage.logout();
    }


    @Test
    @Feature("Customer registration")
    public void customerCanRegisterTest(){
        registrationPage.registerAs(customer);
        Assert.assertEquals(registrationPage.getRegisterSuccessMessage(),
                registrationPage.getRegistrationCompletedSuccessMessage()
                ,"User is already exists and not unique");
    }

    @Test
    @Feature("Customer can login after registration")
    public void customerCanLoginAfterRegisterTest() {
        registrationPage.registerAs(customer);
        String actualAccountName = loginPage
                .navigateToLoginPage()
                .loginAs(customer)
                .clickOnAccountButton()
                .getActualAccountName(customer.getEmail());
        Assert.assertEquals(actualAccountName, customer.getEmail());
    }
}
