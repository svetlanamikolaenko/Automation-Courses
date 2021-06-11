package com.ct.tests.product;

import com.ct.framework.pages.ProfilePage;
import com.ct.model.Product;
import com.ct.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("View Product Information")
@Story("Juice Shop| Viewing information about the product")
public class ProductInfoTest extends BaseTest {
    Product product;
    ProfilePage profilePage;

    @BeforeMethod
    public void setUp() {
        product = Product.newBuilder().withName("Banana Juice (1000ml)").withDescription("Monkeys love it the most.").withPrice(1.99).withImage("http://beeb0b73705f.sn.mynetname.net:3000/assets/public/images/products/banana_juice.jpg").build();
        profilePage = new ProfilePage(driver);
    }
    @Test
    @Feature("Actual product item information in All Products")
    public void verifyActualProductInfo() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(product.getName(), profilePage.getProductName(product.getName()));
        softAssert.assertEquals(product.getImage(), profilePage.getProductImage(product.getName()));
        softAssert.assertEquals(product.getPrice(), profilePage.getProductPrice(product.getName()));
        softAssert.assertAll();
    }

    @Test
    @Feature("Product item information in Card")
    public void verifyActualProductInfoInCard() {
        SoftAssert softAssert = new SoftAssert();
        profilePage.clickOnProductItem(product.getName());
        Assert.assertTrue(profilePage.cardIsOpen());
        softAssert.assertEquals(product.getName(), profilePage.getNameInCard());
        softAssert.assertEquals(product.getPrice(), profilePage.getPriceInCard());
        softAssert.assertEquals(product.getImage(), profilePage.getImageInCard());
        softAssert.assertEquals(product.getDescription(), profilePage.getDescriptionInCard());
        softAssert.assertAll();
    }
}
