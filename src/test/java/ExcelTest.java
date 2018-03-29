import excel.model.Excel;
import excel.model.ExcelImpl;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class ExcelTest {

    private final Excel excel = new ExcelImpl();

    @Test
    public void readExcelTest_oneField() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/oneField.xlsx"));
        Assert.assertEquals("[[SAG060003, AGENT PROVOCATEUR FATALE EDP 50 ml spray, 6, 3760264453741]]", table.toString());
    }

    @Test
    public void readExcelTest_oneField_Xls() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/oneField.xls"));
        Assert.assertEquals("[[SAZ010009, AZZARO CHROME EDT TESTER 100 ml spray]]", table.toString());
    }

    @Test
    public void readExcelTest_twoField() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/twoField.xlsx"));
        Assert.assertEquals(
                "[[SAB070001, ANNA SUI ROMANTICA EDT TESTER 75 ml spray  с крышкoй, 10], " +
                        "[SAB070002, ANNA SUI ROMANTICA EDT 30 ml spray, 42]]", table.toString());
    }

    @Test
    public void readExcelTest_oneField_withSpace() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/oneFieldWithSpace.xlsx"));
        Assert.assertEquals(
                "[[SBA030010, , 34]]", table.toString());
    }

    @Test
    public void writeExcelTest_oneField() throws IOException, InvalidFormatException {
        final List<List<Object>> table = createTable(
                createList("SBA160002", "8411061784273", "ANTONIO BANDERAS KING OF SEDUCTION  MAN EDT 100 ml spray", "100", "EDT", "М", "15,30")
        );
        final File tempFile = File.createTempFile("got-", ".xlsx");
        final String outputPath = tempFile.getAbsolutePath();

        excel.write(table, outputPath);

        Assert.assertEquals("[[SBA160002, 8411061784273, ANTONIO BANDERAS KING OF SEDUCTION  MAN EDT 100 ml spray, 100, EDT, М, 15,30]]", excel.read(outputPath).toString());
    }

    @Test
    public void writeExcelTest_twoField() throws IOException, InvalidFormatException {
        final List<List<Object>> table = createTable(
                createList("SBA160002", "8411061784273", "ANTONIO BANDERAS KING OF SEDUCTION  MAN EDT 100 ml spray", "100", "EDT", "М", "15,30"),
                createList("SAN020002", "8427395660206", "ANGEL SCHLESSER HOMME EDT 125 ml spray", "125", "EDT", "М", "16,40")
        );
        final File tempFile = File.createTempFile("got-", ".xlsx");
        final String outputPath = tempFile.getAbsolutePath();

        excel.write(table, outputPath);

        Assert.assertEquals(
                "[[SBA160002, 8411061784273, ANTONIO BANDERAS KING OF SEDUCTION  MAN EDT 100 ml spray, 100, EDT, М, 15,30], " +
                        "[SAN020002, 8427395660206, ANGEL SCHLESSER HOMME EDT 125 ml spray, 125, EDT, М, 16,40]]", excel.read(outputPath).toString());
    }

    @Test
    public void readExcelTest_twoField_different_size() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/twoFieldDifferentSizeFirstShorter.xlsx"));
        Assert.assertEquals(
                "[[SOT440001, 3760260453042], " +
                        "[SOT190003, 3760260451994, 50 ml, U]]", table.toString());
    }

    @Test
    public void readExcelTest_oneField_withSpaces_beginningAndMiddle() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/oneFieldSpacesAtBeginningAndAtMiddle.xlsx"));
        Assert.assertEquals(
                "[[, , SOT440001, , , 3760260453042]]", table.toString());
    }

    @Test
    public void readExcelTest_twoField_different_size2() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/twoFieldDifferentSizeSecondShorter.xlsx"));
        Assert.assertEquals(
                "[[SOT190003, 3760260451994, 50 ml, U], " +
                        "[SOT440001, 3760260453042]]", table.toString());
    }

    @Test
    public void readExcelTest_threeField_different_size() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/threeFieldDifferentSize.xlsx"));
        Assert.assertEquals(
                "[[SOT190003, 3760260451994, 50 ml, U], " +
                        "[SOT440001, 3760260453042], " +
                        "[SOT470001, 3760260623042, 100 ml, M, EDP]]", table.toString());
    }

    @Test
    public void getColumnCountTest_ThreeFieldDifferentSize() throws IOException, InvalidFormatException {
        final String path = getFilePath("file/threeFieldDifferentSize.xlsx");
        final int columnCount = excel.getColumnCount(getSheet(path));
        Assert.assertEquals(5, columnCount);
    }

    @Test
    public void readExcelTest_fiveSheets() throws IOException, InvalidFormatException {
        final List<List<Object>> table = excel.read(getFilePath("file/fiveSheets.xlsx"));
        Assert.assertEquals(
                "[[SBA030010, , 34], " +
                        "[SOT190003, 3760260451994, 50 ml, U, ], " +
                        "[SOT440001, 3760260453042, , , ], " +
                        "[SOT470001, 3760260623042, 100 ml, M, EDP]]", table.toString());
    }

    private Sheet getSheet(final String path) throws IOException, InvalidFormatException {
        final Workbook workbook = getWorkbook(path);
        return workbook.getSheetAt(0);
    }

    private String getFilePath(final String fileName) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(fileName);
        if (resource != null) {
            return new File(resource.getFile()).getAbsolutePath();
        }
        return "";
    }

    private Workbook getWorkbook(final String path) throws IOException, InvalidFormatException {
        try (final FileInputStream is = new FileInputStream(new File(path))) {
            return WorkbookFactory.create(is);
        }
    }

    private List<Object> createList(final Object... objects) {
        return Arrays.asList(objects);
    }

    @SafeVarargs
    private final List<List<Object>> createTable(final List<Object>... lists) {
        return Arrays.asList(lists);
    }
}
