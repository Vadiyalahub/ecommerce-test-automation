# Test Plan: E-commerce Test Automation

## 1. Goal
Automatically check that a customer can log in, find products, manage their cart and complete a purchase,
and catch regressions on every code change.

**Application under test:** [Sauce Demo](https://www.saucedemo.com), a demo online shop built for practising test automation.

## 2. Scope

**In scope:** login, product listing and sorting, cart, checkout, price calculations, form validation.

**Out of scope:** performance and load testing, security testing, real payment processing.

## 3. Test types

| Type | Tag / location | When it runs | Purpose |
|---|---|---|---|
| Unit tests | `src/test/java/.../unit` | Every push | Check the framework's own helper code |
| Smoke | `@smoke` | Every push, ~30 s | "Can a customer log in, add to cart and check out?" |
| Regression | `@regression` | Every push | Full check of every feature and error case |

## 4. Environment
- Browser: Chrome (latest), headless in CI. Firefox also supported (`-Dbrowser=firefox`).
- Screen size: 1920×1080.
- CI: GitHub Actions on Ubuntu.
- Test data: user accounts in an H2 SQL table (`src/main/resources/testdata/test-users.sql`).

## 5. Entry and exit criteria
- **Start testing when:** the site is reachable and the build compiles.
- **Done when:** all smoke and regression tests pass and every failure has been investigated.

## 6. Traceability: user stories → test cases

| ID | User story | Test case | Type |
|---|---|---|---|
| TC-01 | US-01 Login | Valid user logs in and sees Products | Smoke |
| TC-02 | US-01 Login | Locked-out user sees an error | Regression |
| TC-03 | US-01 Login | Unknown username is rejected | Regression |
| TC-04 | US-01 Login | Wrong password is rejected | Regression |
| TC-05 | US-01 Login | Empty username shows "Username is required" | Regression |
| TC-06 | US-01 Login | Empty password shows "Password is required" | Regression |
| TC-07 | US-01 Login | User can log out | Smoke |
| TC-08 | US-02 Browse | All 6 products are listed | Smoke |
| TC-09 | US-02 Browse | Sort by name A–Z | Regression |
| TC-10 | US-02 Browse | Sort by name Z–A | Regression |
| TC-11 | US-02 Browse | Sort by price low–high | Regression |
| TC-12 | US-02 Browse | Sort by price high–low | Regression |
| TC-13 | US-03 Cart | Add one product; badge shows 1; product is in cart | Smoke |
| TC-14 | US-03 Cart | Add three products; badge shows 3 | Regression |
| TC-15 | US-03 Cart | Remove a product; only the other remains | Regression |
| TC-16 | US-04 Checkout | Full purchase; item total and tax add up; confirmation shown | Smoke (E2E) |
| TC-17 | US-04 Checkout | First name is required | Regression |
| TC-18 | US-04 Checkout | Last name is required | Regression |
| TC-19 | US-04 Checkout | Postal code is required | Regression |

### User stories
- **US-01** As a customer, I want to log in securely so that only I can use my account.
- **US-02** As a customer, I want to browse and sort products so that I can find what I need.
- **US-03** As a customer, I want to add and remove products in my cart so that I only buy what I want.
- **US-04** As a customer, I want to check out and see correct totals so that I pay the right amount.

## 7. Risks
- **Site changes.** If the demo site's layout changes, locators may need updating.
  They are all in the page classes, so each fix is in one place.
