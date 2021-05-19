package com.ct.framework.driver;

import com.ct.framework.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class WebDriverRunner {
    private static WebDriver driver;

    private WebDriverRunner() {
    }

    public static WebDriver getWebDriver() {
        if (driver == null) {
            switch (TestConfig.CONFIG.browser()){
                case "firefox":{
                    WebDriverManager.firefoxdriver().setup();
                    driver = new EdgeDriver();
                    break;
                }
                case "edge": {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                }
                default: {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                }
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeWebDriver(){
        if (driver != null){
            driver.close();
        }
    }
}
