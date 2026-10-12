package com.qaautomation.pages;

import com.qaautomation.utils.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;

/** Checkout step 2: order summary with item total, tax and final total. */
public class CheckoutOverviewPage extends BasePage {

    private final By itemPrice = By.className("inventory_item_price");
    private final By itemTotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public List<BigDecimal> getItemPrices() {
        waitForVisible(itemTotalLabel);
        return getAllTexts(itemPrice).stream()
                .map(PriceUtils::parsePrice)
                .toList();
    }

    public BigDecimal getItemTotal() {
        return PriceUtils.parsePrice(getText(itemTotalLabel));
    }

    public BigDecimal getTax() {
        return PriceUtils.parsePrice(getText(taxLabel));
    }

    public BigDecimal getTotal() {
        return PriceUtils.parsePrice(getText(totalLabel));
    }

    public void finish() {
        click(finishButton);
    }
}
