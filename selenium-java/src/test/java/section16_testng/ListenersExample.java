package section16_testng;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersExample implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed!");
        System.out.println("Method: " + result.getName());
        System.out.println("Test name: " + result.getTestName());
        System.out.println(result.getThrowable().getMessage());

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }
}
