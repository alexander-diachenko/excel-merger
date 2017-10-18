import excel.model.ExcelMerger;
import excel.model.ExcelMergerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class ExcelMergerTest {

    @Test
    public void mergeTest_count_oneField() {
        final List<List<Object>> tableTo = new ArrayList<>();
        tableTo.add(Arrays.asList("SAN030003", ""));

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rawFrom = Arrays.asList("SAN030003", 3);
        tableFrom.add(rawFrom);

        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030003, 3]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_count_twoField_tableTo() {
        final List<List<Object>> tableTo = new ArrayList<>();
        final List<Object> rawTo1 = Arrays.asList("SAN040003", "");
        final List<Object> rawTo2 = Arrays.asList("SAN030003", "");
        tableTo.add(rawTo1);
        tableTo.add(rawTo2);

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rawFrom = Arrays.asList("SAN030003", 3);
        tableFrom.add(rawFrom);

        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN040003, ], [SAN030003, 3]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_count_twoField_tableFrom() {
        final List<List<Object>> tableTo = new ArrayList<>();
        final List<Object> rawTo = Arrays.asList("SAN030003", "");
        tableTo.add(rawTo);

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rawFrom1 = Arrays.asList("SAN040003", 3);
        final List<Object> rawFrom2 = Arrays.asList("SAN030003", 3);
        tableFrom.add(rawFrom1);
        tableFrom.add(rawFrom2);

        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030003, 3]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_barcode_oneField() {
        final List<List<Object>> tableTo = new ArrayList<>();
        tableTo.add(Arrays.asList("SAN030003", ""));

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rawFrom = Arrays.asList("SAN030003", "1234567891234");
        tableFrom.add(rawFrom);

        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030003, 1234567891234]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_To_ColumnNull() {
        final List<List<Object>> tableTo = new ArrayList<>();
        List<Object> row = new ArrayList<>();
        row.add("SAN030003");
        tableTo.add(row);

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rawFrom = Arrays.asList("SAN030003", "1234567891234");
        tableFrom.add(rawFrom);

        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030003, 1234567891234]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_From_ColumnNull() {
        final List<List<Object>> tableTo = new ArrayList<>();
        final List<Object> rowTo = new ArrayList<>();
        rowTo.add("SAN030003");
        tableTo.add(rowTo);

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rowFrom = new ArrayList<>();
        rowFrom.add("SAN030003");
        tableFrom.add(rowFrom);

        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030003]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_noCoincidence() {
        final List<List<Object>> tableTo = new ArrayList<>();
        final List<Object> rowTo = new ArrayList<>();
        rowTo.add("SAN030003");
        tableTo.add(rowTo);

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rawFrom = Arrays.asList("SAN030004", 3);
        tableFrom.add(rawFrom);

        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030003]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_To_idColumnHigherToSize() {
        final List<List<Object>> tableTo = new ArrayList<>();
        final List<Object> rowTo = new ArrayList<>();
        rowTo.add("SAN030004");
        tableTo.add(rowTo);

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rawFrom = Arrays.asList("SAN030004", 3);
        tableFrom.add(rawFrom);

        final List<Integer> articles = Arrays.asList(0, 1);
        final List<Integer> fields = Arrays.asList(1, 2);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030004]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_To_idColumnHigherFromSize() {
        final List<List<Object>> tableTo = new ArrayList<>();
        final List<Object> rowTo = new ArrayList<>();
        rowTo.add("SAN030004");
        tableTo.add(rowTo);

        final List<List<Object>> tableFrom = new ArrayList<>();
        final List<Object> rowFrom = new ArrayList<>();
        rowFrom.add("SAN030004");
        tableFrom.add(rowFrom);

        final List<Integer> articles = Arrays.asList(0, 1);
        final List<Integer> fields = Arrays.asList(1, 2);
        final ExcelMerger excelMerger = new ExcelMergerImpl();
        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030004]]", tableMerged.toString());
    }
}
