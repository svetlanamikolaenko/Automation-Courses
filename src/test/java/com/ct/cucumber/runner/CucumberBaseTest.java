package com.ct.cucumber.runner;


import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = "pretty",
        monochrome = true,
        tags = "smoke",
        glue = "io.ct.cucumber",
        features = "classpath:io/ct/features"
)
public class CucumberBaseTest {


}
