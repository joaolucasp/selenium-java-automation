package excel;

public class TestData {
  private String username;
  private String password;
  private String expectedMessage;

  public TestData(String username, String password, String expectedMessage) {
    this.username = username;
    this.password = password;
    this.expectedMessage = expectedMessage;
  }

  public String getUsername() { return username; }
  public String getPassword() { return password; }
  public String getExpectedMessage() { return expectedMessage; }
}
