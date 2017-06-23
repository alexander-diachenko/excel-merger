import java.util.List;

/**
 * Created by User on 19.06.2017.
 */
public class MergeExcelImpl implements MergeExcel {

    private final List<List<Object>> from;
    private final List<List<Object>> to;

    public MergeExcelImpl(final List<List<Object>> from, final List<List<Object>> to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<List<Object>> mergeOneField(final List<Integer> articles, final List<Integer> fields) {
        final Integer articleColumnFrom = articles.get(0);
        final Integer articleColumnTo = articles.get(1);
        final Integer mergeColumnFrom = fields.get(0);
        final Integer mergeColumnTo = fields.get(1);
        for (List<Object> rawTo : to) {
            final String articleTo = String.valueOf(rawTo.get(articleColumnTo));
            for (List<Object> rawFrom : from) {
                final String articleFrom = String.valueOf(rawFrom.get(articleColumnFrom));
                if (articleTo.equals(articleFrom)) {
                    if (rawTo.size() > mergeColumnTo) {
                        rawTo.set(mergeColumnTo, rawFrom.get(mergeColumnFrom));
                    } else {
                        rawTo.add(mergeColumnTo, rawFrom.get(mergeColumnFrom));
                    }
                    break;
                }
            }
        }
        return to;
    }
}
