package com.qaautomation.pages;

import com.qaautomation.utils.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.util.List;

/** The "Products" page shown after a successful login. */
public class InventoryPage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By inventoryItems = By.className("inventory_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemPrice = By.className("inventory_item_price");
    private final By itemButton = By.tagName("button");
    private final By sortDropdown = By.className("product_sort_container");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public int getProductCount() {
        waitForVisible(inventoryItems);
        return driver.findElements(inventoryItems).size();
    }

    public List<String> getProductNames() {
        waitForVisible(inventoryItems);
        return getAllTexts(itemName);
    }

    public List<BigDecimal> getProductPrices() {
        waitForVisible(inventoryItems);
        return getAllTexts(itemPrice).stream()
                .map(PriceUtils::parsePrice)
                .toList();
    }

    public void addToCart(String productName) {
        findProduct(productName).findElement(itemButton).click();
    }

    /** Uses the text shown in the dropdown, e.g. "Price (low to high)". */
    public void sortBy(String optionText) {
        new Select(waitForVisible(sortDropdown)).selectByVisibleText(optionText);
    }

    private WebElement findProduct(String productName) {
        waitForVisible(inventoryItems);
        return driver.findElements(inventoryItems).stream()
                .filter(item -> item.findElement(itemName).getText().equals(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + productName));
    }
}
