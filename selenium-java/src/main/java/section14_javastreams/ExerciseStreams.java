package section14_javastreams;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExerciseStreams {
    public static void main(String[] args) {
        getAllProductsTest();
        //orderingTest();
    }

    private static void getAllProductsTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

        orderList(driver);

        List<String> allFruits = getAllFruitNames(driver);

        System.out.println(allFruits.toString() + "Size is: " + allFruits.size());

        driver.quit();
    }

    private static void orderingTest(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

        orderList(driver);

        List<String> fruitNamesAfterOrdering = getFruitNames(driver);
        System.out.println("Fruits after ordering: " + fruitNamesAfterOrdering);

        List<String> fruitNamesOrdered = fruitNamesAfterOrdering.stream().sorted().toList();
        System.out.println("Fruits ordered manually: " + fruitNamesOrdered);

        Assert.assertEquals(fruitNamesOrdered, fruitNamesAfterOrdering);

        driver.quit();
    }

    private static void orderList(WebDriver driver) {
        WebElement headerFruitNameOrderLink = driver.findElement(By.cssSelector("table thead tr > th:first-child"));

        if (!Objects.equals(headerFruitNameOrderLink.getAttribute("aria-sort"), "ascending")) {
            headerFruitNameOrderLink.click();
        }
    }

    private static List<String> getFruitNames(WebDriver driver) {
        return driver.findElements(By.cssSelector("table tbody tr > td:first-child")).stream().map(fruit -> {
            return fruit.getText().trim();
        }).toList();
    }

    private static List<String> getAllFruitNames(WebDriver driver) {
        WebElement nextButton = driver.findElement(By.cssSelector("a[aria-label='Next']"));
        List<String> fruitNames = new ArrayList<>();

        do {
            fruitNames.addAll(getFruitNames(driver));

            nextButton.click();


            nextButton = driver.findElement(By.cssSelector("a[aria-label='Next']"));
        } while (Objects.equals(nextButton.getAttribute("aria-disabled"), "false"));

        fruitNames.addAll(getFruitNames(driver));

        return fruitNames;
    }
}
