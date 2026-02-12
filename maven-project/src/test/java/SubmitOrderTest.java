import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import training.TestComponents.BaseTest;
import training.pageobjects.*;

import java.io.IOException;

public class SubmitOrderTest extends BaseTest {
  WebDriver driver;
  String productName = "ADIDAS ORIGINAL";

  @Test()
  public void SubmitOrder() throws IOException {
    LandingPage landingPage = new LandingPage(driver);
    landingPage.goToHomePage();
    landingPage.login("johnlucca@mailinator.com", "!Senha1234");

    ProductCatalog productCatalog = new ProductCatalog(driver);
    productCatalog.addProductToCart(productName);

    // Access to cart
    CartPage cartPage = new CartPage(driver);
    cartPage.goToCartPage();
    Assert.assertTrue(cartPage.productIsPresentOnCart(productName));
    cartPage.goToCheckoutPage();

    CheckoutPage checkoutPage = new CheckoutPage(driver);
    checkoutPage.selectCountry("brazil");
    checkoutPage.submitCheckout();
    checkoutPage.checksIfOrderHasBeenConfirmed();
  }

  @Test(dependsOnMethods = {"SubmitOrder"})
  public void OrderHistoryMethod() {
    LandingPage landingPage = new LandingPage(driver);
    landingPage.goToHomePage();
    landingPage.login("johnlucca@mailinator.com", "!Senha1234");

    OrderPage orderPage = new OrderPage(driver);
    orderPage.goToOrdersPage();
    orderPage.productIsPresentOnOrder(productName);
  }

  @BeforeMethod
  public void launchBrowser() throws IOException {
    driver = initializeDriver();
  }

  @AfterMethod
  public void quitBrowser() throws IOException {
    driver.quit();
  }
}
