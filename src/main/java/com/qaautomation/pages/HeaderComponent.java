package com.qaautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The top bar (menu + cart icon) that appears on every page after login.
 * Kept as its own component so it is not copied into every page class.
 */
public class HeaderComponent extends BasePage {

    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    /** The badge is hidden when the cart is empty, so that counts as 0. */
    public int getCartCount() {
        return isDisplayed(cartBadge) ? Integer.parseInt(getText(cartBadge)) : 0;
    }

    public void openCart() {
        click(cartLink);
    }

    public void logout() {
        click(menuButton);
        // The side menu slides in from the left; wait until it has fully arrived before clicking.
        wait.until(d -> d.findElement(logoutLink).getRect().getX() >= 0);
        click(logoutLink);
    }
}
