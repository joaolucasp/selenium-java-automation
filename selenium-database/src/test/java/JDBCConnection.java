import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnection {
  public static void main(String[] args) throws SQLException {
    String host = "localhost";
    int port = 3306;
    String database = "qa_database";
    String user = "root";
    String password = "admin";

    Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
    Statement statement = connection.createStatement();

    ResultSet queryResult = statement.executeQuery("SELECT * FROM employees");
    List<Employee> employees = new ArrayList<>();

    while (queryResult.next()) {
      int id = queryResult.getInt("id");
      String name = queryResult.getString("name");
      String location = queryResult.getString("location");
      int age = queryResult.getInt("age");

      Employee emp = new Employee(id, name, location, age);
      employees.add(emp);
    }

    employees.forEach(System.out::println);

    queryResult.close();
  }
}