package com.ct.framework.pages;

import com.ct.model.Customer;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractPage {
    WebDriverWait wait;

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


    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, TIME_OUT);
        PageFactory.initElements(driver, this);
    }

    public void openPage() {
        driver.get(BASE_PAGE + "/login");
    }

    public void setCookies() {
        driver.manage().addCookie(new Cookie("welcomebanner_status", "dismiss"));
        driver.manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        driver.navigate().refresh();
    }

    public void navigateToLoginPage() {
        clickOnAccountButton();
        loginNavButton.click();
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

    public void clickOnAccountButton() {
        accountButton.click();
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

    public void loginAs(Customer customer) {
        enterEmail(customer.getEmail());
        enterPassword(customer.getPassword());
        clickOnLoginButton();
        clickOnAccountButton();
    }
}
