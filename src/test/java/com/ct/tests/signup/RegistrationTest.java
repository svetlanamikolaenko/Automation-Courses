package com.ct.tests.signup;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;

public class RegistrationTest extends BaseTest  {
    private String emailField = "//input[contains(@aria-label,'Email')]";
    private String passwordField = "//input[@aria-label='Field for the password']";
    private String passwordRepeatField = "//input[@aria-label='Field to confirm the password']";
    private String showPasswordAdviceTumbler = "//input[@type='checkbox']";
    private String securityQuestionPicklist = "//*[@name='securityQuestion']";
    private String answerField = "//input[contains(@data-placeholder,'Answer')]";
    private String registerButton = "//button[@id='registerButton']/span[@class = 'mat-button-wrapper']";


    @BeforeMethod
    public void openSignUpPage() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@aria-label='Close Welcome Banner']")).click();
        driver.findElement(By.xpath("//button[@id='navbarAccount']")).click();
        driver.findElement(By.xpath("//button[@id='navbarLoginButton']")).click();
        driver.findElement(By.xpath("//a[@href='#/register']")).click();
    }

    @Test
    public void signUpPageIsOpenTest(){
        String title = driver.findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(title, "User Registration");
    }

    @Test
    public void customerCanRegisterTest() throws InterruptedException {
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys("svitlana7@gmail.com");
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys("passw0rd");
        driver.findElement(By.xpath(passwordRepeatField)).clear();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys("passw0rd");
        driver.findElement(By.xpath(securityQuestionPicklist)).click();

        WebElement questionOption = driver.findElement(By.xpath("//mat-option[@id='mat-option-11']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", questionOption);
        questionOption.click();

        driver.findElement(By.xpath(answerField)).clear();
        driver.findElement(By.xpath(answerField)).sendKeys("2468");
        Thread.sleep(2000);
        driver.findElement(By.xpath(registerButton)).click();
        Thread.sleep(5000);
        String title = driver.findElement(By.xpath("//h1")).getText();

        String reg = driver.findElement(By.xpath("//span[contains(.,'Registration completed')]")).getText();
        Assert.assertEquals(title, "Login");
        Assert.assertEquals(reg, "Registration completed successfully. You can now log in.");
    }
}
