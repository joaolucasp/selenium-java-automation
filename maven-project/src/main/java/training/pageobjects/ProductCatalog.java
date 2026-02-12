package training.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import training.AbstractComponents.AbstractComponent;

import java.util.List;

public class ProductCatalog extends AbstractComponent {
  WebDriver driver;

  @FindBy(css = ".mb-3")
  List<WebElement> productsElements;
  By listOfProductsLocator = By.cssSelector(".mb-3");

  @FindBy(css=".ng-animating")
  WebElement spinner;

  @FindBy(css="#toast-container")
  WebElement toastContainerElement;

  By addToCartButtonLocator = By.cssSelector(".card-body button:last-of-type");

  public ProductCatalog(WebDriver driver) {
    super(driver);
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }

  public List<WebElement> getProductList() {
    waitForLocatorToAppear(listOfProductsLocator);
    return productsElements;
  }

  public WebElement getProductByName(String productName) {
    return getProductList().stream()
        .filter((product) -> product.findElement(By.cssSelector("b")).getText()
            .equals(productName)).findFirst().orElse(null);
  }

  public void addProductToCart(String productName) {
    WebElement product = getProductByName(productName);
    product.findElement(addToCartButtonLocator).click();
    waitForElementToAppear(toastContainerElement);
    waitForElementToDisappear(spinner);
  }
}
