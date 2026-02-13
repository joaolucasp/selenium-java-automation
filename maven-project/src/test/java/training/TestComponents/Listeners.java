package training.TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import training.resources.ExtentReporterNG;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
  ExtentReports extent = ExtentReporterNG.getReportObject();
  ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

  @Override
  public void onTestStart(ITestResult result) {
    ExtentTest test = extent.createTest(result.getMethod().getMethodName());
    extentTest.set(test);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    extentTest.get().log(Status.PASS, "Test passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    ExtentTest test = extentTest.get();
    test.fail(result.getThrowable());

    String testName = result.getMethod().getMethodName();

    WebDriver localDriver = null;
    try {
      localDriver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
          .get(result.getInstance());

    } catch (Exception e) {
      test.log(Status.WARNING, "Could not fetch WebDriver via reflection: " + e);
    }

    if (localDriver != null) {
      try {
        String filePath = TakeScreenshot(testName, localDriver);
        test.addScreenCaptureFromPath(filePath, testName);
      } catch (Exception e) {
        test.log(Status.WARNING, "Screenshot capture failed: " + e);
      }
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    extentTest.get().log(Status.SKIP, "Test skipped");
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  }

  @Override
  public void onFinish(ITestContext context) {
    extent.flush();
  }
}
