package com.ct.framework.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources("classpath:config.properties")
public interface TestConfig extends Config {
    TestConfig CONFIG = ConfigFactory.create(TestConfig.class);

    @DefaultValue("browser")
    String browser();

    @Key("base.url")
    String baseUrl();

    @Key("remote")
    boolean remote();

    @Key("selenium.server.url")
    String seleniumServerUrl();

    @Key("user.email")
    String userEmail();

    @Key("user.password")
    String userPassword();
}
