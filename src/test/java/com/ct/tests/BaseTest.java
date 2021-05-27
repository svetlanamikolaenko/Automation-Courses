package com.ct.tests;

import com.ct.framework.driver.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
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

    @BeforeClass
    public void openSite() throws InterruptedException {
        driver.get("http://beeb0b73705f.sn.mynetname.net:3000/#/");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@aria-label='Close Welcome Banner']")).click();
    }
}
