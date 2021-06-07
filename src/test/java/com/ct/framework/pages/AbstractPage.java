package com.ct.framework.pages;

import com.ct.framework.config.TestConfig;
import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {
    protected WebDriver driver;
    WebDriverWait wait;
    protected final int TIME_OUT = 10;
    protected final String BASE_PAGE = TestConfig.CONFIG.baseUrl();
    Faker faker;


    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickJS(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                element);
    }

    public void waitUntilVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void waitUntilTextToBePresent(WebElement element, String text){
        wait.until(ExpectedConditions.textToBePresentInElement(element,text));
    }

    public abstract void openPage();
}
