package gui;

import events.EventHandler;

import java.awt.*;
import java.util.ArrayList;

public class GUITable extends GUIObject {

    static final int xMargin = 5;
    ArrayList<ArrayList<GUIObject>> tableRows; //list of rows

    public GUITable(int x, int y) {
        super();

        this.tableRows = new ArrayList<>();
        setPosition(x, y);
    }

    public GUITable(ArrayList<ArrayList<GUIObject>> rows) {
        super();

        this.tableRows = rows;
    }

    public void addRow(ArrayList<GUIObject> row) {
        this.tableRows.add(row);
    }

    @Override
    public void setHandler(EventHandler h) {
        super.setHandler(h);

        for (ArrayList<GUIObject> row: tableRows) {
            for (GUIObject obj: row) {
                obj.setHandler(h);
            }
        }
    }

    public void appendToRow(GUIObject obj, int index) {
        tableRows.get(index).add(obj);
    }

    @Override
    public void draw(Graphics g) {
        for (ArrayList<GUIObject> row: tableRows) {
            for(GUIObject obj: row) {
                obj.draw(g);
            }
        }
    }

    @Override
    public void updateDimensions() {
        int currentY = this.coordY;
        for (ArrayList<GUIObject> row: tableRows) {
            int currentX = this.coordX;
            for(GUIObject obj: row) {
                obj.setPosition(currentX, currentY);
                obj.updateDimensions();

                currentX += xMargin; //Add small margin between columns
                currentX += getColumnWidth(tableRows, row.indexOf(obj));
            }
            currentY += getRowHeight(row);
        }
        this.width = calculateWidth();
        this.height = calculateHeight();
    }

    private int calculateHeight() {
        int sum = 0;
        for (ArrayList<GUIObject> row: tableRows) {
            sum += getRowHeight(row);
        }
        return sum;
    }

    private int calculateWidth() {
        int sum = 0;

        // Get length of longest row in table
        int longestRow = 0;
        for (ArrayList<GUIObject> row: tableRows) {
            if (row.size() > longestRow) longestRow = row.size();
        }

        for (int i = 0; i < longestRow; i++) {
            sum += getColumnWidth(tableRows, i);
        }

        return sum;
    }

    private static int getRowHeight(ArrayList<GUIObject> row) {
        int max = 0;
        for (GUIObject obj:row) {
            if (obj.height > max) max = obj.height;
        }
        return max;
    }

    private static int getColumnWidth(ArrayList<ArrayList<GUIObject>> table, int columnIndex) {
        int max = 0;
        for (ArrayList<GUIObject> row: table ) {
            if (row.size() <= columnIndex) continue; // if this row doesn't have a cell at this index
            if (row.get(columnIndex).width > max)
                max = row.get(columnIndex).width;
        }
        return max;
    }

    @Override
    public void handleMouseEvent(int x, int y) {
        for (ArrayList<GUIObject> row: tableRows) {
            for (GUIObject obj: row) {
                if (obj.isInGUIObject(x, y)) {
                    obj.handleMouseEvent(x, y);
                }
            }
        }
    }

    @Override
    public ArrayList<GUIObject> getChildObjects() {
        ArrayList<GUIObject> objs = new ArrayList<>();
        for (ArrayList<GUIObject> row: tableRows) {
            for (GUIObject obj: row) {
                objs.addAll(obj.getChildObjects());
            }
            objs.addAll(row);
        }
        return objs;
    }
}
