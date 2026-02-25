import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v144.emulation.Emulation;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DevToolsTest {
  @Test
  public static void devToolsTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();
    devTools.send(Emulation.setDeviceMetricsOverride(600, 1000, 50, true, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");

    driver.findElement(By.cssSelector(".navbar-toggler")).click();
    Thread.sleep(3000);
    driver.findElement(By.linkText("Library")).click();
  }

  @Test
  public static void cdpCommandExecutionTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();

    Map<String, Object> deviceMetrics = new HashMap<String, Object>();
    deviceMetrics.put("width", 600);
    deviceMetrics.put("height", 1000);
    deviceMetrics.put("deviceScaleFactor", 50);
    deviceMetrics.put("mobile", true);

    driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");

    driver.findElement(By.cssSelector(".navbar-toggler")).click();
    Thread.sleep(3000);
    driver.findElement(By.linkText("Library")).click();
  }
}
