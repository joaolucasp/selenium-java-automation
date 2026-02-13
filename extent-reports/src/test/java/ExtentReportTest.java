import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ExtentReportTest {
  ExtentReports extent;

  @BeforeTest
  public void config() {
    String filePath = System.getProperty("user.dir") + "/reports/index.html";
    ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
    reporter.config().setReportName("Web Automation Results");
    reporter.config().setDocumentTitle("Automation Results");

    extent = new ExtentReports();
    extent.attachReporter(reporter);
    extent.setSystemInfo("Tester", "User");
  }

  @Test
  public void initialDemo() {
    extent.createTest("Initial Demo");
    WebDriver driver = new ChromeDriver();
    driver.get("https://rahulshettyacademy.com/");
    System.out.println(driver.getTitle());
    extent.flush();
    driver.quit();
  }

  @Test
  public void failureTest() {
    ExtentTest test = extent.createTest("Initial Demo");
    WebDriver driver = new ChromeDriver();
    driver.get("https://rahulshettyacademy.com/");
    System.out.println(driver.getTitle());
    test.fail("Result does not exist");
    extent.flush();
    driver.quit();
  }

  @AfterTest
  public void tearDown() {
    extent.flush();
  }
}
