package gui.Objects;

import events.EventHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * A gui table
 */
public class GUITable extends GUIObject {

    // needed parameters
    public static final int xMargin = 5;
    ArrayList<ArrayList<GUIObject>> tableRows; //list of rows

    /**
     * constructor
     * @param x The x coordinate of the table
     * @param y The y coordinate of the table
     */
    public GUITable(int x, int y) {
        super();
        this.tableRows = new ArrayList<>();
        setPosition(x, y);
    }

    /**
     * constructor
     * @param rows A list of rows to add
     */
    public GUITable(ArrayList<ArrayList<GUIObject>> rows) {
        super();

        this.tableRows = rows;
    }

    /**
     * add a row to the table
     * @param row the row to add
     */
    public void addRow(ArrayList<GUIObject> row) {
        this.tableRows.add(row);
    }

    /**
     * Get all inputs related this table
     * @return a list of inputs in this table
     */
    public ArrayList<GUIInput> getInputs(){
        ArrayList<GUIInput> inputs = new ArrayList<>(); // list of all inputs in this table
        for(ArrayList<GUIObject> row : tableRows) {
            for (GUIObject obj : row) {
                inputs.addAll(obj.getInputs());
            }
        }
        return inputs;
    }

    /**
     * Get all buttons in this table
     * @return a list of buttons in this table
     */
    public ArrayList<GUIButton> getButtons(){
        ArrayList<GUIButton> buttons = new ArrayList<>(); // list of all buttons in this table
        // list of all inputs in this table
        for(ArrayList<GUIObject> row : tableRows) {
            for (GUIObject obj : row) {
                buttons.addAll(obj.getButtons());
            }
        }
        return buttons;
    }

    /**
     * set the handler for all objects in this table
     * @param h  the document area that needs to be set
     */
    @Override
    public void setHandler(EventHandler h) {
        super.setHandler(h);

        for (ArrayList<GUIObject> row: tableRows) {
            for (GUIObject obj: row) {
                obj.setHandler(h);
            }
        }
    }

    /**
     * append an element to the row
     * @param obj   The object to add
     * @param index The index of the row
     */
    public void appendToRow(GUIObject obj, int index) {
        tableRows.get(index).add(obj);
    }

    /**
     * Draw every object in the table
     * @param g the graphics needed to draw each object
     */
    @Override
    public void draw(Graphics g) {
        for (ArrayList<GUIObject> row: tableRows) {
            for(GUIObject obj: row) {
                obj.draw(g);
            }
        }
    }

    /**
     * update the dimension of every object in the table
     */
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

    /**
     * Get the height of the table
     * @return the height of the table
     */
    private int calculateHeight() {
        int sum = 0;
        for (ArrayList<GUIObject> row: tableRows) {
            sum += getRowHeight(row);
        }
        return sum;
    }

    /**
     * Calculate the width of the table
     * @return  the width of the table
     */
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

    /**
     * Get the height of a row
     * @param row   the row to calculate
     * @return      The height of a row
     */
    private static int getRowHeight(ArrayList<GUIObject> row) {
        int max = 0;
        for (GUIObject obj:row) {
            if (obj.height > max) max = obj.height;
        }
        return max;
    }

    /**
     * Get the width of a column
     * @param table         The table
     * @param columnIndex   The index of the column
     * @return              The width of the column
     */
    private static int getColumnWidth(ArrayList<ArrayList<GUIObject>> table, int columnIndex) {
        int max = 0;
        for (ArrayList<GUIObject> row: table ) {
            if (row.size() <= columnIndex) continue; // if this row doesn't have a cell at this index
            if (row.get(columnIndex).width > max)
                max = row.get(columnIndex).width;
        }
        return max;
    }

    /**
     * Handle the mouse event on all objects in table
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of hte mouse event
     * @param id            The id of the event
     * @param clickCount    The click count of the event
     */
    @Override
    public void handleMouseEvent(int x, int y, int id, int clickCount) {
        for (ArrayList<GUIObject> row: tableRows) {
            for (GUIObject obj: row) {
                obj.handleMouseEvent(x, y, id, clickCount);
            }
        }
    }

    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier){
        for (ArrayList<GUIObject> row: tableRows) {
            for (GUIObject obj: row) {
                obj.handleKeyEvent(id, keyCode, keyChar, modifier);
            }
        }
    }

    /**
     * get all child objects form table
     * @return  a list of child objects
     */
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
