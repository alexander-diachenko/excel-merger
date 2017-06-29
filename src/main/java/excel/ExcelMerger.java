package excel;

import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public interface ExcelMerger {
    List<List<Object>> mergeOneField(final List<Integer> articles, final List<Integer> fields);
}
