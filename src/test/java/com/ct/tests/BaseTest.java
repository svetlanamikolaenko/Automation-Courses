package com.ct.tests;

import com.ct.framework.config.TestConfig;
import com.ct.framework.driver.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static com.ct.framework.driver.WebDriverRunner.getWebDriver;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String BASE_PAGE = TestConfig.CONFIG.baseUrl();

    @BeforeSuite
    public void setup(){
        System.out.println("Setting driver before suit running");
        driver = getWebDriver();
    }

    @AfterSuite
    public void tearDown(){
     WebDriverRunner.closeWebDriver();
     System.out.println("Closing driver after suit");
    }

    @BeforeClass
    public void openSite(){
        driver.get(BASE_PAGE);
        wait = new WebDriverWait(driver, 10);
        driver.manage().addCookie(new Cookie("welcomebanner_status", "dismiss"));
        driver.manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        driver.navigate().refresh();
    }
}
