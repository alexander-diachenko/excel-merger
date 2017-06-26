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
public class ExcelImpl implements Excel {

    @Override
    public List<List<Object>> read(final String path) throws ExcelException {
        final List<List<Object>> table = new ArrayList<>();
        final XSSFWorkbook workbook = getWorkbook(path);
        final XSSFSheet sheet = workbook.getSheetAt(0);
        final Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            final List<Object> raw = new ArrayList<>();
            final XSSFRow row = (XSSFRow) rows.next();
            final short lastCellNum = row.getLastCellNum();
            int index = 0;
            while (index < getWorkbookSize(path)) {
                if (index < lastCellNum) {
                    final XSSFCell cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    final DataFormatter df = new DataFormatter();
                    final String valueAsString = df.formatCellValue(cell);
                    raw.add(valueAsString);
                    index++;
                } else {
                    raw.add("");
                    index++;
                }
            }
            table.add(raw);
        }
        return table;
    }

    @Override
    public int getWorkbookSize(final String path) throws ExcelException {
        final XSSFWorkbook workbook = getWorkbook(path);
        final XSSFSheet sheet = workbook.getSheetAt(0);
        final Iterator rows = sheet.rowIterator();
        short workbookSize = 0;
        while (rows.hasNext()) {
            final XSSFRow row = (XSSFRow) rows.next();
            final short lastCellNum = row.getLastCellNum();
            if (workbookSize < lastCellNum) {
                workbookSize = lastCellNum;
            }
        }
        return workbookSize;
    }

    @Override
    public void write(final List<List<Object>> table, final String path) throws ExcelException {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        int rowNum = 0;
        for (List<Object> rows : table) {
            final Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object obj : rows) {
                final Cell cell = row.createCell(colNum++);
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
        FileOutputStream outputStream = null;
        try {
            outputStream = getFileOutputStream(path);
            workbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ExcelException(e.getMessage(), e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private XSSFWorkbook getWorkbook(String path) throws ExcelException {
        FileInputStream is = null;
        XSSFWorkbook workbook = null;
        try {
            is = getFileInputStream(path);
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExcelException(e.getMessage(), e);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return workbook;
    }

    private FileInputStream getFileInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(new File(path));
    }
    private FileOutputStream getFileOutputStream(String path) throws FileNotFoundException {
        return new FileOutputStream(new File(path));
    }
}
