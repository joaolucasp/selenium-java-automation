package training;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // This code shows how to use the driver locally, instead of having it managed dynamically by the library.
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Tokenlab\\Documents\\selenium-java-automation\\chromedriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.qaonlinetraining.com/");

        //driver.manage().window().maximize();
        //driver.manage().window().minimize();
        driver.navigate().to("https://www.google.com");
        driver.navigate().back();
        driver.navigate().forward();

        String pageTitle = driver.getTitle();

        System.out.println(pageTitle);

        // The 'close' method closes only actual tab or current window from browser. This method don't close all instances from the driver.
        //driver.close();

        // The 'quit' method closes all tabs/windows from the browser, closing all instances.
        driver.quit();
    }
}