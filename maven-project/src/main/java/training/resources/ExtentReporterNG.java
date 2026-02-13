package training.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
  public static ExtentReports getReportObject() {
    String filePath = System.getProperty("user.dir") + "/reports/index.html";
    ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
    reporter.config().setReportName("Web Automation Results");
    reporter.config().setDocumentTitle("Automation Results");

    ExtentReports extent = new ExtentReports();
    extent.attachReporter(reporter);
    extent.setSystemInfo("Tester", "User");
    return extent;
  }
}
