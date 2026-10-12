package com.qaautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Final page shown after the order is placed. */
public class CheckoutCompletePage extends BasePage {

    private final By confirmationHeader = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationMessage() {
        return getText(confirmationHeader);
    }
}
