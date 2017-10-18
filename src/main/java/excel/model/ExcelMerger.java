package excel.model;

import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public interface ExcelMerger {
    /**
     * Return merged lists.
     * @param from List representation from excel file.
     * @param to List representation to excel file.
     *@param idColumnNumbers List<Integer>. First index number of id column from file. Second index number of id column to file.
     * @param mergeColumnNumbers List<Integer>.  First index number of merged column from file. Second index number of merged column to file.   @return Merged lists.
     */
    List<List<Object>> mergeOneField(final List<List<Object>> from, final List<List<Object>> to, final List<Integer> idColumnNumbers, final List<Integer> mergeColumnNumbers);
}
