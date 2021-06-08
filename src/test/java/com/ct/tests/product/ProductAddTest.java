package com.ct.tests.product;

import com.ct.framework.pages.LoginPage;
import com.ct.framework.pages.ProfilePage;
import com.ct.model.Customer;
import com.ct.model.Product;
import com.ct.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
        customer = Customer.newBuilder().withEmail("svitlana8@gmail.com").withPassword("passw0rd").build();
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
    public void verifyAddingProductToBasket(){
        SoftAssert softAssert = new SoftAssert();
        String successMessage = profilePage.addApplePomaceToBasket();
        softAssert.assertEquals(successMessage, "Placed Apple Pomace into basket.");
        String applePomace = profilePage.applePomaceInBasket();
        softAssert.assertEquals(applePomace, applePomaceProduct.getName());
        softAssert.assertEquals(profilePage.getTotalPrice(), applePomaceProduct.getPrice() );
        profilePage.removeApplePomaceFromBasket();
        softAssert.assertAll();
    }

    @Test
    public void verifyAddingSoldOutProductToBasket() {
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(profilePage.profilePageHeadingIsDisplayed());
        profilePage.clickOnNextButton();
        profilePage.clickOnButtonAddToBasketJuiceShopCoaster();
        Assert.assertEquals(profilePage.getOutOfStockMessage(),
                "We are out of stock! Sorry for the inconvenience.",
                "Chosen item is not sold out!");
        profilePage.clickOnBasketButton();
        Assert.assertTrue(profilePage.productTableIsDisplayed());
        softAssert.assertFalse(profilePage.checkoutButtonIsEnabled(), "Check items in the basket!");
        softAssert.assertEquals(profilePage.getTotalPrice(), 0.0);
        softAssert.assertAll();
    }
}
