package com.ct.framework.driver;

import com.ct.framework.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverRunner {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private WebDriverRunner() {
    }
    public static WebDriver getWebDriver() {
        if (driver.get() == null) {
            switch (TestConfig.CONFIG.browser()){
                case "firefox":{
                    if(TestConfig.CONFIG.remote()){
                        try {
                            FirefoxOptions firefoxOptions = new FirefoxOptions();
                            firefoxOptions.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
                            driver.set(new RemoteWebDriver(new URL(TestConfig.CONFIG.seleniumServerUrl()),
                                    firefoxOptions));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        WebDriverManager.firefoxdriver().setup();
                        driver.set(new FirefoxDriver());
                        break;
                    }
                }
                case "edge": {
                    if(TestConfig.CONFIG.remote()) {
                        try {
                            EdgeOptions edgeOptions = new EdgeOptions();
                            driver.set(new RemoteWebDriver(new URL(TestConfig.CONFIG.seleniumServerUrl()),
                                    edgeOptions));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        WebDriverManager.edgedriver().setup();
                        driver.set(new EdgeDriver());
                        break;
                    }
                }
                default: {
                    if(TestConfig.CONFIG.remote()){
                        try {
                            ChromeOptions options = new ChromeOptions();
                            driver.set(new RemoteWebDriver(new URL(TestConfig.CONFIG.seleniumServerUrl()),
                                    options));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        WebDriverManager.chromedriver().setup();
                        driver.set(new ChromeDriver());
                    }
                }
            }
            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    public static void closeWebDriver(){
        if (driver.get() != null){
            driver.get().quit();
            driver.remove();
        }
    }
}
