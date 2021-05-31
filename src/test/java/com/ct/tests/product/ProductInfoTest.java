package com.ct.tests.product;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProductInfoTest extends BaseTest {
    private String bananaJuiceItem = "//div[@class = 'item-name' and contains(., 'Banana Juice')]";
    private String itemPrice = "/following-sibling::div[@class='item-price']";
    private String itemImage = "/../preceding-sibling::div/img";
    private String bananaJuiceDescription = "Monkeys love it the most.";
    private String card = "//mat-dialog-container";
    private String priceInCard = "//p[@class = 'item-price']";
    private String nameInCard = "//h1";
    private String imageInCard = "//img[@alt='Banana Juice (1000ml)']";
    private String descriptionInCard = "//br/preceding-sibling::div";

    @Test
    public void verifyActualProductInfo(){
        SoftAssert softAssert = new SoftAssert();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(bananaJuiceItem)));
        String bananaJuiceName = driver.findElement(By.xpath(bananaJuiceItem)).getText();
        String bananaJuicePrice = driver.findElement(By.xpath(bananaJuiceItem + itemPrice)).getText();
        String bananaJuiceImage = driver.findElement(By.xpath( bananaJuiceItem + itemImage)).getAttribute("src");
        driver.findElement(By.xpath(bananaJuiceItem)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(card)));
        String bananaJuiceNameInCard = driver.findElement(By.xpath(nameInCard)).getText();
        String bananaJuicePriceInCard = driver.findElement(By.xpath(priceInCard)).getText();
        String bananaJuiceImageInCard = driver.findElement(By.xpath(imageInCard)).getAttribute("src");
        String bananaJuiceDescriptionInCard = driver.findElement(By.xpath(descriptionInCard)).getText();

        softAssert.assertEquals(bananaJuiceNameInCard, bananaJuiceName);
        softAssert.assertEquals(bananaJuicePriceInCard, bananaJuicePrice);
        softAssert.assertEquals(bananaJuiceImageInCard, bananaJuiceImage);
        softAssert.assertEquals(bananaJuiceDescriptionInCard, bananaJuiceDescription );
        softAssert.assertAll();
    }
}
