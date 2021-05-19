package com.ct.first.test;

import com.ct.first.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest extends BaseTest {

    public String baseUrl = "https://www.youtube.com/";

    @Test
    public void shouldAnswerWithTrue() {
        driver.get(baseUrl);
        String title = driver.getTitle();
        Assert.assertEquals(title, "YouTube");
    }

    @Test
    public void checkRandomValue() {
        int randomInt = (int) (Math.random() * 10);
        Assert.assertTrue(randomInt > 1 && randomInt < 10, "Not in the range from 1 to 10");
    }
}

