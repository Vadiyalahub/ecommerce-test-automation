package com.qaautomation.steps;

import com.qaautomation.driver.DriverFactory;
import com.qaautomation.pages.InventoryPage;
import com.qaautomation.utils.SortUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventorySteps {

    private InventoryPage inventoryPage() {
        return new InventoryPage(DriverFactory.getDriver());
    }

    @Then("I should see {int} products")
    public void iShouldSeeProducts(int expected) {
        assertEquals(expected, inventoryPage().getProductCount());
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String option) {
        inventoryPage().sortBy(option);
    }

    @Then("the products should be sorted by {word} in {word} order")
    public void theProductsShouldBeSorted(String field, String order) {
        boolean ascending = switch (order) {
            case "ascending" -> true;
            case "descending" -> false;
            default -> throw new IllegalArgumentException("Unknown order: " + order);
        };
        InventoryPage page = inventoryPage();
        switch (field) {
            case "name" -> assertSorted(page.getProductNames(), ascending);
            case "price" -> assertSorted(page.getProductPrices(), ascending);
            default -> throw new IllegalArgumentException("Unknown sort field: " + field);
        }
    }

    private static <T extends Comparable<? super T>> void assertSorted(List<T> values, boolean ascending) {
        assertTrue(SortUtils.isSorted(values, ascending),
                "Expected " + (ascending ? "ascending" : "descending") + " order but got: " + values);
    }
}
