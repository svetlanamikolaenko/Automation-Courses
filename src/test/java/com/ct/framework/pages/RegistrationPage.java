package com.ct.framework.pages;

import com.ct.framework.helpers.JavaScriptHelper;
import com.ct.model.Customer;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
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
        setCookies();
    }

    @Step("Register As Customer")
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

    @Step("Populate Empty Email")
    public RegistrationPage enterEmptyEmail() {
        emailField.clear();
        emailField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Get Error Message Under Email Field")
    public String getEmailErrorMessage() {
        waitElementUntilVisible(errorMessageEmail);
        return errorMessageEmail.getText();
    }

    @Step("Get Error Message Under Password Field")
    public String getPasswordErrorMessage() {
        waitElementUntilVisible(errorMessagePassword);
        return errorMessagePassword.getText();
    }

    @Step("Get Error Message Under Repeat Password Field")
    public String getRepeatPasswordErrorMessage() {
        waitElementUntilVisible(errorMessageRepeatPassword);
        return errorMessageRepeatPassword.getText();
    }

    @Step("Populate Not Valid Email")
    public RegistrationPage enterNotValidEmail() {
        emailField.click();
        emailField.sendKeys(faker.name().name());
        emailField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Populate Empty Password")
    public RegistrationPage enterEmptyPassword() {
        passwordField.clear();
        passwordField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Populate Empty Repeat Password")
    public RegistrationPage enterEmptyRepeatPassword() {
        passwordRepeatField.clear();
        passwordRepeatField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Populate Too Short Password")
    public RegistrationPage enterShortPassword() {
        passwordField.click();
        passwordField.sendKeys("pass");
        passwordField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Fill In Report Password Not Corresponding To Password")
    public RegistrationPage enterAnyRepeatPassword() {
        passwordRepeatField.click();
        passwordRepeatField.sendKeys("pass");
        passwordRepeatField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Populate Empty Security Question")
    public RegistrationPage selectEmptySecurityQuestion() {
        waitElementUntilVisible(securityQuestionPicklist);
        actions.click(securityQuestionPicklist)
                .moveToElement(emailField)
                .click(emailField)
                .build()
                .perform();
        return this;
    }

    @Step("Get Error Message Under Security Question Field")
    public String getSecurityQuestionErrorMessage() {
        waitElementUntilVisible(errorMessageSecurityQuestion);
        return errorMessageSecurityQuestion.getText();
    }

    @Step("Populate Empty Answer")
    public RegistrationPage enterEmptyAnswer() {
        answerField.clear();
        answerField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Get Error Message Under Answer Field")
    public String getAnswerErrorMessage() {
        waitElementUntilVisible(errorMessageAnswer);
        return errorMessageAnswer.getText();
    }

    @Step("Get Page Caption")
    public String getCaption() {
        waitElementUntilVisible(caption);
        return caption.getText();
    }

    @Step("Get Error Message If User Already Exists")
    public String getErrorUniqueUser() {
        waitUntilTextToBePresent(errorUniqueUser, emailMustBeUniqueText);
        return errorUniqueUser.getText();
    }

    @Step("Click On Account Button")
    public void clickOnAccountButton() {
        accountButton.click();
    }

    @Step("Get Success Message If Customer Registration Completed Successfully")
    public String getRegistrationCompletedSuccessMessage() {
        return registrationCompletedSuccessMessage;
    }

    @Step("Click On Select Question Drop Down")
    public RegistrationPage clickOnSelectQuestionDropDown() {
        javaScriptHelper.clickJS(securityQuestionPicklist);
        return this;
    }

    @Step("Get Success Message After Registration")
    public String getRegisterSuccessMessage() {
        waitUntilTextToBePresent(registrationSuccessMessage, registrationCompletedSuccessMessage);
        return registrationSuccessMessage.getText();
    }

    @Step("Click On Register Button")
    public void clickOnRegisterButton() {
        waitElementUntilVisible(registerButton);
        javaScriptHelper.clickJS(registerButton);
    }

    @Step("Enter Any Answer")
    public void enterAnswer() {
        answerField.clear();
        answerField.sendKeys(faker.code().ean8());
    }

    @Step("Choose Security Question")
    public void chooseSecurityQuestion() {
        javaScriptHelper.clickJS(securityQuestion);
    }

    public void repeatPassword(String password) {
        passwordRepeatField.clear();
        passwordRepeatField.sendKeys(password);
    }

    @Step("Enter Password")
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    @Step("Enter Email")
    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }
}
