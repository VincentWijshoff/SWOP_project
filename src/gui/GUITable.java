package gui;

import java.awt.*;
import java.util.ArrayList;

public class GUITable extends GUIObject {

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

    @Override
    public void setDocumentArea(DocumentArea documentArea) {
        super.setDocumentArea(documentArea);

        for (ArrayList<GUIObject> row: tableRows) {
            for (GUIObject obj: row) {
                obj.setDocumentArea(documentArea);
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        int currentY = this.coordY;

        for (ArrayList<GUIObject> row: tableRows) {

            int currentX = this.coordX;
            for(GUIObject obj: row) {

                obj.setPosition(currentX, currentY);
                obj.draw(g);

                currentX += getColumnWidth(tableRows, row.indexOf(obj));
            }
            currentY += getRowHeight(row);
        }

        setDimensions(calculateWidth(), calculateHeight());
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
    public void handleClick(int x, int y) {
        for (ArrayList<GUIObject> row: tableRows) {
            for (GUIObject obj: row) {
                if (obj.isInGUIObject(x, y)) {
                    obj.handleClick(x, y);
                }
            }
        }
    }
}