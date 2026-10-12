package com.qaautomation.steps;

import com.qaautomation.data.TestUser;
import com.qaautomation.data.TestUserRepository;
import com.qaautomation.driver.DriverFactory;
import com.qaautomation.pages.HeaderComponent;
import com.qaautomation.pages.InventoryPage;
import com.qaautomation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private final TestUserRepository users = new TestUserRepository();

    private LoginPage loginPage() {
        return new LoginPage(DriverFactory.getDriver());
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage().open();
    }

    @When("I log in as a {string} user")
    public void iLogInAsUser(String userType) {
        TestUser user = users.findByType(userType);
        loginPage().loginAs(user.username(), user.password());
    }

    @Given("I am logged in as a {string} user")
    public void iAmLoggedInAsUser(String userType) {
        iAmOnTheLoginPage();
        iLogInAsUser(userType);
        iShouldSeeTheProductsPage();
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWith(String username, String password) {
        loginPage().loginAs(username, password);
    }

    @When("I log out")
    public void iLogOut() {
        new HeaderComponent(DriverFactory.getDriver()).logout();
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        assertEquals("Products", new InventoryPage(DriverFactory.getDriver()).getTitle());
    }

    @Then("I should see the login error {string}")
    public void iShouldSeeTheLoginError(String expected) {
        assertEquals(expected, loginPage().getErrorMessage());
    }

    @Then("I should be back on the login page")
    public void iShouldBeBackOnTheLoginPage() {
        assertTrue(loginPage().isLoaded(), "Login page was not shown after logout");
    }
}
