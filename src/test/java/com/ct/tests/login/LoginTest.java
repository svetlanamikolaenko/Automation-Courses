package com.ct.tests.login;

import com.ct.framework.config.TestConfig;
import com.ct.framework.pages.LoginPage;
import com.ct.model.Customer;
import com.ct.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Sign in")
@Story("Customer sign in to Juice Shop")
public class LoginTest extends BaseTest {

    Customer customer;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        customer = Customer.newBuilder().withEmail(TestConfig.CONFIG.userEmail()).withPassword(TestConfig.CONFIG.userPassword()).build();
        loginPage = new LoginPage(driver);
        loginPage.openPage();
    }

    @AfterMethod
    public void logout() {
       loginPage.logout();
    }


    @Test
    @Feature("Customer login")
    @TmsLink("ELEM-65961")
    public void userCanLoginTest(){
        loginPage.loginAs(customer);
        loginPage.clickOnAccountButton();
        String actualAccountName = loginPage.getActualAccountName(customer.getEmail());
        Assert.assertEquals(actualAccountName, customer.getEmail());
    }
}
