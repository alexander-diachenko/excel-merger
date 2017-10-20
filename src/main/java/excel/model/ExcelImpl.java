package excel.model;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.SheetUtil;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class ExcelImpl implements Excel {

    /**
     * Return list representation of excel file.
     *
     * @param path Path to excel file.
     * @return List representation of excel file.
     * @throws IOException            Throws IOException if file read failed.
     * @throws InvalidFormatException Throws InvalidFormatException if it is not excel file(.xls or .xlsx).
     */
    @Override
    public List<List<Object>> read(final String path) throws IOException, InvalidFormatException {
        final List<List<Object>> table = new ArrayList<>();
        final Workbook workbook = getWorkbook(path);
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            final Sheet sheet = workbook.getSheetAt(sheetIndex);
            table.addAll(getTableFromSheet(sheet));
        }
        return table;
    }

    private Workbook getWorkbook(final String path) throws IOException, InvalidFormatException {
        try (final FileInputStream is = new FileInputStream(new File(path))) {
            return WorkbookFactory.create(is);
        }
    }

    private List<List<Object>> getTableFromSheet(final Sheet sheet) {
        final List<List<Object>> table = new ArrayList<>();
        final Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            table.add(getRaw(rows));
        }
        return table;
    }

    private List<Object> getRaw(final Iterator rows) {
        final List<Object> raw = new ArrayList<>();
        final Row row = (Row) rows.next();
        int index = 0;
        final short lastCellNum = row.getLastCellNum();
        while (index < lastCellNum) {
            raw.add(getCell(row, index++));
        }
        return raw;
    }

    private String getCell(final Row row, final int index) {
        final Cell cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        return getFormattedCell(cell);
    }

    private String getFormattedCell(final Cell cell) {
        final DataFormatter df = new DataFormatter();
        final String result = df.formatCellValue(cell);
        return !result.equals("#NULL!") ? result : "";
    }

    /**
     * Write List<List<>> to excel file.
     *
     * @param table Data in List<List<>>.
     * @param path  Path to new excel file.
     * @throws IOException Throws IOException if file write failed.
     */
    @Override
    public void write(final List<List<Object>> table, final String path) throws IOException {
        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("New sheet"));
        for (int rawIndex = 0; rawIndex < table.size(); rawIndex++) {
            final List<Object> raw = table.get(rawIndex);
            final Row row = sheet.createRow(rawIndex);
            for (int colIndex = 0; colIndex < raw.size(); colIndex++) {
                final Object obj = raw.get(colIndex);
                final Cell cell = row.createCell(colIndex);
                cell.setCellValue(String.valueOf(obj));
            }
        }
        autoResizeSheet(sheet);
        write(workbook, path);
    }

    private void write(final Workbook workbook, final String path) throws IOException {
        try (final FileOutputStream outputStream = new FileOutputStream(new File(path))) {
            workbook.write(outputStream);
        }
    }

    /**
     * Return sheet column count.
     *
     * @param sheet sheet of excel file.
     * @return int column count.
     */
    @Override
    public int getColumnCount(final Sheet sheet) {
        int columnCount = 0;
        for (int rowNumber = 0; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
            final Row row = sheet.getRow(rowNumber);
            final short lastCellNum = row.getLastCellNum();
            columnCount = Math.max(columnCount, lastCellNum);
        }
        return columnCount;
    }

    /**
     * Auto resize excel table. If column is empty - hide it
     *
     * @param sheet of table
     */
    @Override
    public void autoResizeSheet(final Sheet sheet) {
        for (int columnIndex = 0; columnIndex < getColumnCount(sheet); columnIndex++) {
            if (isEmpty(sheet, columnIndex)) {
                hideColumn(sheet, columnIndex);
            }
            sheet.autoSizeColumn(columnIndex);
        }
    }

    private boolean isEmpty(final Sheet sheet, final int index) {
        final double columnWidth = SheetUtil.getColumnWidth(sheet, index, true);
        return columnWidth < 2;
    }

    private void hideColumn(final Sheet sheet, final int index) {
        sheet.setColumnHidden(index, true);
    }
}
