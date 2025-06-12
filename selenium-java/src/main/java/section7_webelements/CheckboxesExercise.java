package section7_webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class CheckboxesExercise {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");

        CheckboxesExercise.stepOne(driver);
        CheckboxesExercise.stepTwo(driver);

        driver.quit();
    }

    public static void stepOne(WebDriver driver) {
        WebElement firstCheckbox = driver.findElement(By.id("checkBoxOption1"));
        firstCheckbox.click();

        boolean firstCheckboxIsChecked = firstCheckbox.isSelected();

        Assert.assertTrue(firstCheckboxIsChecked);

        firstCheckbox.click();

        Assert.assertFalse(firstCheckbox.isSelected());
    }

    public static void stepTwo(WebDriver driver) {
        int numberOfCheckboxesInPage = driver.findElements(By.cssSelector("input[type='checkbox']")).size();
        System.out.println("Number of checkboxes in page: " + numberOfCheckboxesInPage);
    }
}
