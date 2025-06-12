package section11_practise;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        //handleWindowsAndLinks(driver);
        handleCalendarComponent(driver);
    }

    private static void handleWindowsAndLinks(WebDriver driver) {
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");

        WebElement footerElement = driver.findElement(By.id("gf-BIG"));

        // 1) Number Of links presents in footer
        int numberOfLinksInFooter = footerElement.findElements(By.tagName("a")).size();
        System.out.println("Number of links presents in footer: " + numberOfLinksInFooter);

        WebElement firstColumnFooterElement = footerElement.findElement(By.xpath("//table/tbody/tr/td[1]/ul"));
        int numberOfLinksInFirstColumn = firstColumnFooterElement.findElements(By.tagName("a")).size();

        // 2) Click on each link in the column and check if the pages are opening
        for (int i = 1; i < numberOfLinksInFirstColumn; i++) {
            String shortcutThatSimulatesTheClick = Keys.chord(Keys.CONTROL, Keys.ENTER);

            firstColumnFooterElement.findElements(By.cssSelector("a")).get(i).sendKeys(shortcutThatSimulatesTheClick);
            sleep(500);
        }

        // 3) Open all the tabs and get page titles
        Set<String> windowTabs = driver.getWindowHandles();
        Iterator<String> windowIterator = windowTabs.iterator();

        while (windowIterator.hasNext()) {
            String currentWindowHandle = windowIterator.next();
            driver.switchTo().window(currentWindowHandle);
            sleep(500);

            System.out.println("Current window handle: " + driver.getTitle());
        }
    }

    private static void handleCalendarComponent(WebDriver driver) {
        String yearDesired = "2027";
        String monthDesired = "6";
        String dayDesired = "15";

        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

        // 1) Open calendar component
        WebElement calendarInput = driver.findElement(By.cssSelector(".react-date-picker__inputGroup"));
        calendarInput.click();

        // 2) Open year select
        WebElement yearButtonChange = driver.findElement(By.cssSelector(".react-calendar__navigation__label"));
        yearButtonChange.click();
        yearButtonChange.click();

        // 3) Search year desired
        WebElement yearButton = driver.findElement(By.xpath("//button[text()='" + yearDesired + "']"));
        yearButton.click();

        // 4) Select month desired
        WebElement monthButton = driver.findElement(By.cssSelector(".react-calendar__year-view__months__month:nth-child(" + monthDesired + ")"));
        monthButton.click();

        // 5) Select day desired
        // NOTE: This locator is fragile, because the calendar component displays the days of the previous month and the next month, so if you select a day like '30' or '1', there will probably be 2 elements found.
        WebElement dayButton = driver.findElement(By.xpath("//abbr[text()='" + dayDesired + "']"));
        dayButton.click();

        String monthInputValue = calendarInput.findElement(By.cssSelector("input[name='month']")).getAttribute("value");
        String dayInputValue = calendarInput.findElement(By.cssSelector("input[name='day']")).getAttribute("value");
        String yearInputValue = calendarInput.findElement(By.cssSelector("input[name='year']")).getAttribute("value");

        String obtainedDateSelected = String.format("%s/%s/%s", dayInputValue, monthInputValue, yearInputValue);
        String expectedDate = String.format("%s/%s/%s", dayDesired, monthDesired, yearDesired);

        System.out.printf("Obtained date: %s | Expected Date: %s", obtainedDateSelected, expectedDate);
        Assert.assertEquals(obtainedDateSelected, expectedDate);
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
