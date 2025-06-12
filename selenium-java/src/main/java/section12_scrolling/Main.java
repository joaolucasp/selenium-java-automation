package section12_scrolling;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");

        handleScrollInTableComponent(driver);
    }

    private static void handleScrollInTableComponent(WebDriver driver) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        WebElement tableFixedHeader = driver.findElement(By.cssSelector(".tableFixHead"));

        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", tableFixedHeader);
        sleep(1000);
        javascriptExecutor.executeScript("document.querySelector('.tableFixHead').scrollTop=5000");

        List<WebElement> listOfAmountRows = tableFixedHeader.findElements(By.cssSelector("tbody tr td:last-child"));
        int totalAmountObtained = listOfAmountRows.stream().mapToInt(element -> Integer.parseInt(element.getText().trim())).sum();
        System.out.println("Total amount obtained: " + totalAmountObtained);

        WebElement totalAmountElement = driver.findElement(By.cssSelector(".totalAmount"));
        String totalAmount = totalAmountElement.getText().split(":")[1].trim();
        System.out.println("Total amount collected: " + totalAmount);

        Assert.assertEquals(totalAmountObtained, Integer.parseInt(totalAmount));

        driver.close();
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
