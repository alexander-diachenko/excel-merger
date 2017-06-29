package excel;

import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public interface ExcelMerger {
    /**
     * Return merged lists.
     * @param idColumnNumbers List<Integer>. First index number of id column from file. Second index number of id column to file.
     * @param mergeColumnNumbers List<Integer>.  First index number of merged column from file. Second index number of merged column to file.
     * @return Merged lists.
     */
    List<List<Object>> mergeOneField(final List<Integer> idColumnNumbers, final List<Integer> mergeColumnNumbers);
}
