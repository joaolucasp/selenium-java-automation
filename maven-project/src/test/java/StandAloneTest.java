import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
  public static void main(String[] args) {
    WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().maximize();
    driver.get("https://rahulshettyacademy.com/client");

    driver.findElement(By.cssSelector("#userEmail")).sendKeys("johnlucca@mailinator.com");
    driver.findElement(By.cssSelector("#userPassword")).sendKeys("!Senha1234");
    driver.findElement(By.cssSelector("#login")).click();

    String productName = "ADIDAS ORIGINAL";
    List<WebElement> productsElements = driver.findElements(By.cssSelector("#products .card"));
    WebElement productFounded = productsElements.stream()
        .filter((product) -> product.findElement(By.cssSelector("h5 > b")).getText()
            .equals(productName)).findFirst().orElse(null);

    // Add item to cart
    productFounded.findElement(By.cssSelector(".card-body button:last-of-type")).click();

    // Waiting for loading
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

    // Access to cart
    driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

    List<WebElement> cartProductsName = driver.findElements(By.cssSelector(".cartSection h3"));

    Boolean matched = cartProductsName.stream().anyMatch((cartProductName) -> cartProductName.getText().equalsIgnoreCase(productName));
    Assert.assertTrue(matched);

    driver.findElement(By.cssSelector(".totalRow button")).click();

    Actions actions = new Actions(driver);
    actions.sendKeys(driver.findElement(By.cssSelector(".user__address [placeholder='Select Country']")), "brazil").build().perform();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
    driver.findElement(By.cssSelector(".ta-results .ta-item")).click();
    driver.findElement(By.cssSelector(".action__submit")).click();

    String orderConfirmationMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
    Assert.assertTrue(orderConfirmationMessage.equalsIgnoreCase("Thankyou for the order."));

    driver.close();
  }
}
