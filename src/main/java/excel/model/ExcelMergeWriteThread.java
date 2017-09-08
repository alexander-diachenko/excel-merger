package excel.model;


import excel.Util.ThreadListener;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Alexander Diachenko
 */
public class ExcelMergeWriteThread implements Runnable {

    private final Excel excel;
    private final List<Integer> articles;
    private final List<Integer> fields;
    private final File fileFrom;
    private final File fileTo;
    private final String path;
    private final Set<ThreadListener> listeners = new CopyOnWriteArraySet<>();
    private Color textColor;
    private String text;

    public ExcelMergeWriteThread(final Excel excel, final List<Integer> articles, final List<Integer> fields,
                                 final File fileFrom, final File fileTo, final String path) {
        this.excel = excel;
        this.articles = articles;
        this.fields = fields;
        this.fileFrom = fileFrom;
        this.fileTo = fileTo;
        this.path = path;
    }

    public final void addListener(final ThreadListener listener) {
        listeners.add(listener);
    }
    public final void removeListener(final ThreadListener listener) {
        listeners.remove(listener);
    }
    private final void notifyListeners() {
        for (ThreadListener listener : listeners) {
            listener.notifyOfThread(new Thread(this));
        }
    }

    @Override
    public void run() {
        try {
            final List<List<Object>> from = excel.read(fileFrom.getAbsolutePath());
            final List<List<Object>> to = excel.read(fileTo.getAbsolutePath());
            final ExcelMerger excelMerger = new ExcelMergerImpl(from, to);
            final List<List<Object>> merged = excelMerger.mergeOneField(articles, fields);
            excel.write(merged, path);
            textColor = Color.GREEN;
            text = "DONE!";
            notifyListeners();
        } catch (Exception e) {
            textColor = Color.RED;
            text = e.getMessage();
            e.printStackTrace();
        }
    }

    public Color getTextColor() {
        return textColor;
    }

    public String getText() {
        return text;
    }
}
