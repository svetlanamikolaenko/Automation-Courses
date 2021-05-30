package com.ct.tests.product;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProductTest extends BaseTest {
    private String emailField = "//input[@name='email']";
    private String passwordField = "//input[@name='password']";
    private String loginButton = "//button[@id='loginButton']";
    private String accountButton  = "//button[@id='navbarAccount']";
    //private String bananaJuiceItem = "//div[@class = 'item-name' and contains(., 'Banana Juice')]//ancestor::mat-card/div[contains(@aria-label, 'Click')]";
    private String bananaJuiceItem = "//div[@class = 'item-name' and contains(., 'Banana Juice')]";

    String email;
    String password;
    WebDriverWait wait;

    @BeforeMethod
    public void openSignUpPage() {
        wait = new WebDriverWait(driver, 5);
        email = "svitlana14@gmail.com";
        password = "passw0rd";
    }

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
