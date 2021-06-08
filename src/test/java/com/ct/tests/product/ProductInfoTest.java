package com.ct.tests.product;

import com.ct.framework.pages.ProfilePage;
import com.ct.model.Product;
import com.ct.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProductInfoTest extends BaseTest {
    Product product;
    ProfilePage profilePage;

    @BeforeMethod
    public void setUp() {
        product = Product.newBuilder().withName("Banana Juice (1000ml)").withDescription("Monkeys love it the most.").withPrice(1.99).withImage("http://beeb0b73705f.sn.mynetname.net:3000/assets/public/images/products/banana_juice.jpg").build();
        profilePage = new ProfilePage(driver);
    }

    @Test
    public void verifyActualProductInfo() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(product.getName(), profilePage.getBananaJuiceName());
        softAssert.assertEquals(product.getImage(), profilePage.getBananaJuiceImage());
        softAssert.assertEquals(product.getPrice(), profilePage.getBananaJuicePrice());
        softAssert.assertAll();
    }

    @Test
    public void verifyActualProductInfoInCard() {
        SoftAssert softAssert = new SoftAssert();
        profilePage.clickOnBananaJuiceItem();
        Assert.assertTrue(profilePage.cardIsOpen());
        softAssert.assertEquals(product.getName(), profilePage.getNameInCard());
        softAssert.assertEquals(product.getPrice(), profilePage.getPriceInCard());
        softAssert.assertEquals(product.getImage(), profilePage.getImageInCard());
        softAssert.assertEquals(product.getDescription(), profilePage.getDescriptionInCard());
        softAssert.assertAll();
    }
}
