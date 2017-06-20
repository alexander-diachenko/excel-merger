import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class ReadWriteExcelImpl implements ReadWriteExcel {

    @Override
    public List<List<Object>> read(final String path) {
        final List<List<Object>> table = new ArrayList<>();
        try {
            final Workbook workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
            final Sheet datatypeSheet = workbook.getSheetAt(0);
            for (Row aDatatypeSheet : datatypeSheet) {
                final List<Object> row = new ArrayList<>();
                for (Cell currentCell : aDatatypeSheet) {
                    final Object cell;
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        cell = currentCell.getStringCellValue();
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        cell = String.valueOf(currentCell.getNumericCellValue());
                    } else {
                        cell = "";
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
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        int rowNum = 0;
        for (List<Object> rows : table) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object obj : rows) {
                Cell cell = row.createCell(colNum++);
                if (obj instanceof Date)
                    cell.setCellValue((Date) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(new File(path));
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}