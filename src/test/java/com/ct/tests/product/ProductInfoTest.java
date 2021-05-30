package com.ct.tests.product;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProductInfoTest extends BaseTest {
    private String bananaJuiceItem = "//div[@class = 'item-name' and contains(., 'Banana Juice')]";


    @Test
    public void verifyActualProductInfo(){
        SoftAssert softAssert = new SoftAssert();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(bananaJuiceItem)));
        String bananaJuiceName = driver.findElement(By.xpath(bananaJuiceItem)).getText();
        String bananaJuicePrice = driver.findElement(By.xpath(bananaJuiceItem + "/following-sibling::div[@class='item-price']")).getText();
        String bananaJuiceImage = driver.findElement(By.xpath( bananaJuiceItem + "/../preceding-sibling::div/img")).getAttribute("src");
        String bananaJuiceDescription = "Monkeys love it the most.";

        driver.findElement(By.xpath(bananaJuiceItem)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//mat-dialog-container")));

        String bananaJuiceNameInCard = driver.findElement(By.xpath("//h1")).getText();
        String bananaJuicePriceInCard = driver.findElement(By.xpath("//p[@class = 'item-price']")).getText();
        String bananaJuiceImageInCard = driver.findElement(By.xpath("//img[@alt='Banana Juice (1000ml)']")).getAttribute("src");
        String bananaJuiceDescriptionInCard = driver.findElement(By.xpath("//br/preceding-sibling::div")).getText();

        softAssert.assertEquals(bananaJuiceName, bananaJuiceNameInCard);
        softAssert.assertEquals(bananaJuicePrice, bananaJuicePriceInCard);
        softAssert.assertEquals(bananaJuiceImage, bananaJuiceImageInCard );
        softAssert.assertEquals(bananaJuiceDescriptionInCard, bananaJuiceDescription );
        softAssert.assertAll();
    }
}
