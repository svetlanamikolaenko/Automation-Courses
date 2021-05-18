package com.ct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

public class AppTest {

    @Test
    public void shouldAnswerWithTrue() {
        //System.setProperty("webdriver.chrome.driver", "C:/Program Files/WebDrivers/chromedriver.exe");
        System.setProperty("webdriver.edge.driver", "C:/Program Files/WebDrivers/msedgedriver.exe");

        WebDriver driver = new EdgeDriver();
        driver.get("https://google.com");
        driver.close();
    }
}
