package training.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import training.AbstractComponents.AbstractComponent;

import java.util.List;

public class CartPage extends AbstractComponent {
  WebDriver driver;

  @FindBy(css=".cartSection h3")
  List<WebElement> productsNameElement;

  @FindBy(css=".totalRow button")
  WebElement checkoutButton;

  public CartPage(WebDriver driver) {
    super(driver);
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }

  public Boolean productIsPresentOnCart(String productName) {
    return productsNameElement.stream().anyMatch((cartProductName) -> cartProductName.getText().equalsIgnoreCase(productName));
  }

  public void goToCheckoutPage() {
    checkoutButton.click();
  }
}
