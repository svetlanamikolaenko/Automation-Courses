package com.ct.tests.product;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ProductAddTest extends BaseTest {
    private String accountButton  = "//button[@id='navbarAccount']";
    private String loginNavButton  = "//button[@id='navbarLoginButton']";
    private String applePomace = "Apple Pomace";
    private String applePomaceItem = "//div[@class = 'item-name' and contains(., '" + applePomace + "')]";
    private String juiceShopCoaster = "OWASP Juice Shop Coaster";
    private String juiceShopCoasterItem = "//div[@class = 'item-name' and contains(., '" + juiceShopCoaster + "')]";
    private String addToBasket = "//ancestor::figure//button[@aria-label='Add to Basket']";
    private String basketButton = "//button[contains(@aria-label,'Show the shopping cart')]";
    private String nextPageButton = "//button[@aria-label='Next page']";
    private String soldOut = "//ancestor::mat-card/div[contains(@class,'ribbon-sold')]";
    private String outOfStockErrorMessage = "//span[contains(.,'We are out of stock!')]";

    String email = "svitlana8@gmail.com";
    String password = "passw0rd";

    @BeforeMethod
    public void login() {
        driver.findElement(By.xpath(accountButton)).click();
        driver.findElement(By.xpath(loginNavButton)).click();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }

    @AfterMethod
    public void logout() {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
        driver.navigate().refresh();
    }

    @Test
    public void verifyAddingProductToBasket(){
        SoftAssert softAssert = new SoftAssert();
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[contains(@class , 'heading')]"), "All Products"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(applePomaceItem)));
        driver.findElement(By.xpath(applePomaceItem + addToBasket)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Apple Pomace')]")));
        softAssert.assertEquals(driver.findElement(By.xpath("//span[contains(.,'Apple Pomace')]")).getText(), "Placed Apple Pomace into basket.");

        driver.findElement(By.xpath(basketButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-row")));
        softAssert.assertEquals(driver.findElement(By.xpath("//mat-cell[contains(.,'" + applePomace + "')]")).getText(), applePomace);

        driver.findElement(By.xpath("//mat-cell[contains(., 'Apple Pomace')]//following-sibling::mat-cell[last()]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//mat-cell")));
        softAssert.assertAll();
    }

    @Test
    public void verifyAddingSoldOutProductToBasket() {
        SoftAssert softAssert = new SoftAssert();
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[contains(@class , 'heading')]"), "All Products"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//button[@aria-label='Next page']")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(nextPageButton)));
        driver.findElement(By.xpath(nextPageButton)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(juiceShopCoasterItem)));
        softAssert.assertEquals(driver.findElement(By.xpath(juiceShopCoasterItem + soldOut)).getText(), "Sold Out");

        driver.findElement(By.xpath(juiceShopCoasterItem + addToBasket)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(outOfStockErrorMessage)));
        softAssert.assertEquals(driver.findElement(By.xpath(outOfStockErrorMessage)).getText(), "We are out of stock! Sorry for the inconvenience.");

        driver.findElement(By.xpath(basketButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//mat-table")));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(outOfStockErrorMessage)));
        softAssert.assertFalse(driver.findElement(By.xpath("//button[@id='checkoutButton']")).isEnabled(), "Check items in the basket!");
        softAssert.assertEquals(driver.findElement(By.xpath("//div[@id='price']")).getText(), "Total Price: 0¤");
        softAssert.assertAll();
    }
}
