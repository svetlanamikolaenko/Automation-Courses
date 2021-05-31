package com.ct.tests.signup;

import com.ct.tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class RegistrationFieldsErrorsTest extends BaseTest {
    private String emailField = "//input[contains(@aria-label,'Email')]";
    private String passwordField = "//input[@aria-label='Field for the password']";
    private String passwordRepeatField = "//input[@aria-label='Field to confirm the password']";
    private String securityQuestionPicklist = "//*[@name='securityQuestion']";
    private String answerField = "//input[contains(@data-placeholder,'Answer')]";
    private String registerButton = "//button[@id='registerButton']";
    private String errorUniqueUser = "//div[@class = 'error']";
    private String accountButton = "//button[@id='navbarAccount']";
    private String loginNavButton = "//button[@id='navbarLoginButton']";

    String existingEmail;
    String password;

    @BeforeMethod
    public void openSignUpPage() {
        driver.findElement(By.xpath(accountButton)).click();
        driver.findElement(By.xpath(loginNavButton)).click();
        driver.findElement(By.xpath("//a[@href='#/register']")).click();
        existingEmail = "svitlana8@gmail.com";
        password = "passw0rd";
    }

    @Test
    public void signUpPageIsOpenTest() {
        String title = driver.findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(title, "User Registration");
    }

    @Test
    public void emailMustBeUniqueErrorTest() {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(existingEmail);
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(passwordRepeatField)).clear();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys(password);
        driver.findElement(By.xpath(securityQuestionPicklist)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,\"What's your favorite place to go hiking?\")]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[contains(.,\"What's your favorite place to go hiking?\")]")));

        driver.findElement(By.xpath(answerField)).clear();
        driver.findElement(By.xpath(answerField)).sendKeys("any text");
        String message =  "//span[contains(.,'Language has been changed to English')]";
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(message)));
        softAssert.assertTrue(driver.findElement(By.xpath(registerButton)).isEnabled());
        driver.findElement(By.xpath(registerButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorUniqueUser)));
        softAssert.assertEquals(driver.findElement(By.xpath(errorUniqueUser)).getText(),
                "Email must be unique", "Email is unique. User not exists!");
    }

    @Test
    public void checkErrorMessagesUnderEmailFieldTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(Keys.TAB);
        String error = "//input[contains(@aria-label,'Email')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(error)));
        softAssert.assertEquals(driver.findElement(By.xpath(error)).getText(), "Please provide an email address.");
        driver.findElement(By.xpath(emailField)).click();
        driver.findElement(By.xpath(emailField)).sendKeys("abc");
        driver.findElement(By.xpath(emailField)).sendKeys(Keys.TAB);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(error)));
        softAssert.assertEquals(driver.findElement(By.xpath(error)).getText(), "Email address is not valid.");
        softAssert.assertAll();
    }

    @Test
    public void checkErrorMessagesUnderPasswordFieldTest() {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(Keys.TAB);
        String errorPassword = "//input[contains(@aria-label,'password')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorPassword)));
        softAssert.assertEquals(driver.findElement(By.xpath(errorPassword)).getText(), "Please provide a password.");
        driver.findElement(By.xpath(passwordField)).click();
        driver.findElement(By.xpath(passwordField)).sendKeys("pass");
        driver.findElement(By.xpath(passwordField)).sendKeys(Keys.TAB);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorPassword)));
        softAssert.assertEquals(driver.findElement(By.xpath(errorPassword)).getText(), "Password must be 5-20 characters long.");
        softAssert.assertAll();
    }

    @Test
    public void checkErrorMessagesUnderRepeatPasswordFieldTest() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(By.xpath(passwordRepeatField)).clear();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys(Keys.TAB);

        String errorPassword = "//input[contains(@aria-label,'Field to confirm the password')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorPassword)));
        softAssert.assertEquals(driver.findElement(By.xpath(errorPassword)).getText(), "Please repeat your password.");
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
        driver.findElement(By.xpath(passwordRepeatField)).click();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys("pass");
        driver.findElement(By.xpath(passwordField)).sendKeys(Keys.TAB);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorPassword)));
        softAssert.assertEquals(driver.findElement(By.xpath(errorPassword)).getText(), "Passwords do not match");
        softAssert.assertAll();
    }

    @Test
    public void checkErrorMessageUnderSecurityQuestionFieldTest() {
        driver.findElement(By.xpath(securityQuestionPicklist)).click();
        driver.findElement(By.xpath(securityQuestionPicklist)).sendKeys(Keys.ESCAPE);
        String errorMessage = "//*[@name='securityQuestion']/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorMessage)));
        Assert.assertEquals(driver.findElement(By.xpath(errorMessage)).getText(), "Please select a security question.");
    }

    @Test
    public void checkErrorMessagesUnderAnswerFieldTest() {
        driver.findElement(By.xpath(answerField)).clear();
        driver.findElement(By.xpath(answerField)).sendKeys(Keys.TAB);
        String errorMessage = "//input[contains(@data-placeholder,'Answer')]/ancestor::div[contains(@class, 'mat-form')]//mat-error";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorMessage)));
        Assert.assertEquals(driver.findElement(By.xpath(errorMessage)).getText(), "Please provide an answer to your security question.");
    }
}
