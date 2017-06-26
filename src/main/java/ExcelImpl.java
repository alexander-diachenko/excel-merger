import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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
    public List<List<Object>> read(final String path) throws IOException, InvalidFormatException {
        final List<List<Object>> table = new ArrayList<>();
        final Workbook workbook = getWorkbook(path);
        final Sheet sheet = workbook.getSheetAt(0);
        final Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            final List<Object> raw = new ArrayList<>();
            final Row row = (Row) rows.next();
            final short lastCellNum = row.getLastCellNum();
            int index = 0;
            while (index < getWorkbookSize(path)) {
                if (index < lastCellNum) {
                    final Cell cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    raw.add(getFormattedCell(cell));
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
    public int getWorkbookSize(final String path) throws IOException, InvalidFormatException {
        final Workbook workbook = getWorkbook(path);
        final Sheet sheet = workbook.getSheetAt(0);
        final Iterator rows = sheet.rowIterator();
        short workbookSize = 0;
        while (rows.hasNext()) {
            final Row row = (Row) rows.next();
            final short lastCellNum = row.getLastCellNum();
            if (workbookSize < lastCellNum) {
                workbookSize = lastCellNum;
            }
        }
        return workbookSize;
    }

    @Override
    public void write(final List<List<Object>> table, final String path) throws IOException {
        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Datatypes in Java");
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
        try (FileOutputStream outputStream = new FileOutputStream(new File(path))) {
            workbook.write(outputStream);
        }
    }

    private String getFormattedCell(Cell cell) {
        final DataFormatter df = new DataFormatter();
        return df.formatCellValue(cell);
    }

    private Workbook getWorkbook(String path) throws IOException, InvalidFormatException {
        try (FileInputStream is = new FileInputStream(new File(path))) {
            return WorkbookFactory.create(is);
        }
    }
}
