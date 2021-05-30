package com.ct.tests.product;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProductAddTest extends BaseTest {
    private String accountButton  = "//button[@id='navbarAccount']";
    private String applePomace = "Apple Pomace";
    private String applePomaceItem = "//div[@class = 'item-name' and contains(., '" + applePomace + "')]";
    private String addToBasket = "//ancestor::figure//button[@aria-label='Add to Basket']";
    private String basketButton = "//button[contains(@aria-label,'Show the shopping cart')]";


    String email;
    String password;

    @BeforeMethod
    public void openSignUpPage() {
        driver.findElement(By.xpath(accountButton)).click();
        driver.findElement(By.xpath("//button[@id='navbarLoginButton']")).click();
        email = "svitlana14@gmail.com";
        password = "passw0rd";
    }

    @AfterMethod
    public void logout() {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
    }

    @Test
    public void verifyAddingProductToBasket(){
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[contains(@class , 'heading')]"), "All Products"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(applePomaceItem)));
        driver.findElement(By.xpath(applePomaceItem + addToBasket)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Apple Pomace')]")));
        softAssert.assertEquals(driver.findElement(By.xpath("//span[contains(.,'Apple Pomace')]")).getText(), "Placed Apple Pomace into basket.");
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(.,'Apple Pomace')]")));
        driver.findElement(By.xpath(basketButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-row")));
        softAssert.assertEquals(driver.findElement(By.xpath("//mat-cell[contains(.,'" + applePomace + "')]")).getText(), applePomace);
        driver.findElement(By.xpath("//mat-cell[contains(., 'Apple Pomace')]//following-sibling::mat-cell[last()]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//mat-cell")));
    }

    @Test
    public void verifyAddingSoldOutProductToBasket() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[contains(@class , 'heading')]"), "All Products"));
        driver.manage().addCookie( new Cookie("cookieconsent_status", "dismiss"));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
        softAssert.assertFalse(driver.findElement(By.xpath("//div[@role='dialog']")).isDisplayed(),"not visible");


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//button[@aria-label='Next page']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Next page']")));
        driver.findElement(By.xpath("//button[@aria-label='Next page']")).click();
        Thread.sleep(10000);



//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(applePomaceItem)));
//        driver.findElement(By.xpath(applePomaceItem + addToBasket)).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Apple Pomace')]")));
//        softAssert.assertEquals(driver.findElement(By.xpath("//span[contains(.,'Apple Pomace')]")).getText(), "Placed Apple Pomace into basket.");
//        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(.,'Apple Pomace')]")));
//        driver.findElement(By.xpath(basketButton)).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-row")));
//        softAssert.assertEquals(driver.findElement(By.xpath("//mat-cell[contains(.,'" + applePomace + "')]")).getText(), applePomace);
//        driver.findElement(By.xpath("//mat-cell[contains(., 'Apple Pomace')]//following-sibling::mat-cell[last()]")).click();
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//mat-cell")));
    }
}
