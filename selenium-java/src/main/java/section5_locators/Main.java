package section5_locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        // Defines an implicit timeout that waits for an element to be identified, until the test fails
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

        driver.get("https://rahulshettyacademy.com/locatorspractice/");

        // 1. Fill in the login form
        WebElement usernameInput = Main.identifyUsingId(driver, "inputUsername");
        usernameInput.sendKeys("input_value");

        WebElement passwordInput = Main.identifyUsingName(driver, "inputPassword");
        passwordInput.sendKeys("password_value");

        WebElement submitButton = Main.identifyUsingClassname(driver, "signInBtn");
        submitButton.click();

        System.out.println("Login Error Message: " + Main.getTextFromElement(driver, "p.error"));

        // 2. Click on recover password link
        WebElement forgotPasswordLink = Main.identifyUsingLinkText(driver, "Forgot your password?");
        forgotPasswordLink.click();

        Main.sleep(1000);

        // 3. Fill out the password recovery form
        WebElement nameInput = Main.identifyUsingXPath(driver, "//input[@placeholder='Name']");
        nameInput.sendKeys("input_value");

        WebElement emailInput = Main.identifyUsingCssSelector(driver, "input[placeholder='Email'");
        emailInput.sendKeys("input_value@domain.com");

        WebElement phoneNumberInput = Main.identifyUsingCssSelector(driver, "input[placeholder='Phone Number']");
        phoneNumberInput.sendKeys("123457890");

        WebElement resetPasswordButton = Main.identifyUsingCssSelector(driver, ".forgot-pwd-btn-conainer .reset-pwd-btn");
        resetPasswordButton.click();

        // 4. Getting temporary password
        String temporaryPassword = Main.getTextFromElement(driver, ".sign-up-container p.infoMsg");
        temporaryPassword = Main.extractPasswordFromText(temporaryPassword);

        // 5. Click the link to return to the login page
        WebElement goToLoginPageLink = Main.identifyUsingCssSelector(driver, "button.go-to-login-btn");
        goToLoginPageLink.click();

        Main.sleep(1000);

        // 6. Fill login form again with temporary password
        usernameInput.sendKeys("input_value");
        passwordInput.sendKeys(temporaryPassword);

        WebElement rememberUserCheckbox = Main.identifyUsingCssSelector(driver, "#chkboxOne");
        WebElement termsAndPrivacyPolicyCheckbox = Main.identifyUsingCssSelector(driver, "#chkboxTwo");
        rememberUserCheckbox.click();
        termsAndPrivacyPolicyCheckbox.click();

        submitButton.click();

        // 7. Wait page load
        Main.sleep(1000); // Flakiness fail, selenium is bad :/

        String successMessage = Main.getTextFromElement(driver, ".login-container p");
        System.out.println("Success Login Message: " + successMessage);

        Assert.assertEquals(successMessage, "You are successfully logged in.");

        Main.sleep(5000);
        driver.quit();
    }

    public static WebElement identifyUsingId(WebDriver driver, String locator) {
        return driver.findElement(By.id(locator));
    }

    public static WebElement identifyUsingName(WebDriver driver, String locator) {
        return driver.findElement(By.name(locator));
    }

    public static WebElement identifyUsingClassname(WebDriver driver, String locator) {
        return driver.findElement(By.className(locator));
    }

    public static WebElement identifyUsingCssSelector(WebDriver driver, String locator) {
        return driver.findElement(By.cssSelector(locator));
    }

    public static WebElement identifyUsingLinkText(WebDriver driver, String text) {
        return driver.findElement(By.linkText(text));
    }

    public static WebElement identifyUsingXPath(WebDriver driver, String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public static String getTextFromElement(WebDriver driver, String cssLocator) {
        WebElement element = Main.identifyUsingCssSelector(driver, cssLocator);
        return element.getText().trim();
    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String extractPasswordFromText(String text) {
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? matcher.group(1) : "";
    }
}
