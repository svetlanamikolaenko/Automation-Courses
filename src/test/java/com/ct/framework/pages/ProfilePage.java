package com.ct.framework.pages;

import org.openqa.selenium.WebDriver;

public class ProfilePage {
    private String emailFieldCss =  "[name=email]";
    private String emailFieldXpath = "//input[@name='email']";

    private String userNameFieldCss =  "[name=username]";
    private String userNameFieldXpath = "//input[@name='username']";

    private String setUserNameButtonCss = "[type=submit][aria-label*='username']";
    private String setUserNameButtonXpath = "//button[contains(.,'Set Username')]";

    private String chooseFileButtonCss = "[type=file]";
    private String chooseFileButtonXpath = "//input[@type='file']";

    private String uploadPictureButtonCss = "[type=submit][aria-label*='picture']";
    private String uploadPictureButtonXpath = "//button[contains(.,'Upload Picture')]";

    private String imageUrlFieldCss = "[name=imageUrl]";
    private String imageUrlFieldXpath = "//input[@name='imageUrl']";

    private String linkImageButtonCss = "[type=submit][aria-label*='image']";
    private String linkImageButtonXpath = "//button[contains(.,'Link Image')]";

}
