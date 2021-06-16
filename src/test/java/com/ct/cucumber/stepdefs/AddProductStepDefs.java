package com.ct.cucumber.stepdefs;

import com.ct.cucumber.page.ProfilePage;
import com.ct.cucumber.runner.CucumberBaseTest;
import com.ct.framework.config.TestConfig;
import com.ct.framework.driver.WebDriverRunner;
import com.ct.framework.pages.LoginPage;
import com.ct.model.Customer;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AddProductStepDefs {
    private LoginPage loginPage = new LoginPage(WebDriverRunner.getWebDriver());
    private ProfilePage profilePage = new ProfilePage(WebDriverRunner.getWebDriver());
    private Customer customer = Customer.newBuilder().withEmail(TestConfig.CONFIG.userEmail()).withPassword(TestConfig.CONFIG.userPassword()).build();

    @Given("Customer goes to Login Page")
    public void customerGoesToLoginPage() {
        loginPage.openPage();
    }

    @When("User logins as existing customer")
    public void userLoginsAsExistingCustomer() {
        loginPage.loginAs(customer);
    }

    @And("Customer goes to All Products Page")
    public void customerGoesToAllProductsPage() {
        Assert.assertTrue(profilePage.profilePageHeadingIsDisplayed());
    }

    @And("Customer can view {string}")
    public void customerCanViewProductItem(String productItem) {
        Assert.assertTrue(profilePage.itemNameIsDisplayed(productItem));
    }

    @And("Customer clicks on {string} Add To Basket Button")
    public void customerClicksOnProductItemAddToBasketButton(String productItem) {
        profilePage.clickOnAddToBasketButton(productItem);
    }

    @Then("Customer should receive message \"Placed {string} into basket.\"")
    public void customerShouldReceiveSuccessMessage(String productItem) {
        String actualSuccessMessage = profilePage.getAddToBasketSuccessMessage(productItem);
        Assert.assertEquals(actualSuccessMessage, "Placed " + productItem + " into basket.");
    }

    @And("Customer should view {string} in Basket")
    public void customerShouldViewProductItemInBasket(String productItem) {
        profilePage.clickOnBasketButton();
        Assert.assertTrue(profilePage.productTableIsDisplayed());
        String actualProductInBasket = profilePage.productInBasket(productItem);
        Assert.assertEquals(actualProductInBasket, productItem);
    }

    @And("Customer removes {string} from Basket")
    public void customerRemovesProductItemFromBasket(String productItem) {
        profilePage.removeProductFromBasket(productItem);
        Assert.assertEquals(profilePage.getTotalPrice(), 0.0);
    }
}

