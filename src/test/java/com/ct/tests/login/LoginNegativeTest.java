package com.ct.tests.login;

import com.ct.tests.BaseTest;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginNegativeTest extends BaseTest {
    private String emailField = "//input[@name='email']";
    private String passwordField = "//input[@name='password']";
    private String loginButton = "//button[@id='loginButton']";
    private String accountButton  = "//button[@id='navbarAccount']";
    private String loginNavButton  = "//button[@id='navbarLoginButton']";


    String email;
    String password;

    @BeforeMethod
    public void openSignUpPage() {
        Faker faker = new Faker();
        driver.findElement(By.xpath(accountButton)).click();
        driver.findElement(By.xpath(loginNavButton)).click();
        email = faker.name().username() + "gmail.com";
        password = faker.code().ean8();
    }

    @Test
    public void loginWithNotValidEmailTest()  {
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(loginButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class , 'error')]")));
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(@class , 'error')]")).getText(), "Invalid email or password.");
    }

    @Test
    public void checkErrorMessageUnderPasswordFieldTest() {
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(Keys.TAB);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//mat-error")));
        Assert.assertEquals(driver.findElement(By.xpath("//mat-error")).getText(), "Please provide a password.");
    }

    @Test
    public void checkLoginButtonIsDisabledTest() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(driver.findElement(By.xpath(loginButton)).getAttribute("disabled").contains("true"));
        softAssert.assertFalse(driver.findElement(By.xpath(loginButton)).isEnabled());
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loginButton)));
        softAssert.assertTrue(driver.findElement(By.xpath(loginButton)).isEnabled());
        softAssert.assertAll();
    }
}
