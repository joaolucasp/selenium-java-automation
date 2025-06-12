# Guia Completo de TestNG para QAs

Este guia foi criado com base em perguntas e dúvidas práticas sobre o uso do TestNG, especialmente voltado para profissionais de QA que desejam dominar o uso do `testng.xml` e as anotações de ciclo de vida.

---

## 🌐 O que é o TestNG?

**TestNG** é um framework de testes para Java, inspirado no JUnit, mas com recursos avançados como:

* Execução paralela de testes
* Agrupamento de métodos
* Parâmetros externos via XML
* Gerenciamento de dependências entre testes
* Suporte a testes de unidade, integração e funcionais

---

## 📁 Onde criar o `testng.xml`?

Crie o arquivo em:

* `src/test/resources` ➜ Local mais comum e reconhecido por IDEs e Maven
* Raiz do projeto (`/`) ➜ Alternativa válida

---

## ✨ Como gerar automaticamente no IntelliJ

1. Instale o plugin **TestNG** (se ainda não estiver ativo)
2. Clique com o botão direito em uma classe/pacote de teste
3. Selecione: `TestNG > Create TestNG XML`
4. Salve o arquivo em `src/test/resources/testng.xml`

---

## ✍️ Principais Tags e Parâmetros do `testng.xml`

### `<suite>`

```xml
<suite name="MinhaSuite" parallel="classes" thread-count="4">
```

* `parallel`: controla execução simultânea (`tests`, `classes`, `methods`)
* `thread-count`: número de threads para paralelismo

### `<test>`

Agrupa classes ou pacotes. Representa um cenário ou conjunto de testes.

```xml
<test name="TestesDeLogin">
  ...
</test>
```

### `<classes>` e `<class>`

Lista de classes de teste a serem executadas.

```xml
<classes>
  <class name="tests.LoginTest"/>
</classes>
```

### `<methods>`

Filtra métodos específicos dentro da classe:

```xml
<methods>
  <include name="testeLoginValido"/>
  <exclude name="testeLoginInvalido"/>
</methods>
```

### `<groups>`

Executa apenas métodos que pertencem a certos grupos:

```xml
<groups>
  <run>
    <include name="regression"/>
    <exclude name="smoke"/>
  </run>
</groups>
```

### `<package>`

Inclui todas as classes de um pacote:

```xml
<packages>
  <package name="tests.login"/>
</packages>
```

### `<parameter>`

Passa valores externos para os testes:

```xml
<parameter name="browser" value="chrome"/>
<parameter name="baseUrl" value="https://site.com"/>
```

No código:

```java
@Parameters({"browser", "baseUrl"})
@Test
public void meuTeste(String browser, String baseUrl) {
  // usa os valores passados
}
```

---

## 🎓 Grupos e Classes: por que declarar ambos?

> Mesmo usando `<groups>`, é obrigatório declarar `<classes>` ou `<packages>`, pois o TestNG **só avalia os grupos dentro das classes listadas**.

Para evitar isso, use `<package>`:

```xml
<test name="TestesComGrupos">
  <groups>
    <run>
      <include name="regression"/>
    </run>
  </groups>
  <packages>
    <package name="tests"/>
  </packages>
</test>
```

---

## ⚖️ Diferenças entre Before Annotations

| Anotação        | Quando executa                                 |
| --------------- | ---------------------------------------------- |
| `@BeforeSuite`  | 1x antes de **toda a suite** (antes de tudo)   |
| `@BeforeTest`   | 1x antes de cada `<test>` no XML               |
| `@BeforeGroups` | Antes do primeiro método do grupo especificado |
| `@BeforeClass`  | 1x antes de todos os testes de uma classe      |
| `@BeforeMethod` | Antes de **cada** método `@Test`               |

### 🔍 Exemplo:

```java
@BeforeSuite
public void setupSuite() { System.out.println("[BeforeSuite]"); }

@BeforeTest
public void setupTest() { System.out.println("[BeforeTest]"); }

@BeforeGroups("regression")
public void setupGroup() { System.out.println("[BeforeGroups]"); }

@BeforeClass
public void setupClass() { System.out.println("[BeforeClass]"); }

@BeforeMethod
public void setupMethod() { System.out.println("[BeforeMethod]"); }

@Test(groups = "regression")
public void teste1() { System.out.println("[TEST] teste1"); }
```

---

## 🌐 Compartilhamento de contexto entre testes

### 📌 Mesmo dentro da classe:

Use atributos da instância para compartilhar:

```java
WebDriver driver;
@BeforeClass
public void setup() {
  driver = new ChromeDriver();
}
```

### 💼 Entre classes:

Use **herança** ou um **singleton** como `DriverManager`.

### ⚡ Execução paralela:

Use `ThreadLocal` para isolar instâncias por thread:

```java
public class DriverManager {
  private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
  public static WebDriver get() { return driver.get(); }
  public static void set(WebDriver d) { driver.set(d); }
}
```

---


## 🧱 Uso do parâmetro `dependsOnMethods`

### 🧠 O que faz?

Permite definir **dependência entre métodos** de teste.
Se o método do qual depende **falhar ou for ignorado**, o método dependente **não será executado**.

### 📌 Sintaxe:

```java
@Test
public void login() {
    // código de login
}

@Test(dependsOnMethods = {"login"})
public void acessoAreaLogada() {
    // só será executado se login() passar
}
```

### ✅ Vantagens:

* Cria **fluxos encadeados** de teste (ex: login → navegação → ação)
* Evita falsos negativos por falta de pré-condição

### ⚠️ Cuidados:

* Se a cadeia de dependência for muito longa, dificulta a manutenção
* Testes devem ser preferencialmente **independentes**, exceto quando realmente necessário

---

## Parâmetros da Anotação `@Test` no TestNG

A anotação `@Test` do TestNG é altamente configurável e permite definir vários comportamentos para o método de teste. Esta referência cobre os parâmetros mais comuns e úteis para controle de execução.

---

### 🔍 Lista de Parâmetros

### `priority`

Define a ordem de execução dos testes (quanto menor o valor, mais cedo o teste roda).

```java
@Test(priority = 1)
public void testeA() {}
```

### `enabled`

Permite desativar temporariamente um teste.

```java
@Test(enabled = false)
public void testeDesativado() {}
```

### `groups`

Associa o teste a um ou mais grupos.

```java
@Test(groups = {"regression", "smoke"})
public void testeAgrupado() {}
```

### `dependsOnMethods`

Indica que o teste depende da execução de outros métodos.

```java
@Test(dependsOnMethods = {"login"})
public void verificarAcesso() {}
```

### `dependsOnGroups`

Define dependência com base em grupos.

```java
@Test(dependsOnGroups = {"login"})
public void testarCheckout() {}
```

### `alwaysRun`

Garante que o teste seja executado mesmo que os testes dos quais depende falhem.

```java
@Test(dependsOnMethods = {"configurar"}, alwaysRun = true)
public void limpeza() {}
```

### `invocationCount`

Define quantas vezes o teste será executado.

```java
@Test(invocationCount = 3)
public void rodarTresVezes() {}
```

### `timeOut`

Tempo máximo (em milissegundos) para execução do teste.

```java
@Test(timeOut = 2000) // 2 segundos
public void testeComTimeout() {}
```

### `expectedExceptions`

Indica que uma exceção é esperada para o teste ser considerado bem-sucedido.

```java
@Test(expectedExceptions = ArithmeticException.class)
public void deveLancarErro() {
    int x = 1 / 0;
}
```

### `dataProvider`

Define qual DataProvider será usado para fornecer dados ao teste.

```java
@Test(dataProvider = "dadosLogin")
public void testeComDados(String usuario, String senha) {}
```

---

### 📅 Exemplo com vários parâmetros combinados

```java
@Test(
    priority = 2,
    groups = {"checkout"},
    dependsOnMethods = {"login"},
    timeOut = 3000,
    invocationCount = 2
)
public void realizarCompra() {
    // Teste de compra
}
```

---

### ✅ Dicas de uso

* Use `priority` apenas se a ordem de execução for relevante (ex: fluxo dependente).
* `dependsOnMethods` e `dependsOnGroups` ajudam a organizar testes encadeados.
* `enabled = false` é melhor do que comentar um teste.
* `invocationCount` é ótimo para testes de estabilidade ou carga.
* Combine com `@DataProvider` para testes dinâmicos.

---

## 📑 Exemplo de `testng.xml` completo

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="SuiteCompleta" parallel="classes" thread-count="3">
  <parameter name="browser" value="chrome"/>

  <test name="TestesDeRegressao">
    <groups>
      <run>
        <include name="regression"/>
      </run>
    </groups>
    <packages>
      <package name="tests"/>
    </packages>
  </test>

  <test name="TestesSmoke">
    <groups>
      <run>
        <include name="smoke"/>
      </run>
    </groups>
    <classes>
      <class name="tests.LoginTest"/>
    </classes>
  </test>
</suite>
```

---
