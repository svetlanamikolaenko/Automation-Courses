package com.ct.tests;

import com.ct.framework.config.TestConfig;
import com.ct.framework.driver.WebDriverRunner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String BASE_PAGE = TestConfig.CONFIG.baseUrl();

    @BeforeClass
    public void openSite(){
        System.out.println("Setting driver before class running");
        driver = WebDriverRunner.getWebDriver();
        driver.get(BASE_PAGE);
        wait = new WebDriverWait(driver, 10);
        driver.manage().addCookie(new Cookie("welcomebanner_status", "dismiss"));
        driver.manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        driver.navigate().refresh();
    }

    @AfterClass
    public void closeSite(){
        System.out.println("Closing driver after class");
        WebDriverRunner.closeWebDriver();
    }
}
