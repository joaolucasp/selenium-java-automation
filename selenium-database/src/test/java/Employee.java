public class Employee {
  private int id;
  private String name;
  private String location;
  private int age;

  public Employee(int id, String name, String location, int age) {
    this.id = id;
    this.name = name;
    this.location = location;
    this.age = age;
  }

  @Override
  public String toString() {
    return id + " - " + name + " - " + location + " - " + age;
  }
}