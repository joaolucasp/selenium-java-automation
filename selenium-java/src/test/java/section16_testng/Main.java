package section16_testng;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    @BeforeSuite
    public void antesDaSuite() {
        System.out.println("[BeforeSuite] → Executa 1x antes de tudo (antes de todos os testes)");
    }

    @BeforeTest
    public void antesDoTeste() {
        System.out.println("[BeforeTest] → Executa 1x antes do <test> no testng.xml");
    }

    @BeforeGroups("regression")
    public void antesDoGrupoRegression() {
        System.out.println("[BeforeGroups] → Executa antes dos testes do grupo 'regression'");
    }

    @BeforeClass
    public void antesDaClasse() {
        System.out.println("[BeforeClass] → Executa 1x antes de todos os métodos da classe");
    }

    @BeforeMethod
    public void antesDeCadaMetodo() {
        System.out.println("[BeforeMethod] → Executa antes de CADA método de teste");
    }

    @Test(groups = {"regression"}, enabled = false)
    public void teste1() {
        System.out.println("[TEST] → Executando teste1 (grupo: regression)");
    }

    @Test(enabled = false)
    public void teste2() {
        System.out.println("[TEST] → Executando teste2 (sem grupo)");
    }

    @Test
    @Parameters({"browser", "baseUrl"})
    public void testeComParametros(String browser, String baseUrl) {
        System.out.println("Navegador: " + browser);
        System.out.println("Base URL: " + baseUrl);
    }

    @Test(dataProvider = "getDataProvider", enabled = false)
    public void testeComDataProvider(String browserName, String baseUrl) {
        System.out.println("Navegador: " + browserName + ", Base URL: " + baseUrl);

        Assert.assertEquals(true, false);
    }

    @DataProvider
    public Iterator<Object[]> getDataProvider() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"firefox", "https://www.google.com"});
        data.add(new Object[]{"chrome", "https://www.google.com"});
        data.add(new Object[]{"chromium", "https://www.google.com"});

        return data.iterator();
    }
}
