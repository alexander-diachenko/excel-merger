import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by User on 20.06.2017.
 */
public class ReadWriteExcelTest {

    @Test
    public void readExcelTest_oneField() {
        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();
        List<List<Object>> table = readWriteExcel.read(getFilePath("file/oneField.xlsx"));
        Assert.assertEquals("[[SAG060003, AGENT PROVOCATEUR FATALE EDP 50 ml spray, 6, 3760264453741]]", table.toString());
    }

    @Test
    public void readExcelTest_twoField() {
        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();
        List<List<Object>> table = readWriteExcel.read(getFilePath("file/twoField.xlsx"));
        Assert.assertEquals(
                "[[SAB070001, ANNA SUI ROMANTICA EDT TESTER 75 ml spray  с крышкoй, 10], " +
                        "[SAB070002, ANNA SUI ROMANTICA EDT 30 ml spray, 42]]", table.toString());
    }

    private String getFilePath(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getAbsolutePath();
    }
}
