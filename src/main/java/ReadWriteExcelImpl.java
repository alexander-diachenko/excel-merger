import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class ReadWriteExcelImpl implements ReadWriteExcel {

    @Override
    public List<List<Object>> read(final String path) {
        final List<List<Object>> table = new ArrayList<>();
        try {
            final XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(path)));
            final XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            final Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                final List<Object> raw = new ArrayList<>();
                row = (XSSFRow) rows.next();
                for (int index = 0; index < row.getLastCellNum(); index++) {
                    cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    final DataFormatter df = new DataFormatter();
                    final String valueAsString = df.formatCellValue(cell);
                    raw.add(valueAsString);
                }
                table.add(raw);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    @Override
    public void write(final List<List<Object>> table, final String path) {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
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
                else if (obj instanceof BigInteger)
                    cell.setCellValue(String.valueOf(obj));
            }
        }

        try {
            final FileOutputStream outputStream = new FileOutputStream(new File(path));
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}