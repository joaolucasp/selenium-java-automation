package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class DataDriven {
  public static void main(String[] args) throws IOException {
    List<TestData> data = DataDriven.getData("page1");

    for (int i = 0; i < data.size(); i++) {
      System.out.println(data.get(i).getUsername() + " " + data.get(i).getPassword() + " " + data.get(i).getExpectedMessage());
    }
  }

  public static List<TestData> getData(String sheetName) throws IOException {
    String path = "C:\\Users\\jluca\\Downloads\\selenium-test.xlsx";

    try (FileInputStream in = new FileInputStream(path);
      Workbook workbook = new XSSFWorkbook(in)) {

      Sheet sheet = workbook.getSheet(sheetName);
      if (sheet == null) throw new IllegalArgumentException("Sheet %s não existe".formatted(sheetName));

      DataFormatter fmt = new DataFormatter();
      FormulaEvaluator eval = workbook.getCreationHelper().createFormulaEvaluator();

      Map<String, Integer> cols = headerToIndex(sheet.getRow(0), fmt, eval);

      List<TestData> list = new ArrayList<>();
      int lastRow = sheet.getLastRowNum();

      for (int r = 1; r <= lastRow; r++) { // começa na linha 2 (index 1)
        Row row = sheet.getRow(r);
        if (row == null) continue;

        String username = get(row, cols, "username", fmt, eval);
        String password = get(row, cols, "password", fmt, eval);
        String expected = get(row, cols, "expectedMessage", fmt, eval);

        list.add(new TestData(username, password, expected));
      }

      return list;
    }
  }

  private static Map<String, Integer> headerToIndex(Row header, DataFormatter fmt, FormulaEvaluator eval) {
    if (header == null) throw new IllegalArgumentException("Header (linha 1) não encontrado");

    Map<String, Integer> cols = new HashMap<>();
    for (Cell cell : header) {
      String name = fmt.formatCellValue(cell, eval).trim();
      if (!name.isEmpty()) cols.put(name, cell.getColumnIndex());
    }
    return cols;
  }

  private static String get(Row row, Map<String, Integer> cols, String colName,
                            DataFormatter fmt, FormulaEvaluator eval) {
    Integer idx = cols.get(colName);
    if (idx == null) return ""; // coluna não existe
    Cell cell = row.getCell(idx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
    return (cell == null) ? "" : fmt.formatCellValue(cell, eval).trim();
  }
}
