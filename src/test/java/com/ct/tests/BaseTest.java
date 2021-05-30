package com.ct.tests;

import com.ct.framework.driver.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

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
    public void openSite() {
        wait = new WebDriverWait(driver, 5);
        driver.get("http://beeb0b73705f.sn.mynetname.net:3000/#/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Close Welcome Banner']")));
        driver.findElement(By.xpath("//button[@aria-label='Close Welcome Banner']")).click();
    }
}
