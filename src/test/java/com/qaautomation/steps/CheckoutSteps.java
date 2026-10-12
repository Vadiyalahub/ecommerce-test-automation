package com.qaautomation.steps;

import com.qaautomation.driver.DriverFactory;
import com.qaautomation.pages.CartPage;
import com.qaautomation.pages.CheckoutCompletePage;
import com.qaautomation.pages.CheckoutInfoPage;
import com.qaautomation.pages.CheckoutOverviewPage;
import com.qaautomation.utils.PriceUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {

    private CheckoutInfoPage infoPage() {
        return new CheckoutInfoPage(DriverFactory.getDriver());
    }

    private CheckoutOverviewPage overviewPage() {
        return new CheckoutOverviewPage(DriverFactory.getDriver());
    }

    @When("I start checkout")
    public void iStartCheckout() {
        new CartPage(DriverFactory.getDriver()).checkout();
    }

    @When("I enter checkout details {string}, {string}, {string}")
    public void iEnterCheckoutDetails(String firstName, String lastName, String postalCode) {
        infoPage().enterDetails(firstName, lastName, postalCode);
    }

    @When("I continue to the order overview")
    public void iContinueToTheOrderOverview() {
        infoPage().continueToOverview();
    }

    @Then("I should see the checkout error {string}")
    public void iShouldSeeTheCheckoutError(String expected) {
        assertEquals(expected, infoPage().getErrorMessage());
    }

    @Then("the item total should equal the sum of the item prices")
    public void theItemTotalShouldEqualTheSumOfPrices() {
        CheckoutOverviewPage overview = overviewPage();
        assertSameAmount(PriceUtils.sum(overview.getItemPrices()), overview.getItemTotal());
    }

    @Then("the order total should equal item total plus tax")
    public void theOrderTotalShouldEqualItemTotalPlusTax() {
        CheckoutOverviewPage overview = overviewPage();
        assertSameAmount(overview.getItemTotal().add(overview.getTax()), overview.getTotal());
    }

    @When("I finish the order")
    public void iFinishTheOrder() {
        overviewPage().finish();
    }

    @Then("I should see the confirmation message {string}")
    public void iShouldSeeTheConfirmationMessage(String expected) {
        String actual = new CheckoutCompletePage(DriverFactory.getDriver()).getConfirmationMessage();
        assertTrue(expected.equalsIgnoreCase(actual), "Expected '" + expected + "' but was '" + actual + "'");
    }

    /** compareTo ignores scale, so 39.98 and 39.980 count as the same amount. */
    private static void assertSameAmount(BigDecimal expected, BigDecimal actual) {
        assertEquals(0, expected.compareTo(actual), "Expected $" + expected + " but was $" + actual);
    }
}
