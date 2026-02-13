package training.TestComponents;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import training.data.DataReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
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

  @BeforeMethod(alwaysRun = true)
  public void launchBrowser() throws IOException {
    driver = initializeDriver();
  }

  @AfterMethod(alwaysRun = true)
  public void quitBrowser() throws IOException {
    driver.quit();
  }

  public List<HashMap<String, String>> getJsonData(String filePath) throws IOException {
    DataReader dataReader = new DataReader();
    return dataReader.getJsonDataToHashMap(filePath);
  }

  public String TakeScreenshot(String filename) throws IOException {
    TakesScreenshot ts = (TakesScreenshot) driver;
    String filePath = System.getProperty("user.dir") + "\\reports\\" + filename + ".png";

    File source = ts.getScreenshotAs(OutputType.FILE);
    File destination = new File(filePath);

    FileUtils.copyFile(source, destination);
    return filePath;
  }
}
