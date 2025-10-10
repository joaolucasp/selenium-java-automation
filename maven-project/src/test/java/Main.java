import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");

        driver.findElement(By.cssSelector("#userEmail")).sendKeys("johnlucca@mailinator.com");
        driver.findElement(By.cssSelector("#userPassword")).sendKeys("!Senha1234");
        driver.findElement(By.cssSelector("#login")).click();


    }
}
