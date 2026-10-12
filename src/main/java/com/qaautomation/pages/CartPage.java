package com.qaautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By cartItems = By.className("cart_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemButton = By.tagName("button");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getItemNames() {
        // The cart can be empty, so wait for the page itself rather than for an item.
        waitForVisible(checkoutButton);
        return getAllTexts(itemName);
    }

    public void removeItem(String productName) {
        waitForVisible(checkoutButton);
        WebElement item = driver.findElements(cartItems).stream()
                .filter(row -> row.findElement(itemName).getText().equals(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not in cart: " + productName));
        item.findElement(itemButton).click();
    }

    public void checkout() {
        click(checkoutButton);
    }
}
