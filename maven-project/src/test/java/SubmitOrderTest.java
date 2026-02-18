import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testComponents.BaseTest;
import training.pageobjects.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {
  @Test(dataProvider = "getJsonData", groups = {"Purchase"})
  public void SubmitOrder(HashMap<String, String> testData) throws IOException {
    LandingPage landingPage = new LandingPage(driver);
    landingPage.goToHomePage();
    landingPage.login(testData.get("email"), testData.get("password"));

    ProductCatalog productCatalog = new ProductCatalog(driver);
    productCatalog.addProductToCart(testData.get("productName"));

    // Access to cart
    CartPage cartPage = new CartPage(driver);
    cartPage.goToCartPage();
    Assert.assertTrue(cartPage.productIsPresentOnCart(testData.get("productName")));
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
    orderPage.productIsPresentOnOrder("ADIDAS ORIGINAL");
  }

  @DataProvider
  public Object[][] getHashMapData() {
    HashMap<String, String> data = new HashMap<>();
    data.put("email", "johnlucca@mailinator.com");
    data.put("password", "!Senha1234");
    data.put("productName", "ADIDAS ORIGINAL");

    HashMap<String, String> data2 = new HashMap<>();
    data2.put("email", "johnlucca@mailinator.com");
    data2.put("password", "!Senha1234");
    data2.put("productName", "ZARA COAT 3");

    return new Object[][] {{data}, {data2}};
  }

  @DataProvider
  public Object[][] getJsonData() throws IOException {
    String filePath = System.getProperty("user.dir") + "/src/test/java/training/data/PurchaseOrder.json";

    List<HashMap<String, String>> data = getJsonData(filePath);
    return data.stream().map(item -> new Object[]{item}).toArray(Object[][]::new);
  }
}
