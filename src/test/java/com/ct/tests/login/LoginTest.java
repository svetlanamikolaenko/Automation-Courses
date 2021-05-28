package com.ct.tests.login;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {
    private String emailField = "//input[@name='email']";
    private String passwordField = "//input[@name='password']";
    private String loginButton = "//button[@id='loginButton']";
    private String logoutButton = "//button[@id='navbarLogoutButton']/div";
    private String forgotPasswordLink = "//a[@href='#/forgot-password']";
    private String registerLinkPage = "//a[@href='#/register']";
    private String rememberMeCheckbox = "//input[@type='checkbox']";
    private String accountButton  = "//button[@id='navbarAccount']";


    String email = "svitlana14@gmail.com";
    String password = "passw0rd";

    @BeforeMethod
    public void openSignUpPage() {
        driver.findElement(By.xpath(accountButton)).click();
        driver.findElement(By.xpath("//button[@id='navbarLoginButton']")).click();
    }

    public void logout() {
        Actions builder = new Actions(driver);
        builder.moveToElement(driver.findElement(By.xpath(logoutButton)))
                .click()
                .build()
                .perform();
        driver.navigate().refresh();


    }

    @Test
    public void loginPageFieldsPresentTest(){
        SoftAssert softAssert = new SoftAssert();
        String title = driver.findElement(By.xpath("//h1")).getText();
        softAssert.assertEquals(title, "Login");
        softAssert.assertTrue(driver.findElement(By.xpath(loginButton)).getAttribute("disabled").contains("true"));
        softAssert.assertTrue(driver.findElement(By.xpath(forgotPasswordLink)).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.xpath(registerLinkPage)).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.xpath(rememberMeCheckbox)).isDisplayed());
    }

    @Test
    public void auserCanLoginTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(loginButton)).click();
        Thread.sleep(2000);
        softAssert.assertEquals(driver.findElement(By.xpath("//div[contains(@class , 'heading')]")).getText(), "All Products");
        driver.findElement(By.xpath(accountButton)).click();
        softAssert.assertEquals(driver.findElement(By.xpath("//button[@aria-label='Go to user profile']/span")).getText(), email);
        softAssert.assertAll();
        logout();
    }

    @Test
    public void loginWithNotValidEmailTest() throws InterruptedException {
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys("text");
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(loginButton)).click();
        Thread.sleep(2000);
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(@class , 'error')]")).getText(), "Invalid email or password.");
    }

    @Test
    public void loginWithNotValidPasswordTest() throws InterruptedException {
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys("notvalidpasssword");
        driver.findElement(By.xpath(loginButton)).click();
        Thread.sleep(2000);
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(@class , 'error')]")).getText(), "Invalid email or password.");
    }
}
