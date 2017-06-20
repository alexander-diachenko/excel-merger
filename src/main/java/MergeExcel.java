import java.util.List;

/**
 * Created by User on 20.06.2017.
 */
public interface MergeExcel {
    List<List<Object>> mergeOneField(final List<Integer> articles, final List<Integer> fields);
}
