import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class Article {
    public List<List<Object>> renameArticle(List<List<Object>> table) {
        for (List<Object> row : table) {
            for (int index = 0; index < row.size(); index++) {
                Object cell = row.get(index);
                String cellToString = String.valueOf(cell);
                String[] split = cellToString.split("_");
                String article = split[0];
                row.set(index, article);
            }
        }
        return table;
    }
}
