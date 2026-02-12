import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import training.pageobjects.CartPage;
import training.pageobjects.CheckoutPage;
import training.pageobjects.LandingPage;
import training.pageobjects.ProductCatalog;

import java.time.Duration;
import java.util.List;

public class SubmitOrderTest {
  public static void main(String[] args) {
    WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().maximize();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    LandingPage landingPage = new LandingPage(driver);
    landingPage.goToHomePage();
    landingPage.login("johnlucca@mailinator.com", "!Senha1234");

    String productName = "ADIDAS ORIGINAL";

    ProductCatalog productCatalog = new ProductCatalog(driver);
    productCatalog.addProductToCart(productName);

    // Access to cart
    CartPage cartPage = new CartPage(driver);
    cartPage.goToCartPage();
    Assert.assertTrue(cartPage.productIsPresentOnCart(productName));
    cartPage.goToCheckoutPage();

    //Actions actions = new Actions(driver);
    //actions.sendKeys(driver.findElement(By.cssSelector(".user__address [placeholder='Select Country']")), "brazil").build().perform();

    CheckoutPage checkoutPage = new CheckoutPage(driver);
    checkoutPage.selectCountry("brazil");
    checkoutPage.submitCheckout();
    checkoutPage.checksIfOrderHasBeenConfirmed();

    driver.close();
  }
}
