package com.ct.framework.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.ct.framework.driver.WebDriverRunner.getWebDriver;

public class AllureListener extends AllureTestNg {

    @Override
    public void onTestFailure(ITestResult result) {
        File screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
        try {
            Allure.addAttachment(screenshot.getName(), new FileInputStream(screenshot));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        super.onTestFailure(result);
    }

//    @Override
//    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
//        if (!testResult.isSuccess()) {
//            File screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
//            try {
//                Allure.addAttachment(screenshot.getName(), new FileInputStream(screenshot));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
