import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.fetch.Fetch;
import org.openqa.selenium.devtools.v142.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v142.network.model.ErrorReason;
import org.openqa.selenium.devtools.v143.network.Network;
import org.openqa.selenium.devtools.v143.network.model.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import java.net.URI;
import java.util.function.Predicate;

public class DevToolsNetworkTest {
  @Test
  public static void devToolsNetworkListenerTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

    devTools.addListener(Network.requestWillBeSent(), request -> {
      Request res = request.getRequest();

      String requestUrl = res.getUrl();
      System.out.println(requestUrl);
    });

    devTools.addListener(Network.responseReceived(), response -> {
      Response res = response.getResponse();

      String responseUrl = res.getUrl();
      int responseStatusCode = res.getStatus();
      String responseStatusCodeName = res.getStatusText();

      // Only prints if request has failed
      if (res.getStatus().toString().startsWith("4")) {
        System.out.println(responseUrl + "[" + responseStatusCode + "] " + responseStatusCodeName);
      }

    });

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");
    driver.findElement(By.cssSelector("button[routerlink*='/library']")).click();
  }

  @Test
  public static void devToolsFetchRedirectRequestTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();

    devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

    devTools.addListener(Fetch.requestPaused(), request -> {
      if (request.getRequest().getUrl().contains("shetty")) {
        String requestRedirectUrl = request.getRequest().getUrl().replace("=shetty", "=BadGuy");

        System.out.println(requestRedirectUrl);

        devTools.send(Fetch.continueRequest(request.getRequestId(),
            Optional.of(requestRedirectUrl),
            Optional.of(request.getRequest().getMethod()),
            request.getRequest().getPostData(),
            request.getResponseHeaders(),
            Optional.empty()));

        return;
      }

      devTools.send(Fetch.continueRequest(request.getRequestId(),
          Optional.of(request.getRequest().getUrl()),
          Optional.of(request.getRequest().getMethod()),
          request.getRequest().getPostData(),
          request.getResponseHeaders(),
          Optional.empty()));
    });

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");
    driver.findElement(By.cssSelector("button[routerlink*='/library']")).click();
    Thread.sleep(2000);

    String oneBookAvailableMessage = driver.findElement(By.cssSelector("p")).getText();
    System.out.println(oneBookAvailableMessage);
  }

  @Test
  public static void devToolsFetchFailureRequestTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();

    Optional<List<RequestPattern>> patterns = Optional.of(Arrays.asList(new RequestPattern(Optional.of("*GetBook*"), Optional.empty(), Optional.empty())));
    devTools.send(Fetch.enable(patterns, Optional.empty()));

    devTools.addListener(Fetch.requestPaused(), request -> {
      devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
    });

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");
    driver.findElement(By.cssSelector("button[routerlink*='/library']")).click();
    Thread.sleep(2000);
  }

  @Test
  public static void devToolsNetworkBlockRequestTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();

    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

    Optional<List<BlockPattern>> blockedUrls = Optional.of(List.of(
        new BlockPattern("*://*/*.jpg*", true),
        new BlockPattern("*://*/*.css*", true)
    ));
    devTools.send(Network.setBlockedURLs(blockedUrls, Optional.empty()));

    long startTime = System.currentTimeMillis();

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");
    driver.findElement(By.linkText("Browse Products")).click();
    driver.findElement(By.linkText("Selenium")).click();
    driver.findElement(By.cssSelector("button.add-to-cart")).click();
    String itemAddedToCartMessage = driver.findElements(By.cssSelector("p")).getFirst().getText();

    long endTime = System.currentTimeMillis();

    System.out.println(itemAddedToCartMessage);
    System.out.println(endTime - startTime);
  }

  @Test
  public static void devToolsNetworkSpeedControlTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();

    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

    List<NetworkConditions> networkConditions = Arrays.asList(new NetworkConditions(
        "https://rahulshettyacademy.com/*",
        3000,
        20000,
        10000,
        Optional.of(ConnectionType.CELLULAR2G),
        Optional.empty(),
        Optional.empty(),
        Optional.empty()
    ));

    devTools.send(Network.emulateNetworkConditionsByRule(false, networkConditions));

    long startTime = System.currentTimeMillis();

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");
    driver.findElement(By.cssSelector("button[routerlink*='/library']")).click();

    long endTime = System.currentTimeMillis();

    System.out.println(endTime - startTime);
  }

  @Test
  public static void devToolsNetworkOfflineTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();

    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

    List<NetworkConditions> networkConditions = Arrays.asList(new NetworkConditions(
        "https://rahulshettyacademy.com/*",
        3000,
        20000,
        10000,
        Optional.of(ConnectionType.CELLULAR2G),
        Optional.empty(),
        Optional.empty(),
        Optional.empty()
    ));

    devTools.send(Network.emulateNetworkConditionsByRule(true, networkConditions));

    devTools.addListener(Network.loadingFailed(), loadingFailed -> {
      String errorText = loadingFailed.getErrorText();

      System.out.println(errorText);
    });

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");
  }

  @Test
  public static void devToolsNetworkAuthenticationTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();

    DevTools devTools = driver.getDevTools();
    devTools.createSession();

    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
    Predicate<URI> uriPredicate = uri -> uri.getHost().contains("httpbin.org");

    ((HasAuthentication)driver).register(uriPredicate, UsernameAndPassword.of("foo", "bar"));
    driver.get("https://httpbin.org/basic-auth/foo/bar");
  }

  @Test
  public static void devToolsNetworkConsoleLogsCaptureTest() throws InterruptedException {
    ChromeDriver driver = new ChromeDriver();
    driver.manage().window().maximize();

    driver.get("https://rahulshettyacademy.com/angularAppdemo/");
    driver.findElement(By.linkText("Browse Products")).click();
    driver.findElement(By.linkText("Selenium")).click();
    driver.findElement(By.cssSelector("button.add-to-cart")).click();
    driver.findElement(By.linkText("Cart")).click();
    driver.findElement(By.cssSelector("#exampleInputEmail1")).clear();
    driver.findElement(By.cssSelector("#exampleInputEmail1")).sendKeys("2");

    LogEntries entries = driver.manage().logs().get(LogType.BROWSER);
    List<LogEntry> logEntries = entries.getAll();

    for (LogEntry logEntry : logEntries) {
      System.out.println(logEntry);
    }
  }
}

