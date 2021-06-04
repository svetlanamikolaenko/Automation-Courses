package com.ct.framework.pages;

import com.ct.model.Customer;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends AbstractPage {
    private String emailField = "//input[contains(@aria-label,'Email')]";
    private String passwordField = "//input[@aria-label='Field for the password']";
    private String passwordRepeatField = "//input[@aria-label='Field to confirm the password']";
    private String securityQuestionPicklist = "//*[@name='securityQuestion']";
    private String answerField = "//input[contains(@data-placeholder,'Answer')]";
    private String registerButton = "//button[@id='registerButton']";
    private String accountButton = "//button[@id='navbarAccount']";
    private String loginNavButton = "//button[@id='navbarLoginButton']";
    private LoginPage LoginPage;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver,TIME_OUT);
    }

    @Override
    public void openPage() {
        driver.get(BASE_PAGE + "/register");
    }

    public void registerAs(Customer customer){
        enterEmail(customer.getEmail());
        enterPassword(customer.getPassword());
        repeatPassword(customer.getPassword());
        clickOnSelectQuestionDropDown();
        chooseSecurityQuestion();
        enterAnswer();
        clickOnRegisterButton();
    }

    public void clickOnAccountButton() {
        driver.findElement(By.xpath(accountButton)).click();
    }
    public void clickOnSelectQuestionDropDown() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                driver.findElement(By.xpath(securityQuestionPicklist)));
    }

    public String getRegisterSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Registration completed')]")));
        return driver.findElement(By.xpath("//span[contains(.,'Registration completed')]")).getText();
    }

    public void clickOnRegisterButton() {
        // String message = "//span[contains(.,'Language has been changed to English')]";
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(message)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(registerButton)));
    }

    public String getActualCaption() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h1"), "Login"));
        return driver.findElement(By.xpath("//h1")).getText();
    }

    public void enterAnswer() {
        driver.findElement(By.xpath(answerField)).clear();
        driver.findElement(By.xpath(answerField)).sendKeys("2468");
    }

    public void chooseSecurityQuestion() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//span[contains(.,'Your eldest siblings middle name?')]")));
    }

    public void repeatPassword(String password) {
        driver.findElement(By.xpath(passwordRepeatField)).clear();
        driver.findElement(By.xpath(passwordRepeatField)).sendKeys(password);
    }

    public void enterPassword(String password) {
        driver.findElement(By.xpath(passwordField)).clear();
        driver.findElement(By.xpath(passwordField)).sendKeys(password);
    }

    public void enterEmail(String email) {
        driver.findElement(By.xpath(emailField)).clear();
        driver.findElement(By.xpath(emailField)).sendKeys(email);
    }
}
