package com.ct.pages;

public class LoginPage {

    private String emailFieldCss =  "[name=email]";
    private String emailFieldXpath = "//input[@name='email']";

    private String passwordFieldCss =  "[name=password]";
    private String passwordFieldXpath = "//input[@name='password']";

    private String loginButtonCss = "[type='submit'] span.mat-button-focus-overlay";
    private String loginButtonXpath = "//button[@type='submit']/span[contains(@class, 'button-focus')]";

    private String forgotPasswordLinkCss = "[href='#/forgot-password']";
    private String forgotPasswordLinkXpath = "//a[@href='#/forgot-password']";

    private String registerPageLinkCss = "[href='#/register']";
    private String registerLinkPageXpath = "//a[@href='#/register']";

    private String rememberMeCheckboxCss = "[type=checkbox]";
    private String rememberMeCheckboxXpath = "//input[@type='checkbox']";


}
