package com.ct.tests.login;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {
    private String emailField = "//input[@name='email']";
    private String passwordField = "//input[@name='password']";
    private String loginButton = "//button[@id='loginButton']";
    private String logoutButton = "//button[@id='navbarLogoutButton']";
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

    @Test
    public void loginPageIsOpenTesT(){
        SoftAssert softAssert = new SoftAssert();
        String title = driver.findElement(By.xpath("//h1")).getText();
        softAssert.assertEquals(title, "Login");
        softAssert.assertTrue(driver.findElement(By.xpath(loginButton)).getAttribute("disabled").contains("true"));
        softAssert.assertTrue(driver.findElement(By.xpath(forgotPasswordLink)).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.xpath(registerLinkPage)).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.xpath(rememberMeCheckbox)).isDisplayed());
    }

    @Test
    public void userCanLoginTest() throws InterruptedException {
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
        //driver.findElement(By.xpath("//button[@id='navbarLoginButton']")).click();
    }

}
