package com.ct.tests.test;

import com.ct.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FirstTest extends BaseTest {

    @Test
    public void shouldAnswerWithTrue() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "OWASP Juice Shop");
    }

    @Test
    public void checkRandomValue() {
        int randomInt = (int) (Math.random() * 10);
        Assert.assertTrue(randomInt > 1 && randomInt <= 10, "Not in the range from 1 to 10");
    }

    @DataProvider(name = "testWords")
    public Object[][] dataForPrinting() {
        return new Object[][] { { "Hello", "Automation!" },
                { "Test", "Data Provider" }, {"Hello", "World"} };
    }

    @Test(dataProvider = "testWords")
    public void checkPrintingWords(String word1, String word2) {
        System.out.println("Printing words from Data Provider: " + word1 + " " + word2);
    }

    @DataProvider(name = "testNumbers")
    public Object[][] dataProviderNumbers() {
        return new Object[][] { { 2 }, { 10 }, {8} };
    }

    @Test(dataProvider = "testNumbers")
    public void checkPrintingWords(int number) {
        System.out.println("Number is: " + number);
        Assert.assertTrue(number > 1 && number <= 10,  number + " is NOT in the range from 1 to 10");
    }
}


