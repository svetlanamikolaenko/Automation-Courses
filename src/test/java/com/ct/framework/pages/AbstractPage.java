package com.ct.framework.pages;

import com.ct.framework.config.TestConfig;
import org.openqa.selenium.WebDriver;

public abstract class AbstractPage {
    protected WebDriver driver;
    protected final int TIME_OUT = 10;
    protected final String BASE_PAGE = TestConfig.CONFIG.baseUrl();

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public abstract void openPage();
}
