package excel.components.formatterTab.formattThread;

import excel.Util.ExcelUtil;
import excel.model.Excel;
import excel.model.Observer;
import excel.model.Subject;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alexander Diachenko
 */
public class ExcelFormatWriteThread implements Runnable, Subject {

    private final Excel excel;
    private final List<File> files;
    private final String columnNumber;
    private final String columnValue;
    private final Set<Observer> observers = new HashSet<>();
    private Color textColor;
    private String text;

    public ExcelFormatWriteThread(final Excel excel, final List<File> files, final String columnNumber, final String columnValue) {
        this.excel = excel;
        this.files = files;
        this.columnNumber = columnNumber;
        this.columnValue = columnValue;
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
            for (File file : files) {
                if (!ExcelUtil.isExcel(file)) {
                    continue;
                }
                final List<List<Object>> table = excel.read(file.getPath());
                if (isCorrect(columnNumber, columnValue)) {
                    insert(table, Integer.parseInt(columnNumber), columnValue);
                }
                excel.write(table, file.getPath());
            }
            setText(Color.GREEN, "DONE!");
        } catch (Exception e) {
            setText(Color.RED, e.getMessage());
            e.printStackTrace();
        } finally {
            notifyObservers();
        }
    }

    private void setText(final Color color, final String message) {
        textColor = color;
        text = message;
    }

    private boolean isCorrect(final String columnNumber, final String columnValue) {
        return !columnValue.isEmpty() && !columnNumber.isEmpty() && Integer.parseInt(columnNumber) > 0;
    }

    private void insert(final List<List<Object>> table, final int columnNumber, final String columnValue) {
        final int index = columnNumber - 1;
        for (List<Object> row : table) {
            if (row.size() > index) {
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
