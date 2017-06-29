package excel;

import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class ExcelMergerImpl implements ExcelMerger {

    private final List<List<Object>> from;
    private final List<List<Object>> to;

    /**
     * Create merger by given columns.
     * @param from List representation from excel file.
     * @param to List representation to excel file.
     */
    public ExcelMergerImpl(final List<List<Object>> from, final List<List<Object>> to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Return merged lists.
     * @param idColumnNumbers List<Integer>. First index number of id column from file. Second index number of id column to file.
     * @param mergeColumnNumbers List<Integer>.  First index number of merged column from file. Second index number of merged column to file.
     * @return Merged lists.
     */
    @Override
    public List<List<Object>> mergeOneField(final List<Integer> idColumnNumbers, final List<Integer> mergeColumnNumbers) {
        final Integer idColumnFrom = idColumnNumbers.get(0);
        final Integer idColumnTo = idColumnNumbers.get(1);
        final Integer mergeColumnFrom = mergeColumnNumbers.get(0);
        final Integer mergeColumnTo = mergeColumnNumbers.get(1);
        for (List<Object> rawTo : to) {
            final String idTo = String.valueOf(rawTo.get(idColumnTo));
            for (List<Object> rawFrom : from) {
                if (rawFrom.size() > idColumnFrom) {
                    final String idFrom = String.valueOf(rawFrom.get(idColumnFrom));
                    if (idTo.equals(idFrom) && rawFrom.size() > mergeColumnFrom) {
                        while (rawTo.size() <= mergeColumnTo) {
                            rawTo.add("");
                        }
                        rawTo.set(mergeColumnTo, rawFrom.get(mergeColumnFrom));
                        break;
                    }
                }
            }
        }
        return to;
    }
}
