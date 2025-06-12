package section8_ecommerce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        // Implicit wait mode
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10 ));

        driver.get("https://rahulshettyacademy.com/seleniumPractise");

        List<String> itemsOfInterest = Main.getItemsOfInterest();
        List<WebElement> listOfProducts = Main.getAllProducts(driver);

        addProductsToCart(listOfProducts, itemsOfInterest);
        goToCheckoutPage(driver);
        applyPromoCode(driver);

        //driver.quit();
    }

    private static void addProductsToCart(List<WebElement> listOfProducts, List<String> itemsOfInterest) {
        for (int i = 0; i < listOfProducts.size(); i++) {
            String productName = listOfProducts.get(i).findElement(By.cssSelector("h4.product-name")).getText().trim();

            if (itemsOfInterest.contains(productName)) {
                listOfProducts.get(i).findElement(By.cssSelector(".product-action button")).click();
            }
        }
    }

    private static void goToCheckoutPage(WebDriver driver) {
        // Open modal cart
        driver.findElement(By.cssSelector(".cart-icon img")).click();

        // Proceed to checkout
        driver.findElement(By.cssSelector(".cart-preview div:last-child button")).click();
    }

    private static void applyPromoCode(WebDriver driver) {
        WebDriverWait waitDriver = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Add promo code
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.promoCode")));
        driver.findElement(By.cssSelector("input.promoCode")).sendKeys("rahulshettyacademy");

        // Apply code
        driver.findElement(By.cssSelector("button.promoBtn")).click();

        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.promoInfo")));

        // Get promo message
        System.out.println("Promo Info message:" +  driver.findElement(By.cssSelector("span.promoInfo")).getText());
    }

    public static List<String> getItemsOfInterest() {
        List<String> itemsOfInterest = new ArrayList<String>();
        itemsOfInterest.add("Cucumber - 1 Kg");
        itemsOfInterest.add("Beetroot - 1 Kg");
        itemsOfInterest.add("Tomato - 1 Kg");

        return itemsOfInterest;
    }

    public static List<WebElement> getAllProducts(WebDriver driver) {
        return driver.findElements(By.cssSelector("div.products .product"));
    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
