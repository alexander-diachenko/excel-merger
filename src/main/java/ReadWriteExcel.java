import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class ReadWriteExcel {
    private static final String FILE_NAME = "D:\\Downloads\\test.xlsx";
    Workbook workbook = new XSSFWorkbook();

    public List<List<Object>> read() {
        List<List<Object>> table = new ArrayList<>();
        FileInputStream excelFile;
        try {
            excelFile = new FileInputStream(new File(FILE_NAME));
            workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);

            for (Row aDatatypeSheet : datatypeSheet) {
                List<Object> row = new ArrayList<>();
                for (Cell currentCell : aDatatypeSheet) {
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    Object cell = new Object();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        cell = currentCell.getStringCellValue();
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        cell = currentCell.getNumericCellValue();
                    }
                    row.add(cell);
                }
                table.add(row);
            }
            return table;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void write(List<List<Object>> table) {

        Sheet sheet = workbook.createSheet("Datatypes in Java");
        int rowNum = 0;
        for (int rowIndex = 0; rowIndex < table.size(); rowIndex++) {
            List<Object> rows = table.get(rowIndex);
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (int cellIndex = 0; cellIndex < rows.size(); cellIndex++) {
                Object cell1 = rows.get(cellIndex);
                Cell cell = row.createCell(colNum++);
                if (cell1 instanceof String) {
                    cell.setCellValue((String) cell1);
                } else if (cell1 instanceof Integer) {
                    cell.setCellValue((Integer) cell1);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream("D:\\Downloads\\updated.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}