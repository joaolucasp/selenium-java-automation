package training.TestComponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
  public WebDriver driver;

  public WebDriver initializeDriver() throws IOException {
    Properties prop = new Properties();
    FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/training/resources/GlobalData.properties");
    prop.load(file);

    String browserName = prop.getProperty("BROWSER");

    switch (browserName) {
      case "chrome":
        driver = new ChromeDriver();
        break;

      case "firefox":
        driver = new FirefoxDriver();
        break;

      case "edge":
        driver = new EdgeDriver();
        break;

      default:
    }

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().maximize();

    return driver;
  }
}
