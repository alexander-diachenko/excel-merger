package excel;

import java.util.List;

/**
 * Created by User on 19.06.2017.
 */
public class ExcelMergerImpl implements ExcelMerger {

    private final List<List<Object>> from;
    private final List<List<Object>> to;

    public ExcelMergerImpl(final List<List<Object>> from, final List<List<Object>> to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<List<Object>> mergeOneField(final List<Integer> idColumnNumber, final List<Integer> mergeColumnNumber) {
        final Integer idColumnFrom = idColumnNumber.get(0);
        final Integer idColumnTo = idColumnNumber.get(1);
        final Integer mergeColumnFrom = mergeColumnNumber.get(0);
        final Integer mergeColumnTo = mergeColumnNumber.get(1);
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
