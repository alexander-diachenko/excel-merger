import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class ReadWriteExcelImpl implements ReadWriteExcel {

    private Workbook workbook;

    @Override
    public List<List<Object>> read(final String path) {
        List<List<Object>> table = new ArrayList<>();
        try {
            workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
            Sheet datatypeSheet = workbook.getSheetAt(0);
            for (Row aDatatypeSheet : datatypeSheet) {
                List<Object> row = new ArrayList<>();
                for (Cell currentCell : aDatatypeSheet) {
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

    @Override
    public void write(final List<List<Object>> table, final String path) {
        Sheet sheet = workbook.createSheet("Datatypes in Java");
        int rowNum = 0;
        for (List<Object> rows : table) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object cells : rows) {
                Cell cell = row.createCell(colNum++);
                if (cells instanceof String) {
                    cell.setCellValue((String) cells);
                } else if (cells instanceof Integer) {
                    cell.setCellValue((Integer) cells);
                }
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}