package gui;

import java.awt.*;
import java.util.ArrayList;

public class GUITable extends GUIObject {

    ArrayList<ArrayList<GUIObject>> tableObjects; //list of rows

    public GUITable(int x, int y) {
        super();

        this.tableObjects = new ArrayList<>();
        setPosition(x, y);
    }

    public void setTableObjects(ArrayList<ArrayList<GUIObject>> tableObjects) {
        this.tableObjects = tableObjects;

    }

    @Override
    public void draw(Graphics g) {
        int currentY = this.coordY;

        for (ArrayList<GUIObject> row: tableObjects) {

            int currentX = this.coordX;
            for(GUIObject obj: row) {

                obj.draw(g); //We draw before setting the position to make sure all values (width & height) of the GUIObject have been initialized
                obj.setPosition(currentX, currentY);

                currentX += getColumnWidth(tableObjects, row.indexOf(obj));
            }
            currentY += getRowHeight(row);
        }

        setDimensions(calculateWidth(), calculateHeight());
    }

    private int calculateHeight() {
        int sum = 0;
        for (ArrayList<GUIObject> row: tableObjects ) {
            sum += getRowHeight(row);
        }
        return sum;
    }

    private int calculateWidth() {
        int sum = 0;

        // Get length of longest row in table
        int longestRow = 0;
        for (ArrayList<GUIObject> row: tableObjects ) {
            if (row.size() > longestRow) longestRow = row.size();
        }

        for (int i = 0; i < longestRow; i++) {
            sum += getColumnWidth(tableObjects, i);
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



}
