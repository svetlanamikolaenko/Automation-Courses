package com.ct.tests.product;

import com.ct.framework.config.TestConfig;
import com.ct.framework.pages.LoginPage;
import com.ct.framework.pages.ProfilePage;
import com.ct.model.Customer;
import com.ct.model.Product;
import com.ct.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Add Products to Basket")
@Story("Juice Shop| Adding products to basket")
public class ProductAddTest extends BaseTest {
    Customer customer;
    Product applePomaceProduct;
    Product juiceShopCoasterProduct;
    LoginPage loginPage;
    ProfilePage profilePage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        customer = Customer.newBuilder().withEmail(TestConfig.CONFIG.userEmail()).withPassword(TestConfig.CONFIG.userPassword()).build();
        applePomaceProduct = Product.newBuilder().withName("Apple Pomace").withPrice(0.89).build();
        juiceShopCoasterProduct = Product.newBuilder().withName("OWASP Juice Shop Coaster").build();
        loginPage.openPage();
        loginPage.loginAs(customer);
    }

    @AfterMethod
    public void clearLocalStorage() {
        loginPage.logout();
    }

    @Test
    @Feature("Customer can add product to basket")
    public void verifyAddingProductToBasket(){
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(profilePage.profilePageHeadingIsDisplayed());
        Assert.assertTrue(profilePage.itemNameIsDisplayed(applePomaceProduct.getName()));

        profilePage.clickOnAddToBasketButton(applePomaceProduct.getName());
        String successMessage = profilePage.getAddToBasketSuccessMessage(applePomaceProduct.getName());
        softAssert.assertEquals(successMessage, "Placed Apple Pomace into basket.");

        String applePomace = profilePage.productInBasket(applePomaceProduct.getName());
        softAssert.assertEquals(applePomace, applePomaceProduct.getName());
        softAssert.assertEquals(profilePage.getTotalPrice(), applePomaceProduct.getPrice());
        profilePage.removeProductFromBasket(applePomaceProduct.getName());
        softAssert.assertAll();
    }

    @Test
    @Feature("Customer can't add Sold Out product to basket")
    public void verifyAddingSoldOutProductToBasket() {
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(profilePage.profilePageHeadingIsDisplayed());
        profilePage.clickOnNextButton();
        profilePage.clickOnAddToBasketButton(juiceShopCoasterProduct.getName());
        Assert.assertEquals(profilePage.getOutOfStockMessage(),
                "We are out of stock! Sorry for the inconvenience.",
                "Chosen item is not sold out!");
        profilePage.clickOnBasketButton();
        Assert.assertTrue(profilePage.productTableIsDisplayed());
        softAssert.assertFalse(profilePage.checkoutButtonIsEnabled(), "Check items in the basket!");
        softAssert.assertEquals(profilePage.getTotalPrice(), 0.0);
        softAssert.assertAll();
    }

    @Test
    @Feature("Customer can click on Product Item")
    public void verifyClickingOnProductItem(){
        Assert.assertTrue(profilePage.profilePageHeadingIsDisplayed());
        profilePage.clickOnProductItem(applePomaceProduct.getName());
        Assert.assertEquals(profilePage.getNameInCard(), applePomaceProduct.getName());
    }
}
