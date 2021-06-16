package com.ct.framework.helpers;

import com.ct.framework.driver.WebDriverRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptHelper {
    protected WebDriver driver;

    public JavaScriptHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void clickJS(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                element);
    }
    public void allElementsLoaded(){
        ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

    public void scrollIntoView(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)",
                element);
    }

    public void clearLocalStorageJS(){
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
    }



}
