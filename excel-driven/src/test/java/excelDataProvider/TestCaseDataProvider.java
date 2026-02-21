package excelDataProvider;

import excel.TestData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCaseDataProvider {

  @Test(dataProvider = "exampleData")
  public void testCaseData(TestData testData) throws IOException {
    System.out.println(testData.getUsername() + testData.getPassword() + testData.getExpectedMessage());
  }

  @DataProvider(name = "exampleData")
  public Object[][] getExampleData() throws IOException {
    return this.getData("page1");
  }

  public Object[][] getData(String sheetName) throws IOException {
    String path = "C:\\Users\\jluca\\Downloads\\selenium-test.xlsx";

    try (FileInputStream in = new FileInputStream(path);
         Workbook workbook = new XSSFWorkbook(in)) {

      Sheet sheet = workbook.getSheet(sheetName);
      if (sheet == null) throw new IllegalArgumentException("Sheet %s não existe".formatted(sheetName));

      DataFormatter fmt = new DataFormatter();
      FormulaEvaluator eval = workbook.getCreationHelper().createFormulaEvaluator();

      Map<String, Integer> cols = this.headerToIndex(sheet.getRow(0), fmt, eval);

      int lastRowNum = sheet.getLastRowNum();
      int dataSize = lastRowNum;

      Object[][] data = new Object[dataSize][1];// uma dimensão apenas pois enviamos um TestData type, ou seja, é um objeto, e não vários atributos "soltos"
      int index = 0;

      for (int r = 1; r <= lastRowNum; r++) { // começa na linha 2 (index 1)
        Row row = sheet.getRow(r);
        if (row == null) continue;

        String username = this.get(row, cols, "username", fmt, eval);
        String password = this.get(row, cols, "password", fmt, eval);
        String expected = this.get(row, cols, "expectedMessage", fmt, eval);

        data[index++][0] = new TestData(username, password, expected);
      }

      if (index < data.length) {
        Object[][] trimmed = new Object[index][1];
        System.arraycopy(data, 0, trimmed, 0, index);
        return trimmed;
      }

      return data;
    }
  }

  private Map<String, Integer> headerToIndex(Row header, DataFormatter fmt, FormulaEvaluator eval) {
    if (header == null) throw new IllegalArgumentException("Header (linha 1) não encontrado");

    Map<String, Integer> cols = new HashMap<>();
    for (Cell cell : header) {
      String name = fmt.formatCellValue(cell, eval).trim();
      if (!name.isEmpty()) cols.put(name, cell.getColumnIndex());
    }
    return cols;
  }

  private String get(Row row, Map<String, Integer> cols, String colName,
                            DataFormatter fmt, FormulaEvaluator eval) {
    Integer idx = cols.get(colName);
    if (idx == null) return ""; // coluna não existe
    Cell cell = row.getCell(idx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
    return (cell == null) ? "" : fmt.formatCellValue(cell, eval).trim();
  }
}
