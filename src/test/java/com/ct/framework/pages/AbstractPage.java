package com.ct.framework.pages;

import com.ct.framework.config.TestConfig;
import com.ct.framework.helpers.JavaScriptHelper;
import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final int TIME_OUT = 10;
    protected final String BASE_PAGE = TestConfig.CONFIG.baseUrl();
    Faker faker;
    protected JavaScriptHelper javaScriptHelper;
    protected Actions actions;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setCookies() {
        driver.manage().addCookie(new Cookie("welcomebanner_status", "dismiss"));
        driver.manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        driver.navigate().refresh();
    }

    public void waitElementUntilVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilTextToBePresent(WebElement element, String text){
        wait.until(ExpectedConditions.textToBePresentInElement(element,text));
    }
    public void waitLocatorUntilVisible(String locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public WebElement findElementByXpath(String text){
        return driver.findElement(By.xpath(text));
    }

    public abstract void openPage();
}
