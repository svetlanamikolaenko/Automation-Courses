package com.ct.tests.login;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
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
        driver.findElement(By.xpath(accountButton)).click();
        driver.findElement(By.xpath("//button[@id='navbarLoginButton']")).click();
        email = "svitlana14@gmail.com";
        password = "passw0rd";
    }

    @AfterMethod
    public void logout() {
        driver.navigate().refresh();
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
    }

    @Test
    public void userCanLoginTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Login");
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(loginButton)).click();
        Thread.sleep(2000);
        softAssert.assertEquals(driver.findElement(By.xpath("//div[contains(@class , 'heading')]")).getText(), "All Products");
        driver.findElement(By.xpath(accountButton)).click();
        Thread.sleep(2000);
        softAssert.assertEquals(driver.findElement(By.xpath("//button[@aria-label='Go to user profile']/span")).getText(), email);
        softAssert.assertAll();
    }
}
