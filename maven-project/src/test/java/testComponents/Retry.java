package testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
  int count = 0;
  int maxRetries = 1;

  @Override
  public boolean retry(ITestResult result) {
    if (count < maxRetries) {
      count++;
      return true;
    }

    return false;
  }
}
