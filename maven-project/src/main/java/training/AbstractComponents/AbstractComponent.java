package training.AbstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractComponent {
  WebDriver driver;

  @FindBy(css="[routerlink*='cart']")
  WebElement cartNavbarButton;

  @FindBy(css="[routerlink*='myorders']")
  WebElement myOrdersButton;

  public AbstractComponent(WebDriver driver) {
    this.driver = driver;
  }

  public void waitForLocatorToAppear(By locator) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void waitForElementToAppear(WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  public void waitForElementToDisappear(WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.invisibilityOf(element));
  }

  public void goToCartPage() {
    cartNavbarButton.click();
  }

  public void goToOrdersPage() {
    myOrdersButton.click();
  }
}
