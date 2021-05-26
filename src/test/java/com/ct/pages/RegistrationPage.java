package com.ct.pages;

public class RegistrationPage {
    private String emailFieldCss =  "[aria-label^='Email']";
    private String emailFieldXpath = "//input[contains(@aria-label,'Email')]";

    private String passwordFieldCss =  "[aria-label='Field for the password']";
    private String passwordFieldXpath = "//input[@aria-label='Field for the password']";

    private String passwordRepeatFieldCss =  "[aria-label='Field to confirm the password']";
    private String passwordRepeatFieldXpath = "//input[@aria-label='Field to confirm the password']";


    private String showPasswordAdviceTumblerCss = "[type=checkbox]";
    private String showPasswordAdviceTumblerXpath = "//input[@type='checkbox']";

    private String securityQuestionPicklistCss = "[name=securityQuestion]";
    private String securityQuestionPicklistXpath = "//*[@name='securityQuestion']";

    private String answerFieldCss = "[data-placeholder^='Answer']";
    private String answerFieldCssXpath = "//input[contains(@data-placeholder,'Answer')]";

    private String registerButtonCss = "[type='submit'] span.mat-button-focus-overlay";
    private String registerButtonXpath = "//button[@type='submit']/span[contains(@class, 'button-focus')]";

    private String loginPageLinkCss = "[href='#/login']";
    private String loginPageLinkXpath = "//a[@href='#/login']";

}
