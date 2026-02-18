package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import testComponents.BaseTest;
import training.pageobjects.CartPage;
import training.pageobjects.CheckoutPage;
import training.pageobjects.LandingPage;
import training.pageobjects.ProductCatalog;

import java.io.IOException;

public class StepDefinitionImplementations extends BaseTest {
  LandingPage landingPage;
  ProductCatalog productCatalog;
  CartPage cartPage;
  CheckoutPage checkoutPage;

  @Given("I landed on Ecommerce Page")
  public void iLandedOnEcommercePage() throws IOException {
    launchBrowser();

    landingPage = new LandingPage(driver);
    productCatalog = new ProductCatalog(driver);
    cartPage = new CartPage(driver);
    checkoutPage = new CheckoutPage(driver);

    landingPage.goToHomePage();
  }

  @Given("Logged in with username {string} and {string}")
  public void LoggedInWithUsernameAndPassword(String username, String password) {
    landingPage.login(username, password);
  }

  @When("I add product {string} to cart")
  public void iAddProductToCart(String productName) {
    productCatalog.addProductToCart(productName);
  }

  @When("Checkout {string} and submit the order")
  public void CheckoutAndSubmitTheOrder(String productName) {
    landingPage.goToCartPage();
    Assert.assertTrue(cartPage.productIsPresentOnCart(productName));
    cartPage.goToCheckoutPage();
    checkoutPage.selectCountry("brazil");
    checkoutPage.submitCheckout();
  }

  @Then("\"THANKYOU FOR THE ORDER.\" message is displayed on ConfirmationPage")
  public void thankYouForTheOrderMessageIsDisplayedOnConfirmationPage() {
    checkoutPage.checksIfOrderHasBeenConfirmed();
  }
}
