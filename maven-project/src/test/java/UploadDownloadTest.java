import excel.DataDriven;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import training.entities.Fruit;
import training.pageobjects.FruitTablePage;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.util.List;

public class UploadDownloadTest {

  @Test
  public void uploadDownloadTest() throws ParseException, IOException {
    WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
    driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");
    String filePath = "C:\\Users\\jluca\\Downloads\\download.xlsx";
    String sheetName = "Sheet1";
    String updatedValue = "859";

    // Download File
    driver.findElement(By.cssSelector("#downloadButton")).click();

    // Edit Excel
    int rowNumber = DataDriven.getRowNumber(filePath, sheetName, "Apple");
    int columnNumber = DataDriven.getColumnNumber(filePath, sheetName, "price");
    Boolean cellHasBeenUpdated = DataDriven.updateCell(filePath, sheetName, rowNumber, columnNumber, updatedValue);

    Assert.assertTrue(cellHasBeenUpdated);

    // Upload
    WebElement uploadButton = driver.findElement(By.cssSelector("input[type='file']"));
    uploadButton.sendKeys(filePath);

    // Wait for Success Message to show up and wait for disappear
    By toastLocator = By.cssSelector(".Toastify__toast-body div:nth-child(2)");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
    String toastText = driver.findElement(toastLocator).getText();
    Assert.assertEquals(toastText, "Updated Excel Data Successfully.");


    // Verify updated excel data showing in the web table
    List<Fruit> allFruits = getTableData(driver);
    allFruits.forEach(fruit -> {
      if (fruit.getName().equalsIgnoreCase("Apple")) {
        Assert.assertEquals(fruit.getPrice().toString(), updatedValue);
      }
    });

    driver.quit();
  }

  public List<Fruit> getTableData(WebDriver driver) throws ParseException {
    FruitTablePage tablePage = new FruitTablePage(driver);
    return tablePage.getFruits();
  }
}
