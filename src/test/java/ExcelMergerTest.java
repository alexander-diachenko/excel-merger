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

    private final ExcelMerger excelMerger = new ExcelMergerImpl();

    @Test
    public void mergeTest_count_oneField() {
        final List<List<Object>> tableFrom = createTable(createList("SAN030003", 3));
        final List<List<Object>> tableTo = createTable(createList("SAN030003", ""));
        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);

        Assert.assertEquals("[[SAN030003, 3]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_count_twoField_tableTo() {
        final List<List<Object>> tableFrom = createTable(createList("SAN030003", 3));
        final List<List<Object>> tableTo = createTable(createList("SAN040003", ""), createList("SAN030003", ""));
        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);

        Assert.assertEquals("[[SAN040003, ], [SAN030003, 3]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_count_twoField_tableFrom() {
        final List<List<Object>> tableFrom = createTable(createList("SAN040003", 3), createList("SAN030003", 3));
        final List<List<Object>> tableTo = createTable(createList("SAN030003", ""));
        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);

        Assert.assertEquals("[[SAN030003, 3]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_barcode_oneField() {
        final List<List<Object>> tableFrom = createTable(createList("SAN030003", "1234567891234"));
        final List<List<Object>> tableTo = createTable(createList("SAN030003", ""));
        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);

        Assert.assertEquals("[[SAN030003, 1234567891234]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_To_ColumnNull() {
        final List<List<Object>> tableFrom = createTable(createList("SAN030003", "1234567891234"));
        // TODO придумать как сделать createList() без java.lang.UnsupportedOperationException
        final List<Object> row = new ArrayList<>();
        row.add("SAN030003");
        final List<List<Object>> tableTo = createTable(row);
        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);

        Assert.assertEquals("[[SAN030003, 1234567891234]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_From_ColumnNull() {
        final List<List<Object>> tableFrom = createTable(createList("SAN030003"));
        final List<List<Object>> tableTo = createTable(createList("SAN030003"));
        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);

        Assert.assertEquals("[[SAN030003]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_noCoincidence() {
        final List<List<Object>> tableFrom = createTable(createList("SAN030004", 3));
        final List<List<Object>> tableTo = createTable(createList("SAN030003"));
        final List<Integer> articles = Arrays.asList(0, 0);
        final List<Integer> fields = Arrays.asList(1, 1);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);

        Assert.assertEquals("[[SAN030003]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_To_idColumnHigherToSize() {
        final List<List<Object>> tableFrom = createTable(createList("SAN030004", 3));
        final List<List<Object>> tableTo = createTable(createList("SAN030004"));
        final List<Integer> articles = Arrays.asList(0, 1);
        final List<Integer> fields = Arrays.asList(1, 2);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);

        Assert.assertEquals("[[SAN030004]]", tableMerged.toString());
    }

    @Test
    public void mergeTest_oneField_To_idColumnHigherFromSize() {
        final List<List<Object>> tableFrom = createTable(createList("SAN030004"));
        final List<List<Object>> tableTo = createTable(createList("SAN030004"));
        final List<Integer> articles = Arrays.asList(0, 1);
        final List<Integer> fields = Arrays.asList(1, 2);

        final List<List<Object>> tableMerged = excelMerger.mergeOneField(tableFrom, tableTo, articles, fields);
        Assert.assertEquals("[[SAN030004]]", tableMerged.toString());
    }

    private List<Object> createList(final Object... objects) {
        return Arrays.asList(objects);
    }

    @SafeVarargs
    private final List<List<Object>> createTable(final List<Object>... lists) {
        return Arrays.asList(lists);
    }
}
