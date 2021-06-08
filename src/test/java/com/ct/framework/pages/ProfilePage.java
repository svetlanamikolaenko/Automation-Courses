package com.ct.framework.pages;

import com.ct.framework.helpers.JavaScriptHelper;
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
    @FindBy(xpath = "//div[@class = 'item-name' and contains(., 'Banana Juice')]")
    private WebElement bananaJuiceItem;
    @FindBy(xpath = "//div[@class = 'item-name' and contains(., 'Banana Juice')]/following-sibling::div[@class='item-price']")
    private WebElement bananaJuiceItemPrice;
    @FindBy(xpath = "//div[@class = 'item-name' and contains(., 'Banana Juice')]/../preceding-sibling::div/img")
    private WebElement bananaJuiceItemImage;
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

    @FindBy(xpath = "//div[@class = 'item-name' and contains(., 'Apple Pomace')]")
    private WebElement applePomaceItem;

    @FindBy(xpath = "//div[@class = 'item-name' and contains(., 'OWASP Juice Shop Coaster')]")
    private WebElement juiceShopCoasterItem;

    @FindBy(xpath = "//span[contains(.,'We are out of stock!')]")
    private WebElement outOfStockErrorMessage;


    @FindBy(xpath = "//div[@class = 'item-name' and contains(., 'OWASP Juice Shop Coaster')]//ancestor::figure//button[@aria-label='Add to Basket']")
    private WebElement juiceShopCoasterAddToBasketButton;

    @FindBy(xpath = "//div[@class = 'item-name' and contains(., 'Apple Pomace')]//ancestor::figure//button[@aria-label='Add to Basket']")
    private WebElement applePomaceAddToBasketButton;


    @FindBy(xpath = "//button[contains(@aria-label,'Show the shopping cart')]")
    private WebElement basketButton;

    @FindBy(xpath = "//button[@aria-label='Next page']")
    private WebElement nextPageButton;

    @FindBy(xpath = "//span[contains(.,'Apple Pomace')]")
    private WebElement addToBasketApplePomaceSuccessMessage;

    @FindBy(xpath = "//mat-table")
    private WebElement productTable;

    @FindBy(xpath = "//button[@id='checkoutButton']")
    private WebElement checkoutButton;

    @FindBy(xpath = "//div[@id='price']")
    private WebElement totalPrice;

    @FindBy(xpath = "//mat-row")
    private WebElement productInTable;

    @FindBy(xpath = "//mat-cell[contains(.,'Apple Pomace')]")
    private WebElement applePomaceInBasket;

    @FindBy(xpath = "//mat-cell[contains(., 'Apple Pomace')]//following-sibling::mat-cell[last()]")
    private WebElement removeApplePomaceFromBasket;

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
    }

    public String addApplePomaceToBasket(){
        if(profilePageHeadingIsDisplayed() && applePomaceNameIsDisplayed()){
            clickOnButtonAddToBasketApplePomace();
            return getAddToBasketApplePomaceMessage();
        }
        return "Can't find Apple Pomace";
    }

    public String applePomaceInBasket(){
        clickOnBasketButton();
        if( productTableIsDisplayed() && productInTableIsPresent()){
            return getApplePomaceInBasket();
        }
        return "Apple Pomace is not in the basket!";
    }

    public void clickOnBananaJuiceItem() {
        waitUntilVisible(bananaJuiceItem);
        bananaJuiceItem.click();
    }

    public String getBananaJuiceImage() {
        return bananaJuiceItemImage.getAttribute("src");
    }

    public double getBananaJuicePrice() {
        return Double.parseDouble(bananaJuiceItemPrice.getText().replaceAll("[^\\.0123456789]", ""));
    }

    public String getBananaJuiceName() {
        waitUntilVisible(bananaJuiceItem);
        return bananaJuiceItem.getText();
    }

    public String getDescriptionInCard() {
        return descriptionInCard.getText();
    }

    public String getImageInCard() {
        return imageInCard.getAttribute("src");
    }

    public double getPriceInCard() {
        return Double.parseDouble(priceInCard.getText().replaceAll("[^\\.0123456789]", ""));
    }

    public String getNameInCard() {
        return nameInCard.getText();
    }

    public Boolean cardIsOpen() {
        waitUntilVisible(card);
        return card.isDisplayed();
    }

    public Boolean profilePageHeadingIsDisplayed() {
        waitUntilTextToBePresent(heading, profilePageHeading);
        return heading.isDisplayed();
    }

    public Boolean applePomaceNameIsDisplayed() {
        waitUntilVisible(applePomaceItem);
        return applePomaceItem.isDisplayed();
    }

    public String getJuiceShopCoasterName() {
        waitUntilVisible(juiceShopCoasterItem);
        return juiceShopCoasterItem.getText();
    }

    public void clickOnNextButton() {
        javaScriptHelper.scrollIntoView(nextPageButton);
        waitUntilVisible(nextPageButton);
        nextPageButton.click();
    }

    public String getAddToBasketApplePomaceMessage() {
        waitUntilVisible(addToBasketApplePomaceSuccessMessage);
        return addToBasketApplePomaceSuccessMessage.getText();
    }

    public void clickOnButtonAddToBasketApplePomace() {
        applePomaceAddToBasketButton.click();
    }

    public void clickOnButtonAddToBasketJuiceShopCoaster() {
        juiceShopCoasterAddToBasketButton.click();
    }

    public void clickOnBasketButton(){
        basketButton.click();
    }
    public String getOutOfStockMessage() {
        waitUntilVisible(outOfStockErrorMessage);
        return outOfStockErrorMessage.getText();
    }

    public Boolean productTableIsDisplayed(){
        waitUntilVisible(productTable);
        return productTable.isDisplayed();
    }
    public Boolean productInTableIsPresent(){
        waitUntilVisible(productInTable);
        return productInTable.isDisplayed();
    }

    public Boolean checkoutButtonIsEnabled(){
        waitUntilVisible(checkoutButton);
        return checkoutButton.isEnabled();
    }

    public double getTotalPrice(){
        return Double.parseDouble(totalPrice.getText().replaceAll("[^\\.0123456789]", ""));
    }

    public String getApplePomaceInBasket(){
        waitUntilVisible(applePomaceInBasket);
        return applePomaceInBasket.getText();
    }

    public void removeApplePomaceFromBasket(){
        removeApplePomaceFromBasket.click();
    }
}
