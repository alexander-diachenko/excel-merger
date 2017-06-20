import java.util.List;

/**
 * Created by User on 19.06.2017.
 */
public class MergeExcelImpl implements MergeExcel {

    private final List<List<Object>> from;
    private final List<List<Object>> to;
    private final List<Integer> articles;
    private final List<Integer> fields;

    public MergeExcelImpl(final List<List<Object>> from, final List<List<Object>> to, final List<Integer> articles, final List<Integer> fields) {
        this.from = from;
        this.to = to;
        this.articles = articles;
        this.fields = fields;
    }

    @Override
    public List<List<Object>> merge() {
        final Integer articleColumnFrom = articles.get(1);
        final Integer articleColumnTo = articles.get(0);
        final Integer mergeColumnFrom = fields.get(1);
        final Integer mergeColumnTo = fields.get(0);
        for (List<Object> rawTo : to) {
            final String articleTo = String.valueOf(rawTo.get(articleColumnTo));
            for (List<Object> rawFrom : from) {
                final String articleFrom = String.valueOf(rawFrom.get(articleColumnFrom));
                if (articleTo.equals(articleFrom)) {
                    rawTo.set(mergeColumnTo, rawFrom.get(mergeColumnFrom));
                    break;
                } else {
                    rawTo.set(mergeColumnTo, "");
                }
            }
        }
        return to;
    }
}
