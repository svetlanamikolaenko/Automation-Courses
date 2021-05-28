package com.ct.tests.signup;

import com.ct.tests.BaseTest;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


public class RegistrationTest extends BaseTest  {
    private String emailField = "//input[contains(@aria-label,'Email')]";
    private String passwordField = "//input[@aria-label='Field for the password']";
    private String passwordRepeatField = "//input[@aria-label='Field to confirm the password']";
    private String securityQuestionPicklist = "//*[@name='securityQuestion']";
    private String answerField = "//input[contains(@data-placeholder,'Answer')]";
    private String registerButton = "//button[@id='registerButton']/span[@class = 'mat-button-wrapper']";
    private String accountButton = "//button[@id='navbarAccount']";

    String email;
    String password;

    @BeforeMethod
    public void openSignUpPage() {
        Faker faker = new Faker();
        driver.findElement(By.xpath(accountButton)).click();
        driver.findElement(By.xpath("//button[@id='navbarLoginButton']")).click();
        driver.findElement(By.xpath("//a[@href='#/register']")).click();
        email = faker.name().username() + "@gmail.com";
        password = faker.code().ean8();
    }

    @AfterMethod
    public void loginAfterRegistration() throws InterruptedException {
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }

    @Test
    public void customerCanRegisterTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(passwordRepeatField)).clear();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys(password);
        driver.findElement(By.xpath(securityQuestionPicklist)).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
              driver.findElement(By.xpath("//span[contains(.,'Your eldest siblings middle name?')]")));;

        driver.findElement(By.xpath(answerField)).clear();
        driver.findElement(By.xpath(answerField)).sendKeys("2468");
        Thread.sleep(2000);
        driver.findElement(By.xpath(registerButton)).click();
        Thread.sleep(5000);
        String title = driver.findElement(By.xpath("//h1")).getText();
        String registerSuccessMessage = driver.findElement(By.xpath("//span[contains(.,'Registration completed')]")).getText();
        softAssert.assertEquals(title, "Login", "The page title is " + title);
        softAssert.assertEquals(registerSuccessMessage,
                "Registration completed successfully. You can now log in.", "User is already exists and not unique");
    }
}
