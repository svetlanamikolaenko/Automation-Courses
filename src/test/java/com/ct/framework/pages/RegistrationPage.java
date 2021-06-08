package com.ct.framework.pages;

import com.ct.framework.helpers.JavaScriptHelper;
import com.ct.model.Customer;
import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends AbstractPage {

    @FindBy(xpath = "//input[contains(@aria-label,'Email')]")
    private WebElement emailField;

    @FindBy(xpath = "//input[@aria-label='Field for the password']")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@aria-label='Field to confirm the password']")
    private WebElement passwordRepeatField;

    @FindBy(xpath = "//*[@name='securityQuestion']")
    private WebElement securityQuestionPicklist;

    @FindBy(xpath = "//input[contains(@data-placeholder,'Answer')]")
    private WebElement answerField;

    @FindBy(xpath = "//button[@id='registerButton']")
    private WebElement registerButton;

    @FindBy(xpath = "//button[@id='navbarAccount']")
    private WebElement accountButton;

    @FindBy(xpath = "//button[@id='navbarLoginButton']")
    private WebElement loginNavButton;

    @FindBy(xpath = "//span[contains(.,'Registration completed')]")
    private WebElement registrationSuccessMessage;

    @FindBy(xpath = "//h1")
    private WebElement caption;

    @FindBy(xpath = "//span[contains(.,'Your eldest siblings middle name?')]")
    private WebElement securityQuestion;

    @FindBy(xpath = "//div[contains(@class, 'error')]")
    private WebElement errorUniqueUser;

    @FindBy(xpath = "//input[contains(@aria-label,'Email')]/ancestor::div[contains(@class, 'mat-form')]//mat-error")
    private WebElement errorMessageEmail;

    @FindBy(xpath = "//input[contains(@aria-label,'password')]/ancestor::div[contains(@class, 'mat-form')]//mat-error")
    private WebElement errorMessagePassword;

    @FindBy(xpath = "//input[contains(@aria-label,'Field to confirm the password')]/ancestor::div[contains(@class, 'mat-form')]//mat-error")
    private WebElement errorMessageRepeatPassword;

    @FindBy(xpath = "//*[@name='securityQuestion']/ancestor::div[contains(@class, 'mat-form')]//mat-error")
    private WebElement errorMessageSecurityQuestion;

    @FindBy(xpath = "//input[contains(@data-placeholder,'Answer')]/ancestor::div[contains(@class, 'mat-form')]//mat-error")
    private WebElement errorMessageAnswer;

    final private String registrationCompletedSuccessMessage = "Registration completed successfully. You can now log in.";
    final private String emailMustBeUniqueText = "Email must be unique";

    public RegistrationPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, TIME_OUT);
        PageFactory.initElements(driver, this);
        faker = new Faker();
        javaScriptHelper = new JavaScriptHelper(driver);
        actions = new Actions(driver);
    }


    @Override
    public void openPage() {
        driver.get(BASE_PAGE + "/register");
    }

    public RegistrationPage registerAs(Customer customer) {
        enterEmail(customer.getEmail());
        enterPassword(customer.getPassword());
        repeatPassword(customer.getPassword());
        clickOnSelectQuestionDropDown();
        chooseSecurityQuestion();
        enterAnswer();
        clickOnRegisterButton();
        return this;
    }

    public RegistrationPage enterEmptyEmail() {
        emailField.clear();
        emailField.sendKeys(Keys.TAB);
        return this;
    }

    public String getEmailErrorMessage() {
        waitUntilVisible(errorMessageEmail);
        return errorMessageEmail.getText();
    }

    public String getPasswordErrorMessage() {
        waitUntilVisible(errorMessagePassword);
        return errorMessagePassword.getText();
    }

    public String getRepeatPasswordErrorMessage() {
        waitUntilVisible(errorMessageRepeatPassword);
        return errorMessageRepeatPassword.getText();
    }


    public RegistrationPage enterNotValidEmail() {
        emailField.click();
        emailField.sendKeys(faker.name().name());
        emailField.sendKeys(Keys.TAB);
        return this;
    }

    public RegistrationPage enterEmptyPassword() {
        passwordField.clear();
        passwordField.sendKeys(Keys.TAB);
        return this;
    }

    public RegistrationPage enterEmptyRepeatPassword() {
        passwordRepeatField.clear();
        passwordRepeatField.sendKeys(Keys.TAB);
        return this;
    }

    public RegistrationPage enterShortPassword() {
        passwordField.click();
        passwordField.sendKeys("pass");
        passwordField.sendKeys(Keys.TAB);
        return this;
    }

    public RegistrationPage enterAnyRepeatPassword() {
        passwordRepeatField.click();
        passwordRepeatField.sendKeys("pass");
        passwordRepeatField.sendKeys(Keys.TAB);
        return this;
    }

    public RegistrationPage selectEmptySecurityQuestion() {
        waitUntilVisible(securityQuestionPicklist);
        actions.click(securityQuestionPicklist)
                .moveToElement(emailField)
                .click(emailField)
                .build()
                .perform();
        return this;
    }

    public String getSecurityQuestionErrorMessage() {
        waitUntilVisible(errorMessageSecurityQuestion);
        return errorMessageSecurityQuestion.getText();
    }

    public RegistrationPage enterEmptyAnswer() {
        answerField.clear();
        answerField.sendKeys(Keys.TAB);
        return this;
    }

    public String getAnswerErrorMessage() {
        waitUntilVisible(errorMessageAnswer);
        return errorMessageAnswer.getText();
    }

    public String getCaption() {
        waitUntilVisible(caption);
        return caption.getText();
    }

    public String getErrorUniqueUser() {
        waitUntilTextToBePresent(errorUniqueUser, emailMustBeUniqueText);
        return errorUniqueUser.getText();
    }

    public void clickOnAccountButton() {
        accountButton.click();
    }

    public String getRegistrationCompletedSuccessMessage() {
        return registrationCompletedSuccessMessage;
    }

    public RegistrationPage clickOnSelectQuestionDropDown() {
        javaScriptHelper.clickJS(securityQuestionPicklist);
        return this;
    }

    public String getRegisterSuccessMessage() {
        waitUntilTextToBePresent(registrationSuccessMessage, registrationCompletedSuccessMessage);
        return registrationSuccessMessage.getText();
    }

    public void clickOnRegisterButton() {
        waitUntilVisible(registerButton);
        javaScriptHelper.clickJS(registerButton);
    }

    public void enterAnswer() {
        answerField.clear();
        answerField.sendKeys(faker.code().ean8());
    }

    public void chooseSecurityQuestion() {
        javaScriptHelper.clickJS(securityQuestion);
    }

    public void repeatPassword(String password) {
        passwordRepeatField.clear();
        passwordRepeatField.sendKeys(password);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }
}
