package training.entities;

public class Fruit {
  Integer number;
  String name;
  String color;
  Number price;
  String Season;

  public Fruit(Integer number, String name, String color, Number price, String Season) {
    this.number = number;
    this.name = name;
    this.color = color;
    this.price = price;
    this.Season = Season;
  }

  public Integer getNumber() {
    return number;
  }
  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Number getPrice() {
    return price;
  }

  public void setPrice(Number price) {
    this.price = price;
  }

  public String getSeason() {
    return Season;
  }

  public void setSeason(String Season) {
    this.Season = Season;
  }
}
