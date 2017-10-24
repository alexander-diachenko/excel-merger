package excel.model;


import javafx.scene.paint.Color;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alexander Diachenko
 */
public class ExcelMergeWriteThread implements Runnable, Subject {

    private final Excel excel;
    private final List<Integer> articles;
    private final List<Integer> fields;
    private final File fileFrom;
    private final File fileTo;
    private final String path;
    private final Set<Observer> observers = new HashSet<>();
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

    @Override
    public final void registerObserver(final Observer observer) {
        observers.add(observer);
    }

    @Override
    public final void removeObserver(final Observer observer) {
        observers.remove(observer);
    }

    @Override
    public final void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void run() {
        try {
            final List<List<Object>> from = excel.read(fileFrom.getAbsolutePath());
            final List<List<Object>> to = excel.read(fileTo.getAbsolutePath());
            final ExcelMerger excelMerger = new ExcelMergerImpl();
            final List<List<Object>> merged = excelMerger.mergeOneField(from, to, articles, fields);
            excel.write(merged, path);
            setText(Color.GREEN, "DONE!");
        } catch (Exception e) {
            setText(Color.RED, e.getMessage());
            e.printStackTrace();
        } finally {
            notifyObservers();
        }
    }

    private void setText(Color color, String message) {
        textColor = color;
        text = message;
    }

    public Color getTextColor() {
        return textColor;
    }

    public String getText() {
        return text;
    }
}
