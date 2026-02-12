package training.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import training.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
  WebDriver driver;

  public LandingPage(WebDriver driver) {
    super(driver);
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }

  @FindBy(id="userEmail")
  WebElement userEmailField;

  @FindBy(id="userPassword")
  WebElement userPasswordField;

  @FindBy(id="login")
  WebElement loginButton;

  public void login(String email, String password) {
    userEmailField.sendKeys(email);
    userPasswordField.sendKeys(password);
    loginButton.click();
  }

  public void goToHomePage() {
    driver.get("https://rahulshettyacademy.com/client");
  }
}
