package section13_miscellaneous;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import org.testng.asserts.SoftAssert;

public class Main {
    public static void main(String[] args) {
        softAssert();

        manageCookies();

        manageLocalStorage();

        manageWindowSize();

        takeScreenshot();

        takePartialScreenshot();

        getElementSize();

        handleProxy();

        addExtensions();

        handleSSLCertificationExpired();
    }

    private static void softAssert() {
        SoftAssert soft = new SoftAssert();

        System.out.println("Iniciando teste com Soft Assertions...");

        // Exemplo 1: booleano verdadeiro
        soft.assertTrue(true, "Exemplo 1: Deve ser verdadeiro");

        // Exemplo 2: booleano falso (falha esperada)
        soft.assertTrue(false, "Exemplo 2: Falha proposital - esperado true");

        // Exemplo 3: igualdade correta
        soft.assertEquals(2 + 2, 4, "Exemplo 3: Soma correta");

        // Exemplo 4: igualdade incorreta (falha esperada)
        soft.assertEquals(5 * 2, 15, "Exemplo 4: Falha proposital - 5 * 2 != 15");

        // Exemplo 5: string comparação
        soft.assertEquals("abc", "abc", "Exemplo 5: Strings iguais");

        // Exemplo 6: string diferente (falha esperada)
        soft.assertEquals("foo", "bar", "Exemplo 6: Falha proposital - strings diferentes");

        System.out.println("Chamando assertAll() para verificar todas as asserções...");

        // Verifica todas as falhas acumuladas
        soft.assertAll();
    }

    private static void manageCookies() {
        WebDriver driver = new ChromeDriver();

        driver.manage().deleteAllCookies();
        driver.manage().addCookie(new Cookie("example", "example"));
        driver.manage().deleteCookieNamed("example");
    }

    private static void manageLocalStorage() {
        WebDriver driver = new ChromeDriver();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem(arguments[0], arguments[1]);", "chave", "valor");

        String valor = (String) js.executeScript("return localStorage.getItem(arguments[0]);", "chave");
        System.out.println("Valor da chave: " + valor);

        js.executeScript("localStorage.removeItem(arguments[0]);", "chave");
    }

    private static void manageWindowSize() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().minimize();
        driver.manage().window().maximize();

        System.out.println(driver.manage().window().getSize());
    }

    private static void takeScreenshot() {
        WebDriver driver = new ChromeDriver();

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshot, new File("screenshot.png")); // Save in current directory
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void takePartialScreenshot() {
        WebDriver driver = new ChromeDriver();
        driver.get("www.google.com");

        WebElement element = driver.findElement(By.cssSelector("example"));

        File screenshot = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshot, new File("screenshot.png")); // Save in current directory
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getElementSize() {
        WebDriver driver = new ChromeDriver();
        driver.get("www.google.com");

        WebElement element = driver.findElement(By.cssSelector("example"));

        System.out.println("Dimension: " + element.getSize());
        System.out.println("Widht: " + element.getRect().getDimension().getWidth());
        System.out.println("Height: " + element.getRect().getDimension().getHeight());
    }

    private static void handleProxy() {
        ChromeOptions options = new ChromeOptions();
        Proxy proxy = new Proxy();

        proxy.setHttpProxy("ipaddress:port");
        options.setProxy(proxy);
    }

    private static void addExtensions() {
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("example/path/for/extension"));

        WebDriver driver = new ChromeDriver(options);
        System.out.println(driver.getTitle());
    }

    private static void handleSSLCertificationExpired() {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.expired.badssl.com");
        System.out.println(driver.getTitle());
    }
}
