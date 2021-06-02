package com.ct.tests.signup;

import com.ct.tests.BaseTest;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


public class RegistrationTest extends BaseTest  {
    private String emailField = "//input[contains(@aria-label,'Email')]";
    private String passwordField = "//input[@aria-label='Field for the password']";
    private String passwordRepeatField = "//input[@aria-label='Field to confirm the password']";
    private String securityQuestionPicklist = "//*[@name='securityQuestion']";
    private String answerField = "//input[contains(@data-placeholder,'Answer')]";
    private String registerButton = "//button[@id='registerButton']";
    private String accountButton = "//button[@id='navbarAccount']";
    private String loginNavButton = "//button[@id='navbarLoginButton']";

    String email;
    String password;

    @BeforeMethod
    public void openSignUpPage() {
        Faker faker = new Faker();
        driver.findElement(By.xpath(accountButton)).click();
        driver.findElement(By.xpath(loginNavButton)).click();
        driver.findElement(By.xpath("//a[@href='#/register']")).click();
        email = faker.name().username() + "@gmail.com";
        password = faker.code().ean8();
    }

    @AfterMethod
    public void refreshPage()  {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
        driver.navigate().refresh();
    }

    @Test
    public void customerCanRegisterTest(){
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
        String message =  "//span[contains(.,'Language has been changed to English')]";
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(message)));
        driver.findElement(By.xpath(registerButton)).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h1"), "Login"));
        String title = driver.findElement(By.xpath("//h1")).getText();;

        String registerSuccessMessage = driver.findElement(By.xpath("//span[contains(.,'Registration completed')]")).getText();
        softAssert.assertEquals(title, "Login", "The page title is " + title);
        softAssert.assertEquals(registerSuccessMessage,
                "Registration completed successfully. You can now log in.", "User is already exists and not unique");
    }

    @Test
    public void customerCanLoginAfterRegisterTest() {
        driver.findElement(By.xpath(emailField)).sendKeys(email);
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys(password);
        driver.findElement(By.xpath(securityQuestionPicklist)).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//span[contains(.,'Your eldest siblings middle name?')]")));;

        driver.findElement(By.xpath(answerField)).sendKeys("2468");
        String message =  "//span[contains(.,'Language has been changed to English')]";
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(message)));
        driver.findElement(By.xpath(registerButton)).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h1"), "Login"));
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[contains(@class , 'heading')]"), "All Products"));
        driver.findElement(By.xpath(accountButton)).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//button[@aria-label='Go to user profile']/span"), email));
        Assert.assertEquals(driver.findElement(By.xpath("//button[@aria-label='Go to user profile']/span")).getText(), email);
    }
}
