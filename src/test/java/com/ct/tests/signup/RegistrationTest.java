package com.ct.tests.signup;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class RegistrationTest extends BaseTest  {
    private String emailField = "//input[contains(@aria-label,'Email')]";
    private String passwordField = "//input[@aria-label='Field for the password']";
    private String passwordRepeatField = "//input[@aria-label='Field to confirm the password']";
    private String securityQuestionPicklist = "//*[@name='securityQuestion']";
    private String answerField = "//input[contains(@data-placeholder,'Answer')]";
    private String registerButton = "//button[@id='registerButton']/span[@class = 'mat-button-wrapper']";
    private String errorUniqueUser = "//div[@class = 'error']";

    String email = "svitlana15@gmail.com";
    String existedEmail = "svitlana8@gmail.com";
    String password = "passw0rd";

    @BeforeMethod
    public void openSignUpPage() {
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
        softAssert.assertAll();
    }

    @Test
    public void emailMustBeUniqueErrorTest() throws InterruptedException {
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(existedEmail);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(passwordRepeatField)).clear();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys(password);
        driver.findElement(By.xpath(securityQuestionPicklist)).click();
        Thread.sleep(5000);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//span[contains(.,\"What's your favorite place to go hiking?\")]")));

        driver.findElement(By.xpath(answerField)).clear();
        driver.findElement(By.xpath(answerField)).sendKeys("any text");
        Thread.sleep(2000);
        driver.findElement(By.xpath(registerButton)).click();
        Thread.sleep(5000);
        Assert.assertEquals(driver.findElement(By.xpath(errorUniqueUser)).getText(),
                "Email must be unique", "Email is unique. User not exists!");
    }

    @Test
    public void checkErrorMessagesUnderEmailFieldTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(Keys.TAB);
        String error = "//input[contains(@aria-label,'Email')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        Thread.sleep(2000);
        softAssert.assertEquals(driver.findElement(By.xpath(error)).getText(), "Please provide an email address.");
        driver.findElement(By.xpath(emailField)).click();
        driver.findElement(By.xpath(emailField)).sendKeys("abc");
        driver.findElement(By.xpath(emailField)).sendKeys(Keys.TAB);
        Thread.sleep(2000);
        softAssert.assertEquals(driver.findElement(By.xpath(error)).getText(), "Email address is not valid.");
        softAssert.assertAll();
    }

    @Test
    public void checkErrorMessagesUnderPasswordFieldTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(Keys.TAB);
        Thread.sleep(2000);
        String errorPassword = "//input[contains(@aria-label,'password')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        softAssert.assertEquals(driver.findElement(By.xpath(errorPassword)).getText(), "Please provide a password.");
        driver.findElement(By.xpath(passwordField)).click();
        driver.findElement(By.xpath(passwordField)).sendKeys("pass");
        driver.findElement(By.xpath(passwordField)).sendKeys(Keys.TAB);
        Thread.sleep(2000);
        softAssert.assertEquals(driver.findElement(By.xpath(errorPassword)).getText(), "Password must be 5-20 characters long.");
        softAssert.assertAll();
    }

    @Test
    public void checkErrorMessagesUnderRepeatPasswordFieldTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(passwordRepeatField)).clear();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys(Keys.TAB);
        Thread.sleep(2000);
        String errorPassword = "//input[contains(@aria-label,'Field to confirm the password')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        softAssert.assertEquals(driver.findElement(By.xpath(errorPassword)).getText(), "Please repeat your password.");
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(passwordRepeatField)).click();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys("pass");
        driver.findElement(By.xpath(passwordField)).sendKeys(Keys.TAB);
        Thread.sleep(2000);
        softAssert.assertEquals(driver.findElement(By.xpath(errorPassword)).getText(), "Passwords do not match");
        softAssert.assertAll();
    }
    @Test
    public void checkErrorMessageUnderSecurityQuestionFieldTest() throws InterruptedException {
        driver.findElement(By.xpath(securityQuestionPicklist)).click();
        driver.findElement(By.xpath(securityQuestionPicklist)).sendKeys(Keys.ESCAPE);
        Thread.sleep(2000);
        String errorMessage = "//*[@name='securityQuestion']/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        Assert.assertEquals(driver.findElement(By.xpath(errorMessage)).getText(), "Please select a security question.");
    }

    @Test
    public void checkErrorMessagesUnderAnswerFieldTest() throws InterruptedException {
        driver.findElement(By.xpath(answerField)).clear();
        driver.findElement(By.xpath(answerField)).sendKeys(Keys.TAB);
        Thread.sleep(2000);
        String errorMessage = "//input[contains(@data-placeholder,'Answer')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        Assert.assertEquals(driver.findElement(By.xpath(errorMessage)).getText(), "Please provide an answer to your security question.");
    }
}
