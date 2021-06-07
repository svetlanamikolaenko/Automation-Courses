package com.ct.framework.pages;

import com.ct.model.Customer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    @FindBy(xpath = "//div[contains(@class , 'heading')]")
    private WebElement heading;

    @FindBy(xpath = "//h1")
    private WebElement caption;

    private final String loginPageCaption = "Login";

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, TIME_OUT);
        PageFactory.initElements(driver, this);
    }

    public void openPage() {
        driver.get(BASE_PAGE + "/login");
    }

    public LoginPage navigateToLoginPage() {
        clickOnAccountButton();
        loginNavButton.click();
        return this;
    }

    public String getActualAccountName(String currentEmail) {
        wait.until(ExpectedConditions.textToBePresentInElement(userProfileButton, currentEmail));
        String actualAccountName = userProfileButton.getText();
        return actualAccountName;
    }

    public String getActualHeading() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        String actualHeading = heading.getText();
        return actualHeading;
    }

    public String getPageCaption() {
         return caption.getText();
    }

    public LoginPage clickOnAccountButton() {
        accountButton.click();
        return this;
    }

    public void clickOnLoginButton() {
        loginButton.click();
        wait.until(ExpectedConditions.visibilityOf(heading));
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public LoginPage loginAs(Customer customer) {
        waitUntilTextToBePresent(caption, caption.getText());
        enterEmail(customer.getEmail());
        enterPassword(customer.getPassword());
        clickOnLoginButton();
        return this;
    }
}
