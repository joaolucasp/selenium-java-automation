import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import training.TestComponents.BaseTest;
import training.pageobjects.LandingPage;
import training.pageobjects.ProductCatalog;

import java.io.IOException;

public class AddProductToCartTest extends BaseTest {
  @Test()
  public void AddProductToCart() throws IOException {
    LandingPage landingPage = new LandingPage(driver);
    landingPage.goToHomePage();
    landingPage.login("johnlucca@mailinator.com", "!Senha1234");

    String productName = "ADIDAS ORIGINAL";

    ProductCatalog productCatalog = new ProductCatalog(driver);
    productCatalog.addProductToCart(productName);
  }
}
