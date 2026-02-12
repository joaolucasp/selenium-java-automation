package training.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import training.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
  WebDriver driver;

  @FindBy(css=".user__address [placeholder='Select Country']")
  WebElement countryAddressElement;

  @FindBy(css=".ta-results")
  WebElement countryResultsElement;

  @FindBy(css=".ta-results .ta-item")
  WebElement countryNameElement;

  @FindBy(css=".action__submit")
  WebElement submitButton;

  @FindBy(css=".hero-primary")
  WebElement orderConfirmationMessageElement;

  public CheckoutPage(WebDriver driver) {
    super(driver);
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }

  public void searchByCountry(String country) {
    countryAddressElement.sendKeys(country);
    waitForElementToAppear(countryResultsElement);
  }

  public void selectCountry(String country) {
    searchByCountry(country);
    countryNameElement.click();
  }

  public void submitCheckout() {
    submitButton.click();
  }

  public void checksIfOrderHasBeenConfirmed() {
    String orderConfirmationMessage = orderConfirmationMessageElement.getText();
    Assert.assertFalse(orderConfirmationMessage.equalsIgnoreCase("Thankyou for the order."));
  }
}
