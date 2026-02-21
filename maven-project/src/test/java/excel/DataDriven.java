package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class DataDriven {
  public static List<TestData> getData(String sheetName, String filePath) throws IOException {
    try (FileInputStream in = new FileInputStream(filePath);
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

  public static Integer getColumnNumber(String filePath, String sheetName, String columnName) throws IOException {
    try (FileInputStream in = new FileInputStream(filePath);
      Workbook workbook = new XSSFWorkbook(in)) {
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null)
          throw new IllegalArgumentException("Sheet %s não existe".formatted(sheetName));

        DataFormatter fmt = new DataFormatter();
        FormulaEvaluator eval = workbook.getCreationHelper().createFormulaEvaluator();

        Map<String, Integer> cols = headerToIndex(sheet.getRow(0), fmt, eval);
        return cols.get(columnName);
    }
  }

  public static Integer getRowNumber(String filePath, String sheetName, String rowName) throws IOException {
    try (FileInputStream in = new FileInputStream(filePath);
      Workbook workbook = new XSSFWorkbook(in)) {
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null)
          throw new IllegalArgumentException("Sheet %s não existe".formatted(sheetName));

      DataFormatter fmt = new DataFormatter();
      FormulaEvaluator eval = workbook.getCreationHelper().createFormulaEvaluator();

      Map<String, Integer> cols = headerToIndex(sheet.getRow(0), fmt, eval);
      int lastRow = sheet.getLastRowNum();
      boolean matchedRow = false;

      for (int r = 1; r <= lastRow; r++) { // começa na linha 2 (index 1)
        Row row = sheet.getRow(r);
        if (row == null) continue;

        String fruitName = get(row, cols, "fruit_name", fmt, eval);

        if (fruitName.equals(rowName)) {
          return r;
        }
      }

      if (!matchedRow) throw new IllegalArgumentException("Row not found");
    }

    return -1;
  }

  public static Boolean updateCell(String filePath, String sheetName, int rowNumber, int columnNumber, String updatedValue) throws IOException {
    try (FileInputStream in = new FileInputStream(filePath);
      Workbook workbook = new XSSFWorkbook(in)) {
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null)
          throw new IllegalArgumentException("Sheet %s não existe".formatted(sheetName));

        Row rowField = sheet.getRow(rowNumber);

        Cell cellField = rowField.getCell(columnNumber);
        cellField.setCellValue(updatedValue);

        FileOutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
        return true;
    }
  }
}
