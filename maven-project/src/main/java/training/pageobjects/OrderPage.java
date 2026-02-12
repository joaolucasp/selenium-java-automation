package training.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import training.AbstractComponents.AbstractComponent;

import java.util.List;

public class OrderPage extends AbstractComponent {
  WebDriver driver;

  @FindBy(css="table tbody > tr td:nth-child(3)")
  List<WebElement> orderProductNamesElement;

  public OrderPage(WebDriver driver) {
    super(driver);
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }

  public Boolean productIsPresentOnOrder(String productName) {
    return orderProductNamesElement.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
  }
}
