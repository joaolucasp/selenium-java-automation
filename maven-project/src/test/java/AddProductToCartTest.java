import org.testng.annotations.Test;
import testComponents.BaseTest;
import testComponents.Retry;
import training.pageobjects.LandingPage;
import training.pageobjects.ProductCatalog;

import java.io.IOException;

public class AddProductToCartTest extends BaseTest {
  @Test(retryAnalyzer = Retry.class)
  public void AddProductToCart() throws IOException {
    LandingPage landingPage = new LandingPage(driver);
    landingPage.goToHomePage();
    landingPage.login("johnlucca@mailinator.com", "!Senha1234");

    String productName = "ADIDAS ORIGINAL";

    ProductCatalog productCatalog = new ProductCatalog(driver);
    productCatalog.addProductToCart(productName);
  }
}
