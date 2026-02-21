package training.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import training.AbstractComponents.AbstractComponent;
import training.entities.Fruit;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FruitTablePage extends AbstractComponent {
  WebDriver driver;

  @FindBy(css = ".rdt_Table[role='table']")
  WebElement tableElement;

  @FindBy(css = "rdt_TableBody[role='rowgroup']")
  WebElement tableBody;

  @FindBy(css = "div[role='row'].rdt_TableRow")
  List<WebElement> tableRows;

  public FruitTablePage(WebDriver driver) {
    super(driver);
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }

  public List<Fruit> getFruits() throws ParseException {
    List<Fruit> fruits = new ArrayList<>();

    for (WebElement row : tableRows) {
      List<WebElement> cellsOfARow = row.findElements(By.cssSelector("div[role='cell'].rdt_TableCell > div"));
      Integer number = Integer.parseInt(cellsOfARow.get(0).getText());
      String name = cellsOfARow.get(1).getText();
      String color = cellsOfARow.get(2).getText();
      Number price = NumberFormat.getNumberInstance().parse(cellsOfARow.get(3).getText());
      String Season = cellsOfARow.get(4).getText();

      fruits.add(new Fruit(number, name, color, price, Season));
    }

    return fruits;
  }
}
