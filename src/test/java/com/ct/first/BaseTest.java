package com.ct.first;

import com.ct.framework.driver.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected WebDriver driver;

    @BeforeSuite
    public void setup(){
        driver = WebDriverRunner.getWebDriver();
        System.out.println("Setting driver before suit running");
    }

    @AfterSuite
    public void tearDown(){
     WebDriverRunner.closeWebDriver();
     System.out.println("Closing driver after suit");
    }
}
