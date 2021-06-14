package com.ct.tests;

import com.ct.framework.driver.WebDriverRunner;
import com.ct.framework.helpers.JavaScriptHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavaScriptHelper javaScriptHelper;

    @BeforeClass
    public void setupDriver() {
        driver = WebDriverRunner.getWebDriver();
        wait = new WebDriverWait(driver, 10);
        javaScriptHelper = new JavaScriptHelper(driver);
    }

    @AfterClass
    public void closeSite() {
        javaScriptHelper.clearLocalStorageJS();
        WebDriverRunner.closeWebDriver();
    }
}
