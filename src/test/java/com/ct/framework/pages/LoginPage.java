package com.ct.framework.pages;

import com.ct.framework.helpers.JavaScriptHelper;
import com.ct.model.Customer;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractPage {

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@id='loginButton']")
    private WebElement loginButton;

    @FindBy(xpath = "//button[@id='navbarAccount']")
    private WebElement accountButton;

    @FindBy(xpath = "//button[@id='navbarLoginButton']")
    private WebElement loginNavButton;

    @FindBy(xpath = "//button[@aria-label='Go to user profile']/span")
    private WebElement userProfileButton;

    @FindBy(xpath = "//div[contains(@class,'heading')]")
    private WebElement heading;

    @FindBy(xpath = "//h1")
    private WebElement caption;

    @FindBy(xpath  = "//div[contains(@class, 'error')]")
    private WebElement errorInvalidEmailPasswordOrEmail;

    @FindBy(xpath = "//mat-error")
    private WebElement errorUnderPasswordField;

    private String errorMessageInvalidCred = "Invalid email or password.";
    private String errorMessageUnderPasswordField = "Please provide a password.";
    private final String loginPageCaption = "Login";
    private final String profilePageHeading = "All Products";

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, TIME_OUT);
        PageFactory.initElements(driver, this);
        javaScriptHelper = new JavaScriptHelper(driver);
    }

    @Override
    public void openPage() {
        driver.get(BASE_PAGE + "/login");
        setCookies();
    }

    @Step
    public LoginPage logout(){
        javaScriptHelper.clearLocalStorageJS();
        return this;
    }

    @Step("Navigate to Login Page")
    public LoginPage navigateToLoginPage() {
        clickOnAccountButton();
        loginNavButton.click();
        return this;
    }

    @Step("Login Button Is Enabled")
    public boolean loginButtonIsEnabled(){
        return loginButton.isEnabled();
    }

    @Step("Get Error Message Under Password Field")
    public String getErrorUnderPasswordField(){
        waitUntilTextToBePresent(errorUnderPasswordField, errorMessageUnderPasswordField);
        return errorUnderPasswordField.getText();
    }

    @Step("Get Error Message Under Invalid Email Or Password On Login Form")
    public String getErrorInvalidEmailOrPassword(){
        waitUntilTextToBePresent(errorInvalidEmailPasswordOrEmail, errorMessageInvalidCred);
        return errorInvalidEmailPasswordOrEmail.getText();
    }

    @Step("Get Actual Account Name")
    public String getActualAccountName(String currentEmail) {
        waitUntilTextToBePresent(userProfileButton, currentEmail);
        String actualAccountName = userProfileButton.getText();
        return actualAccountName;
    }

    @Step("Get Login Form Caption")
    public String getPageCaption() {
         waitUntilTextToBePresent(caption, loginPageCaption );
         return caption.getText();
    }

    @Step("Enter Empty Password")
    public LoginPage enterEmptyPassword() {
        passwordField.clear();
        passwordField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Click On Account Button")
    public LoginPage clickOnAccountButton() {
        accountButton.click();
        return this;
    }

    @Step("Click On Login Button")
    public void clickOnLoginButton() {
        loginButton.click();
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

    @Step("Login as customer")
    public LoginPage loginAs(Customer customer) {
        waitUntilTextToBePresent(caption, caption.getText());
        enterEmail(customer.getEmail());
        enterPassword(customer.getPassword());
        clickOnLoginButton();
        return this;
    }
}
