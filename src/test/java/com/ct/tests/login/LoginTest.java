package com.ct.tests.login;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {
    private String emailField = "//input[@name='email']";
    private String passwordField = "//input[@name='password']";
    private String loginButton = "//button[@id='loginButton']";
    private String accountButton  = "//button[@id='navbarAccount']";

    String email;
    String password;

    @BeforeMethod
    public void openSignUpPage() {
        wait = new WebDriverWait(driver, 5);
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
    public void userCanLoginTest() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Login");
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class , 'heading')]")));
        softAssert.assertEquals(driver.findElement(By.xpath("//div[contains(@class , 'heading')]")).getText(), "All Products");
        driver.findElement(By.xpath(accountButton)).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//button[@aria-label='Go to user profile']/span"), email));
        softAssert.assertEquals(driver.findElement(By.xpath("//button[@aria-label='Go to user profile']/span")).getText(), email);
        softAssert.assertAll();
    }
}
