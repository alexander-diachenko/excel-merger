package excel;

import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public interface ExcelMerger {
    /**
     * Return merged lists.
     * @param articles List<Integer>. First index number of id column from file. Second index number of id column to file.
     * @param fields List<Integer>.  First index number of merged column from file. Second index number of merged column to file.
     * @return Merged lists.
     */
    List<List<Object>> mergeOneField(final List<Integer> articles, final List<Integer> fields);
}
