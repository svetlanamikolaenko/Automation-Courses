package com.ct.framework.pages;

import com.ct.framework.helpers.JavaScriptHelper;
import com.ct.model.Product;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage extends AbstractPage {
    @FindBy(xpath = "//div[contains(@class,'heading')]")
    private WebElement heading;

    @FindBy(xpath = "//button[@id='navbarAccount']")
    private WebElement accountButton;

    @FindBy(xpath = "//button[@id='navbarLoginButton']")
    private WebElement loginNavButton;

    @FindBy(xpath = "//button[@aria-label='Go to user profile']/span")
    private WebElement userProfileButton;

    @FindBy(xpath = "//mat-dialog-container")
    private WebElement card;

    @FindBy(xpath = "//p[@class = 'item-price']")
    private WebElement priceInCard;

    @FindBy(xpath = "//h1")
    private WebElement nameInCard;

    @FindBy(xpath = "//img[@alt='Banana Juice (1000ml)']")
    private WebElement imageInCard;

    @FindBy(xpath = "//br/preceding-sibling::div")
    private WebElement descriptionInCard;

    @FindBy(xpath = "//span[contains(.,'We are out of stock!')]")
    private WebElement outOfStockErrorMessage;

    @FindBy(xpath = "//mat-table")
    private WebElement productTable;

    @FindBy(xpath = "//button[@id='checkoutButton']")
    private WebElement checkoutButton;

    @FindBy(xpath = "//div[@id='price']")
    private WebElement totalPrice;

    @FindBy(xpath = "//mat-row")
    private WebElement productInTable;

    @FindBy(xpath = "//button[contains(@aria-label,'Show the shopping cart')]")
    private WebElement basketButton;

    @FindBy(xpath = "//button[@aria-label='Next page']")
    private WebElement nextPageButton;

    private String item = "//div[@class = 'item-name' and contains(., '%s')]";
    private String itemPrice = item + "/following-sibling::div[@class='item-price']";
    private String itemImage = item + "/../preceding-sibling::div/img";
    private String addToBasketMessage = "//span[contains(.,'%s')]";
    private String addToBasketButton = item + "//ancestor::figure//button[@aria-label='Add to Basket']";
    private String itemInBasket = "//mat-cell[contains(.,'%s')]";
    private String removeItemFromBasket = itemInBasket + "//following-sibling::mat-cell[last()]/button";
    private final String profilePageHeading = "All Products";

    public ProfilePage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, TIME_OUT);
        PageFactory.initElements(driver, this);
        javaScriptHelper = new JavaScriptHelper(driver);
    }

    @Override
    public void openPage() {
        driver.get(BASE_PAGE);
        setCookies();
    }

    @Step("Click on product item")
    public ProfilePage clickOnProductItem(String name) {
        waitLocatorUntilVisible(String.format(item, name));
        findElementByXpath((String.format(item, name))).click();
        return this;
    }

    @Step(" Add Apple Pomace To Basket")
    public String addApplePomaceToBasket(Product product){
        if(profilePageHeadingIsDisplayed() && itemNameIsDisplayed(product.getName())) {
            clickOnAddToBasketButton(product.getName());
            return getAddToBasketSuccessMessage(product.getName());
        }
        return "Can't find Apple Pomace";
    }

    @Step("Get Product Item Name In Basket")
    public String productInBasket(String name){
        clickOnBasketButton();
        if( productTableIsDisplayed() && productInTableIsPresent()){
            return getProductInBasket(name);
        }
        return "Apple Pomace is not in the basket!";
    }

    @Step("Get Product Item Image")
    public String getProductImage(String name) {
        return findElementByXpath(String.format(itemImage,name)).getAttribute("src");
    }

    @Step("Get Product Item Price")
    public double getProductPrice(String name) {
        String price = findElementByXpath(String.format(itemPrice, name)).getText();
        return Double.parseDouble(price.replaceAll("[^\\.0123456789]", ""));
    }

    @Step("Get Product Item Description In Card")
    public String getDescriptionInCard() {
        return descriptionInCard.getText();
    }

    @Step("Get Product Item Image In Card")
    public String getImageInCard() {
        return imageInCard.getAttribute("src");
    }

    @Step("Get Product Item Price In Card")
    public double getPriceInCard() {
        return Double.parseDouble(priceInCard.getText().replaceAll("[^\\.0123456789]", ""));
    }

    @Step("Get Product Item Name In Card")
    public String getNameInCard() {
        return nameInCard.getText();
    }

    @Step("Card Is Open")
    public Boolean cardIsOpen() {
        waitElementUntilVisible(card);
        return card.isDisplayed();
    }

    @Step("Profile Page Heading Is Displayed")
    public Boolean profilePageHeadingIsDisplayed() {
        waitUntilTextToBePresent(heading, profilePageHeading);
        return heading.isDisplayed();
    }

    @Step("Item Name Is Displayed")
    public Boolean itemNameIsDisplayed(String name) {
        waitLocatorUntilVisible(String.format(item, name));
        return findElementByXpath(String.format(item,name)).isDisplayed();
    }

    @Step("Get Product Name")
    public String getProductName(String name) {
        waitLocatorUntilVisible(String.format(item, name));
        return findElementByXpath(String.format(item, name)).getText();
    }

    @Step("Click On Next Button")
    public void clickOnNextButton() {
        javaScriptHelper.scrollIntoView(nextPageButton);
        waitElementUntilVisible(nextPageButton);
        nextPageButton.click();
    }

    @Step("Get Success Message After Adding Product Into the Basket")
    public String getAddToBasketSuccessMessage(String name) {
        waitLocatorUntilVisible(String.format(addToBasketMessage, name));
        return findElementByXpath(String.format(addToBasketMessage, name)).getText();
    }

    @Step("Click On Add To Basket Button")
    public void clickOnAddToBasketButton(String name) {
        waitLocatorUntilVisible(String.format(addToBasketButton,name));
        findElementByXpath(String.format(addToBasketButton, name)).click();
    }

    @Step("Click On Basket Button")
    public void clickOnBasketButton(){
        basketButton.click();
    }

    @Step("Get Error Message If Product is Out Of Stock")
    public String getOutOfStockMessage() {
        waitElementUntilVisible(outOfStockErrorMessage);
        return outOfStockErrorMessage.getText();
    }

    @Step("Product Table Is Displayed")
    public Boolean productTableIsDisplayed(){
        waitElementUntilVisible(productTable);
        return productTable.isDisplayed();
    }

    @Step("Added Product Is Present In Table Basket")
    public Boolean productInTableIsPresent(){
        waitElementUntilVisible(productInTable);
        return productInTable.isDisplayed();
    }

    @Step("CheckOut Button Is Enabled")
    public Boolean checkoutButtonIsEnabled(){
        waitElementUntilVisible(checkoutButton);
        return checkoutButton.isEnabled();
    }

    @Step("Get Total Price")
    public double getTotalPrice(){
        return Double.parseDouble(totalPrice.getText().replaceAll("[^\\.0123456789]", ""));
    }

    @Step("Get Product Name In Basket")
    public String getProductInBasket(String name){
        waitLocatorUntilVisible(String.format(itemInBasket,name));
        return findElementByXpath(String.format(itemInBasket,name)).getText();
    }

    @Step("Remove Product Item From Basket")
    public void removeProductFromBasket(String name){
        waitLocatorUntilVisible(String.format(itemInBasket,name));
        findElementByXpath(String.format(removeItemFromBasket, name)).click();
    }
}
