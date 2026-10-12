package com.qaautomation.steps;

import com.qaautomation.driver.DriverFactory;
import com.qaautomation.pages.CartPage;
import com.qaautomation.pages.HeaderComponent;
import com.qaautomation.pages.InventoryPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {

    private InventoryPage inventoryPage() {
        return new InventoryPage(DriverFactory.getDriver());
    }

    private CartPage cartPage() {
        return new CartPage(DriverFactory.getDriver());
    }

    private HeaderComponent header() {
        return new HeaderComponent(DriverFactory.getDriver());
    }

    @When("I add {string} to the cart")
    public void iAddToTheCart(String productName) {
        inventoryPage().addToCart(productName);
    }

    @When("I add the following products to the cart:")
    public void iAddTheFollowingProducts(List<String> productNames) {
        productNames.forEach(this::iAddToTheCart);
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        header().openCart();
    }

    @When("I remove {string} from the cart")
    public void iRemoveFromTheCart(String productName) {
        cartPage().removeItem(productName);
    }

    @Then("the cart badge should show {int}")
    public void theCartBadgeShouldShow(int expected) {
        assertEquals(expected, header().getCartCount());
    }

    @Then("the cart should contain {string}")
    public void theCartShouldContain(String productName) {
        List<String> items = cartPage().getItemNames();
        assertTrue(items.contains(productName), "Cart items: " + items);
    }

    @Then("the cart should contain only {string}")
    public void theCartShouldContainOnly(String productName) {
        assertEquals(List.of(productName), cartPage().getItemNames());
    }
}
