package section10_interactions;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

        //handleWindows(driver);
        handleIframes(driver);
        //actions(driver);
    }

    public static void handleWindows(WebDriver driver) {
        // =======================
        // Store the ID of the current tab (window)
        // =======================
        String originalWindow = driver.getWindowHandle();

        // =======================
        // Open a new tab using JavaScript
        // (Requires switching afterward)
        // =======================
        ((JavascriptExecutor) driver).executeScript("window.open('https://amazon.com', '_blank');");

        // =======================
        // Get all window handles (IDs of open tabs/windows)
        // =======================
        Set<String> allWindows = driver.getWindowHandles();

        // =======================
        // Switch to the new tab (the one that is not the original)
        // =======================
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // =======================
        // Now you're on the new tab — do something
        // =======================
        System.out.println("Title of new tab: " + driver.getTitle());

        // =======================
        // Close the new tab
        // =======================
        driver.close();

        // =======================
        // Switch back to the original tab
        // =======================
        driver.switchTo().window(originalWindow);

        // =======================
        // Continue testing in the original tab
        // =======================
        System.out.println("Back to original tab: " + driver.getTitle());

        // Finish
        driver.quit();
    }

    public static void handleIframes(WebDriver driver) {
        // =======================
        // Switch to iframe using name or ID
        // =======================
        driver.switchTo().frame("iframe-name");

        // OR Switch to iframe using index (first iframe on the page)
        // driver.switchTo().frame(0);

        // OR switch using WebElement
        // WebElement iframeElement = driver.findElement(By.cssSelector("iframe[src*='embed']"));
        // driver.switchTo().frame(iframeElement);

        // =======================
        // Now you're inside the iframe — interact with elements inside it
        // =======================
        WebElement input = driver.findElement(By.cssSelector("#search-box"));
        input.sendKeys("Selenium");

        // =======================
        // Switch back to the main (parent) document
        // =======================
        driver.switchTo().defaultContent();

        // You can now access elements outside the iframe
        WebElement logo = driver.findElement(By.cssSelector(".main-logo"));
        System.out.println("Logo text: " + logo.getText());

        driver.quit();
    }

    public static void actions(WebDriver driver) {
        Actions actions = new Actions(driver);

        // =======================
        // Move the mouse over an element (hover effect)
        // =======================
        WebElement menu = driver.findElement(By.cssSelector(".menu"));
        actions.moveToElement(menu).perform();

        // =======================
        // Click on an element (left-click)
        // =======================
        WebElement button = driver.findElement(By.cssSelector("#btn-submit"));
        actions.click(button).perform();

        // =======================
        // Right-click on an element (context menu)
        // =======================
        WebElement item = driver.findElement(By.cssSelector(".list-item"));
        actions.contextClick(item).perform();

        // =======================
        // Double-click on an element
        // =======================
        WebElement box = driver.findElement(By.cssSelector(".text-box"));
        actions.doubleClick(box).perform();

        // =======================
        // Click and hold an element, move to another, then release (drag-and-drop)
        // =======================
        WebElement dragItem = driver.findElement(By.cssSelector(".draggable"));
        WebElement dropTarget = driver.findElement(By.cssSelector(".dropzone"));
        actions.clickAndHold(dragItem).moveToElement(dropTarget).release().perform();

        // =======================
        // Type text into the active element (keyboard input)
        // =======================
        actions.sendKeys("typing text").perform();

        // =======================
        // Press CTRL + A (select all)
        // =======================
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();

        // =======================
        // Hold SHIFT and click (e.g., multi-select)
        // =======================
        WebElement checkbox = driver.findElement(By.cssSelector(".checkbox"));
        actions.keyDown(Keys.SHIFT).click(checkbox).keyUp(Keys.SHIFT).perform();

        // =======================
        // Drag one element and drop it onto another (shortcut method)
        // =======================
        WebElement source = driver.findElement(By.cssSelector(".card"));
        WebElement target = driver.findElement(By.cssSelector(".board"));
        actions.dragAndDrop(source, target).perform();
    }
}
