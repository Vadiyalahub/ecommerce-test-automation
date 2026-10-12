# E-commerce Test Automation

I built this project to learn how a real UI test automation framework is put together:
Java, Selenium, Cucumber and JUnit 5, built with Maven and run on GitHub Actions.

It tests [Sauce Demo](https://www.saucedemo.com), a practice online shop, through the whole customer journey:
logging in, browsing and sorting products, using the cart, and checking out.

Right now there are **19 UI scenarios** and **17 unit tests**, and they all pass.

## What it tests

- **Login:** valid user, locked-out user, wrong username or password, empty fields, logout
- **Products:** all 6 products show up, sorting by name and price in both directions
- **Cart:** adding one or several products, removing a product, the cart badge count
- **Checkout:** a full purchase from start to finish, the required fields, and the price maths
  (item total = sum of prices, total = item total + tax)

The tests are written in plain English, so you can read what each one does without knowing Java:

```gherkin
Scenario: Complete a purchase from start to finish
  Given I am logged in as a "standard" user
  When I enter checkout details "Jane", "Doe", "85001"
  And I continue to the order overview
  Then the item total should equal the sum of the item prices
  And the order total should equal item total plus tax
  When I finish the order
  Then I should see the confirmation message "Thank you for your order!"
```

## How it's put together

- **Feature files** (`src/test/resources/features`) hold the scenarios.
- **Step definitions** (`src/test/java/.../steps`) connect each sentence to Java code.
- **Page objects** (`src/main/java/.../pages`) know how to find things on each page. Every page extends
  `BasePage`, which has the shared click/type/wait helpers. If the site changes a button, I only fix one class.
- **Hooks** start a fresh browser before every scenario and attach a screenshot when one fails.
- **Test users** live in a small SQL table (H2 database) instead of being hard-coded in tests.

Tests are tagged `@smoke` (the 5 most important ones) or `@regression` (everything), so I can run a quick check
or the full suite.

## Running it

You need Java 17+ and Maven. If Chrome isn't installed, Selenium downloads a copy automatically.

```bash
./run-tests.sh unit              # unit tests, no browser
./run-tests.sh smoke --headed    # quick check, and you can watch the browser
./run-tests.sh regression        # all 19 scenarios, about a minute
```

The report ends up in `target/cucumber-reports/cucumber.html`. Open it in a browser and click any scenario to
see its steps. Failed ones are red and have a screenshot attached.

GitHub Actions runs the unit tests and then the UI tests on every push. The report can be downloaded from the run
under **Artifacts**.

The test cases and the user stories they come from are listed in [docs/TEST_PLAN.md](docs/TEST_PLAN.md).

## Some choices I made

- **Explicit waits, no `Thread.sleep`.** A test waits only until the element is ready, which keeps runs fast
  and avoids random timing failures.
- **`BigDecimal` for prices.** `double` can't store money exactly (0.1 + 0.2 isn't 0.3), and the checkout
  tests compare totals to the cent.
- **One `HeaderComponent`.** The cart icon and menu are on every page, so they're one shared class instead of
  being copied into each page class.
- **Scenario Outlines for repeated cases.** The four login error cases are one scenario with a data table.
- **Chrome's password popup is turned off.** The demo site uses a well-known password, which makes Chrome show a
  "change your password" warning that can block the page.

## What I'd add next

- Run scenarios in parallel
- API tests with REST Assured
- Run on Chrome and Firefox in CI
