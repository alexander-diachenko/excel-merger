import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 20.06.2017.
 */
public class ExcelTest {

    @Test
    public void readExcelTest_oneField() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = excel.read(getFilePath("file/oneField.xlsx"));
        Assert.assertEquals("[[SAG060003, AGENT PROVOCATEUR FATALE EDP 50 ml spray, 6, 3760264453741]]", table.toString());
    }

    @Test
    public void readExcelTest_oneField_Xls() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = excel.read(getFilePath("file/oneField.xls"));
        Assert.assertEquals("[[SAZ010009, AZZARO CHROME EDT TESTER 100 ml spray]]", table.toString());
    }

    @Test
    public void readExcelTest_twoField() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = excel.read(getFilePath("file/twoField.xlsx"));
        Assert.assertEquals(
                "[[SAB070001, ANNA SUI ROMANTICA EDT TESTER 75 ml spray  с крышкoй, 10], " +
                        "[SAB070002, ANNA SUI ROMANTICA EDT 30 ml spray, 42]]", table.toString());
    }

    @Test
    public void readExcelTest_oneField_withSpace() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = excel.read(getFilePath("file/oneFieldWithSpace.xlsx"));
        Assert.assertEquals(
                "[[SBA030010, , 34]]", table.toString());
    }

    @Test
    public void writeExcelTest_oneField() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = new ArrayList<>();
        List<Object> raw = Arrays.asList("SBA160002", "8411061784273", "ANTONIO BANDERAS KING OF SEDUCTION  MAN EDT 100 ml spray", "100", "EDT", "М", "15,30");
        table.add(raw);
        File got = File.createTempFile("got-", ".xlsx");
        String outputPath = got.getAbsolutePath();
        excel.write(table, outputPath);
        List<List<Object>> tempTable = excel.read(outputPath);
        Assert.assertEquals("[[SBA160002, 8411061784273, ANTONIO BANDERAS KING OF SEDUCTION  MAN EDT 100 ml spray, 100, EDT, М, 15,30]]", tempTable.toString());
    }

    @Test
    public void writeExcelTest_twoField() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = new ArrayList<>();
        List<Object> raw1 = Arrays.asList("SBA160002", "8411061784273", "ANTONIO BANDERAS KING OF SEDUCTION  MAN EDT 100 ml spray", "100", "EDT", "М", "15,30");
        List<Object> raw2 = Arrays.asList("SAN020002", "8427395660206", "ANGEL SCHLESSER HOMME EDT 125 ml spray", "125", "EDT", "М", "16,40");
        table.add(raw1);
        table.add(raw2);
        File got = File.createTempFile("got-", ".xlsx");
        String outputPath = got.getAbsolutePath();
        excel.write(table, outputPath);
        List<List<Object>> tempTable = excel.read(outputPath);
        Assert.assertEquals(
                "[[SBA160002, 8411061784273, ANTONIO BANDERAS KING OF SEDUCTION  MAN EDT 100 ml spray, 100, EDT, М, 15,30], " +
                        "[SAN020002, 8427395660206, ANGEL SCHLESSER HOMME EDT 125 ml spray, 125, EDT, М, 16,40]]", tempTable.toString());
    }

    @Test
    public void readExcelTest_twoField_different_size() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = excel.read(getFilePath("file/twoFieldDifferentSizeFirstShorter.xlsx"));
        Assert.assertEquals(
                "[[SOT440001, 3760260453042], " +
                        "[SOT190003, 3760260451994, 50 ml, U]]", table.toString());
    }

    @Test
    public void readExcelTest_twoField_different_size2() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = excel.read(getFilePath("file/twoFieldDifferentSizeSecondShorter.xlsx"));
        Assert.assertEquals(
                "[[SOT190003, 3760260451994, 50 ml, U], " +
                        "[SOT440001, 3760260453042]]", table.toString());
    }

    @Test
    public void readExcelTest_threeField_different_size() throws IOException, InvalidFormatException {
        Excel excel = new ExcelImpl();
        List<List<Object>> table = excel.read(getFilePath("file/threeFieldDifferentSize.xlsx"));
        Assert.assertEquals(
                "[[SOT190003, 3760260451994, 50 ml, U], " +
                        "[SOT440001, 3760260453042], " +
                        "[SOT470001, 3760260623042, 100 ml, M, EDP]]", table.toString());
    }

    private String getFilePath(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getAbsolutePath();
    }
}
