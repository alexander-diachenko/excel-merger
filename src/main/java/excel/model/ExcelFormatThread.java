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
public class ExcelFormatThread implements Runnable {

    private final Excel excel;
    private final File file;
    private final String columnNumber;
    private final String columnValue;
    private final Set<ThreadListener> listeners = new CopyOnWriteArraySet<>();
    private Color textColor;
    private String text;

    public ExcelFormatThread(final Excel excel, final File file, final String columnNumber, final String columnValue) {
        this.excel = excel;
        this.file = file;
        this.columnNumber = columnNumber;
        this.columnValue = columnValue;
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
            final List<List<Object>> table = excel.read(file.getPath());
            if(isCorrect(columnNumber, columnValue)) {
                insert(table, Integer.parseInt(columnNumber), columnValue);
            }
            excel.write(table, file.getPath());
            setText(Color.GREEN, "DONE!");
        } catch (Exception e) {
            setText(Color.RED, e.getMessage());
            e.printStackTrace();
        } finally {
            notifyListeners();
        }
    }

    private void setText(Color color, String message) {
        textColor = color;
        text = message;
    }

    private boolean isCorrect(final String columnNumber, final String columnValue) {
        return !columnValue.isEmpty() && !columnNumber.isEmpty() && Integer.parseInt(columnNumber) > 0;
    }

    private void insert(final List<List<Object>> table, final int columnNumber, final String columnValue) {
        final int index = columnNumber - 1;
        for(List<Object> row : table) {
            if(row.size() > index) {
                row.add(index, columnValue);
            }
        }
    }

    public Color getTextColor() {
        return textColor;
    }

    public String getText() {
        return text;
    }
}
