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
    public List<List<Object>> mergeOneField(final List<Integer> ids, final List<Integer> fields) {
        final Integer idColumnFrom = ids.get(0);
        final Integer idColumnTo = ids.get(1);
        final Integer mergeColumnFrom = fields.get(0);
        final Integer mergeColumnTo = fields.get(1);
        for (List<Object> rawTo : to) {
            final String idTo = String.valueOf(rawTo.get(idColumnTo));
            for (List<Object> rawFrom : from) {
                final String idFrom = String.valueOf(rawFrom.get(idColumnFrom));
                if (idTo.equals(idFrom)) {
                    while (rawTo.size() <= mergeColumnTo) {
                        rawTo.add("");
                    }
                    rawTo.set(mergeColumnTo, rawFrom.get(mergeColumnFrom));
                    break;
                }
            }
        }
        return to;
    }
}
