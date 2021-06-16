package com.ct.cucumber.hooks;

import com.ct.framework.driver.WebDriverRunner;
import com.ct.framework.helpers.JavaScriptHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebDriverHooks {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavaScriptHelper javaScriptHelper;

    @Before
    public void setupDriver() {
        driver = WebDriverRunner.getWebDriver();
        wait = new WebDriverWait(driver, 10);
        javaScriptHelper = new JavaScriptHelper(driver);
    }

    @After
    public void tearDown() {
        javaScriptHelper.clearLocalStorageJS();
        WebDriverRunner.closeWebDriver();
    }
}

