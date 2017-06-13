import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class Article {

    public List<List<Object>> renameArticle(List<List<Object>> table) {
        List<List<Object>> result = table;
        double number = 1.0;
        for (int i = 0; i < result.size(); i++) {
            List<Object> row = result.get(i);
            if (row.size() > 2 && row.get(0).toString().equals(String.valueOf(number))) {
                String article = String.valueOf(row.get(2));
                String[] split = article.split("_");
                row.set(2, split[0]);
                number++;
            }
        }
        return result;
    }
}
