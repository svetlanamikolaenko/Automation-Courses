package com.ct.tests.login;

import com.ct.framework.pages.LoginPage;
import com.ct.model.Customer;
import com.ct.tests.BaseTest;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    Customer customer;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        customer = Customer.newBuilder().withEmail("svitlana8@gmail.com").withPassword("passw0rd").build();
        loginPage = new LoginPage(driver);
        loginPage.openPage();
    }

    @AfterMethod
    public void logout() {
       loginPage.logout();
    }

    @Test
    public void userCanLoginTest(){
        loginPage.loginAs(customer);
        loginPage.clickOnAccountButton();
        String actualAccountName = loginPage.getActualAccountName(customer.getEmail());
        Assert.assertEquals(actualAccountName, customer.getEmail());
    }
}
