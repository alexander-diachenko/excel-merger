import java.util.List;

/**
 * Created by User on 19.06.2017.
 */
public class MergeExcel {

    private final List<List<Object>> from;
    private final List<List<Object>> to;
    private List<Integer> fields;

    public MergeExcel(List<List<Object>> from, List<List<Object>> to, List<Integer> fields){
        this.from = from;
        this.to = to;
        this.fields = fields;
    }
    
    public List<List<Object>> merge() {
        for (List<Object> rawTo : to) {
            Integer articleColumnTo = fields.get(0);
            String articleTo = String.valueOf(rawTo.get(articleColumnTo));
            for(List<Object> rawFrom : from) {
                Integer articleColumnFrom = fields.get(1);
                String articleFrom = String.valueOf(rawFrom.get(articleColumnFrom));
                if(articleTo.equals(articleFrom)) {
                    Integer mergeColumnTo1 = fields.get(2);
                    Integer mergeColumn1From1 = fields.get(3);
                    rawTo.set(mergeColumnTo1, rawFrom.get(mergeColumn1From1));
                }
            }
        }
        return to;
    }
}
