package com.ct.tests;

import com.ct.framework.config.TestConfig;
import com.ct.framework.driver.WebDriverRunner;
import com.ct.framework.helpers.JavaScriptHelper;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String BASE_PAGE = TestConfig.CONFIG.baseUrl();
    protected JavaScriptHelper javaScriptHelper;

    @BeforeClass
    public void setCookies() {
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
