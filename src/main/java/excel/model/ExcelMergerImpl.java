package excel.model;

import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class ExcelMergerImpl implements ExcelMerger {

    /**
     * Return merged lists.
     * @param from List representation from excel file.
     * @param to List representation to excel file.
     * @param fromColumns List<Integer>. First index number of id column from file. Second index number of merged column from file.
     * @param toColumns List<Integer>.  First index number of id column to file. Second index number of merged column to file.
     * @return Merged lists.
     */
    @Override
    public List<List<Object>> mergeOneField(final List<List<Object>> from, final List<List<Object>> to, final List<Integer> fromColumns, final List<Integer> toColumns) {
        final Integer idColumnFrom = fromColumns.get(0);
        final Integer mergeColumnFrom = fromColumns.get(1);
        final Integer idColumnTo = toColumns.get(0);
        final Integer mergeColumnTo = toColumns.get(1);
        for (List<Object> rawTo : to) {
            for (List<Object> rawFrom : from) {
                if (isCorrectInput(idColumnFrom, idColumnTo, rawFrom.size(), rawTo.size(), mergeColumnFrom)) {
                    final String idTo = String.valueOf(rawTo.get(idColumnTo));
                    final String idFrom = String.valueOf(rawFrom.get(idColumnFrom));
                    if (idTo.equals(idFrom) && !idTo.isEmpty()) {
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

    private boolean isCorrectInput(final Integer idColumnFrom, final Integer idColumnTo,
                                   final Integer rawFromSize, final Integer rawToSize,
                                   final Integer mergeColumnFrom) {
        return rawFromSize > idColumnFrom &&
                rawToSize > idColumnTo &&
                rawFromSize > mergeColumnFrom;
    }
}
