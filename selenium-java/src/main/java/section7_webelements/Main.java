package section7_webelements;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/dropdownsPractise");

        Main.introduceTestNGAssertions();
        //Main.handleStaticDropdowns(driver);
        //Main.handleDropdownWithActions(driver);
        //Main.handleDrodpdownWithDynamicallySearched(driver);
        //Main.handleCheckbox(driver);

        driver.quit();
    }

    public static void introduceTestNGAssertions() {
        boolean numberFourIsEven = 4 % 2 == 0;
        int remainderOfDividingSevenByTwo = 7 % 2;

        Assert.assertTrue(numberFourIsEven);
        Assert.assertFalse(!numberFourIsEven);
        Assert.assertEquals(remainderOfDividingSevenByTwo, 1);
    }

    public static void handleStaticDropdowns(WebDriver driver) {
        WebElement dropdown = Main.identifyUsingId(driver, "ctl00_mainContent_DropDownListCurrency");

        Select dropdownCurrency = new Select(dropdown);

        // Select by index. Note: The first index starts at 0
        dropdownCurrency.selectByIndex(3);
        String selectedOption = dropdownCurrency.getFirstSelectedOption().getText();
        System.out.println("Select by index: " + selectedOption);

        // Select by value.
        dropdownCurrency.selectByValue("AED");
        selectedOption = dropdownCurrency.getFirstSelectedOption().getText();
        System.out.println("Select by value: " + selectedOption);

        // Select by visible text.
        dropdownCurrency.selectByVisibleText("INR");
        selectedOption = dropdownCurrency.getFirstSelectedOption().getText();
        System.out.println("Select by visible text: " + selectedOption);
    }

    public static void handleDropdownWithActions(WebDriver driver) {
        WebElement passengersDropdown = Main.identifyUsingId(driver, "divpaxinfo");
        passengersDropdown.click();

        Main.sleep(2000);

        WebElement incrementAdult = Main.identifyUsingId(driver, "hrefIncAdt");
        incrementAdult.click();

        WebElement closeModalButton = Main.identifyUsingId(driver, "btnclosepaxoption");
        closeModalButton.click();

        String totalOfPassengers = passengersDropdown.getText().trim();
        System.out.println("Total of Passengers: " + totalOfPassengers);
    }

    public static void handleDrodpdownWithDynamicallySearched(WebDriver driver) {
        WebElement countryInputDropdown = Main.identifyUsingId(driver, "autosuggest");
        countryInputDropdown.sendKeys("br");

        Main.sleep(3000);

        List<WebElement> countries = driver.findElements(By.cssSelector("#ui-id-1 li[class='ui-menu-item'] a"));

        countries.forEach(country -> {
            System.out.println("Country: " + country.getText());
            if (country.getText().equalsIgnoreCase("Gibraltar")) {
                country.click();
            }
        });

        Main.sleep(2000);
    }

    public static void handleCheckbox(WebDriver driver) {
        WebElement checkbox = Main.identifyUsingCssSelector(driver, "input[id*='SeniorCitizenDiscount']");
        System.out.println("Checkbox is selected? Answer: " + checkbox.isSelected());

        checkbox.click();

        System.out.println("Checkbox is selected? Answer: " + checkbox.isSelected());
    }

    public static WebElement identifyUsingId(WebDriver driver, String locator) {
        return driver.findElement(By.id(locator));
    }

    public static WebElement identifyUsingCssSelector(WebDriver driver, String locator) {
        return driver.findElement(By.cssSelector(locator));
    }

    public static String getTextFromElement(WebDriver driver, String cssLocator) {
        WebElement element = section5_locators.Main.identifyUsingCssSelector(driver, cssLocator);
        return element.getText().trim();
    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
