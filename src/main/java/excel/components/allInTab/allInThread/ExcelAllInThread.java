package excel.components.allInTab.allInThread;

import excel.Util.ExcelUtil;
import excel.Util.TimeUtil;
import excel.model.Excel;
import excel.model.Observer;
import excel.model.Subject;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.*;

/**
 * @author Alexander Diachenko
 */
public class ExcelAllInThread implements Runnable, Subject {

    private final Excel excel;
    private final Set<Observer> observers = new HashSet<>();
    private Color textColor;
    private String text;
    private List<File> files;
    private String directoryPath;
    private String absolutePath;

    public ExcelAllInThread(final Excel excel, final List<File> files, final String directoryPath) {
        this.excel = excel;
        this.files = files;
        this.directoryPath = directoryPath;
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
        final List<List<Object>> tables = new LinkedList<>();
        try {
            for (File file : files) {
                if (!ExcelUtil.isExcel(file)) {
                    continue;
                }
                tables.addAll(excel.read(file.getAbsolutePath()));
            }
            absolutePath = directoryPath + "\\" + "AllIn_" + TimeUtil.getCurrentTime() + ".xlsx";
            excel.write(tables, absolutePath);
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

    public Color getTextColor() {
        return textColor;
    }

    public String getText() {
        return text;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
