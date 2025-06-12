package section7_webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class FormsExercise {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/angularpractice/");

        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("John Lucca");

        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys("john.lucca@gmail.com");

        WebElement passwordInput = driver.findElement(By.id("exampleInputPassword1"));
        passwordInput.sendKeys("secret");

        WebElement checkBox = driver.findElement(By.id("exampleCheck1"));
        checkBox.click();

        WebElement genderSelect = driver.findElement(By.id("exampleFormControlSelect1"));
        Select gender = new Select(genderSelect);
        gender.selectByVisibleText("Male");

        WebElement employmentStatusRadio = driver.findElement(By.id("inlineRadio1"));
        employmentStatusRadio.click();

        WebElement dateOfBirthInput = driver.findElement(By.name("bday"));
        dateOfBirthInput.sendKeys("2001-01-01");

        WebElement submitButton = driver.findElement(By.cssSelector("input[value='Submit']"));
        submitButton.click();

        String alertSuccessObtained = driver.findElement(By.cssSelector("div.alert-success")).getText().replace("×", "").trim();
        String alertSuccessExpected = "Success! The Form has been submitted successfully!.";

        Assert.assertEquals(alertSuccessObtained, alertSuccessExpected);

        driver.quit();
    }
}
