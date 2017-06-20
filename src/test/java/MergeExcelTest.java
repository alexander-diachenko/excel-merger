import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 20.06.2017.
 */
public class MergeExcelTest {

    private MergeExcel mergeExcel;

    @Test
    public void mergeTest_oneField() {
        List<List<Object>> tableTo = new ArrayList<>();
        tableTo.add(Arrays.asList("SAN030003", ""));

        List<List<Object>> tableFrom = new ArrayList<>();
        List<Object> rawFrom = Arrays.asList("SAN030003", 3);
        tableFrom.add(rawFrom);

        List<Integer> articles = Arrays.asList(0, 0);
        List<Integer> fields = Arrays.asList(1, 1);
        mergeExcel = new MergeExcelImpl(tableFrom, tableTo, articles, fields);
        List<List<Object>> tableMerged = mergeExcel.merge();
        Assert.assertEquals("[[SAN030003, 3]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_twoField_tableTo() {
        List<List<Object>> tableTo = new ArrayList<>();
        List<Object> rawTo1 = Arrays.asList("SAN040003", "");
        List<Object> rawTo2 = Arrays.asList("SAN030003", "");
        tableTo.add(rawTo1);
        tableTo.add(rawTo2);

        List<List<Object>> tableFrom = new ArrayList<>();
        List<Object> rawFrom = Arrays.asList("SAN030003", 3);
        tableFrom.add(rawFrom);

        List<Integer> articles = Arrays.asList(0, 0);
        List<Integer> fields = Arrays.asList(1, 1);
        mergeExcel = new MergeExcelImpl(tableFrom, tableTo, articles, fields);
        List<List<Object>> tableMerged = mergeExcel.merge();
        Assert.assertEquals("[[SAN040003, ], [SAN030003, 3]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_twoField_tableFrom() {
        List<List<Object>> tableTo = new ArrayList<>();
        List<Object> rawTo = Arrays.asList("SAN030003", "");
        tableTo.add(rawTo);

        List<List<Object>> tableFrom = new ArrayList<>();
        List<Object> rawFrom1 = Arrays.asList("SAN040003", 3);
        List<Object> rawFrom2 = Arrays.asList("SAN030003", 3);
        tableFrom.add(rawFrom1);
        tableFrom.add(rawFrom2);

        List<Integer> articles = Arrays.asList(0, 0);
        List<Integer> fields = Arrays.asList(1, 1);
        mergeExcel = new MergeExcelImpl(tableFrom, tableTo, articles, fields);
        List<List<Object>> tableMerged = mergeExcel.merge();
        Assert.assertEquals("[[SAN030003, 3]]", tableMerged.toString());
    }
}
